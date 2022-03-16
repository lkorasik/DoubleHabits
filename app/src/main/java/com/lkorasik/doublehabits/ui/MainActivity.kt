package com.lkorasik.doublehabits.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.ActivityMainRBinding
import com.lkorasik.doublehabits.model.Habit


class MainActivity: AppCompatActivity(), HabitSaver {
    private lateinit var binding: ActivityMainRBinding

    private val habitsListFragment: HabitsListFragment = HabitsListFragment.newInstance()
    private val aboutFragment: AboutFragment = AboutFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureNavigationDrawer()
        configureToolbar()

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                addToBackStack(null)
                add(R.id.fragment_host, habitsListFragment)
            }
        }
    }

    private fun configureNavigationDrawer() {
        val drawer = setupDrawerToggle()
        binding.drawerLayout.addDrawerListener(drawer)

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            binding.navigationView.setCheckedItem(menuItem)

            val fragment = when(menuItem.itemId) {
                R.id.home -> habitsListFragment
                R.id.about -> aboutFragment
                else -> null
            }

            fragment?.let {
                supportFragmentManager.commit {
                    replace(R.id.fragment_host, it)
                }

                binding.drawerLayout.closeDrawers()

                return@setNavigationItemSelectedListener true
            }

            return@setNavigationItemSelectedListener false
        }
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.main_activity_open_navigation_drawer,
            R.string.main_activity_close_navigation_drawer)
        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()
        return toggle
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> {
                supportFragmentManager.findFragmentByTag("Editor")?.onOptionsItemSelected(item)!!
            }
        }
    }

    private fun configureToolbar() {
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        when {
            binding.drawerLayout.isDrawerOpen(GravityCompat.START) -> binding.drawerLayout.closeDrawer(GravityCompat.START)
            supportFragmentManager.backStackEntryCount == 1 -> finish()
            else -> super.onBackPressed()
        }
    }

    fun createHabit() {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.fragment_host, HabitEditorFragment.newInstance(), "Editor")
        }
    }

    fun editHabit(habit: Habit, position: Int) {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.fragment_host, HabitEditorFragment.newInstance(habit, position), "Editor")
        }
    }

    override fun saveHabit(habit: Habit, position: Int) {
        supportFragmentManager.popBackStack()
        habitsListFragment.editHabit(habit, position)
    }

    override fun saveHabit(habit: Habit) {
        supportFragmentManager.popBackStack()
        habitsListFragment.addHabit(habit)
    }
}
