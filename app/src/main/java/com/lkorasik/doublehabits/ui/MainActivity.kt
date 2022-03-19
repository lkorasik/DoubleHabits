package com.lkorasik.doublehabits.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import com.lkorasik.doublehabits.HabitType
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.ActivityMainBinding
import com.lkorasik.doublehabits.model.Habit


class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val habitListBaseFragment = HabitListBaseFragment.newInstance()
    private val aboutFragment = AboutFragment.newInstance()

    var habitsRegular: MutableList<Habit> = mutableListOf()
    var habitsHarmful: MutableList<Habit> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureNavigationDrawer()
        configureToolbar()

        if (savedInstanceState == null)
            initFragmentManager()
    }

    private fun initFragmentManager() {
        supportFragmentManager.commit {
            addToBackStack(null)
            add(R.id.fragment_host, habitListBaseFragment)
        }
    }

    private fun configureNavigationDrawer() {
        val drawer = setupDrawerToggle()
        binding.drawerLayout.addDrawerListener(drawer)

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            binding.navigationView.setCheckedItem(menuItem)

            val fragment = when(menuItem.itemId) {
                R.id.home -> habitListBaseFragment
                R.id.about -> aboutFragment
                else -> return@setNavigationItemSelectedListener false
            }

            supportFragmentManager.commit {
                replace(R.id.fragment_host, fragment)
            }

            binding.drawerLayout.closeDrawers()

            return@setNavigationItemSelectedListener true
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

    private fun configureToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> {
                //TODO: Есть варианты как передать по-другому управление во фрагмент
                val fragment = supportFragmentManager.findFragmentByTag(EDITOR_FRAGMENT_TAG)
                fragment?.onOptionsItemSelected(item) ?: super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onBackPressed() {
        val drawer = binding.drawerLayout

        when {
            drawer.isDrawerOpen(GravityCompat.START) -> drawer.closeDrawer(GravityCompat.START)
            supportFragmentManager.backStackEntryCount == 1 -> finish()
            else -> super.onBackPressed()
        }
    }

    fun createHabit() {
        val host = R.id.fragment_host
        //TODO: Попробуй переиспользовать фрагмент редактора привычки
        val fragment = HabitEditorFragment.newInstance()

        supportFragmentManager.commit {
            addToBackStack(null)
            replace(host, fragment, EDITOR_FRAGMENT_TAG)
        }
    }

    fun editHabit(habit: Habit, position: Int) {
        val host = R.id.fragment_host
        val fragment = HabitEditorFragment.newInstance(habit, position)

        supportFragmentManager.commit {
            addToBackStack(null)
            replace(host, fragment, EDITOR_FRAGMENT_TAG)
        }
    }

    fun saveHabit(habit: Habit, position: Int) {
        when(habit.type) {
            HabitType.REGULAR -> habitsRegular[position] = habit
            HabitType.HARMFUL -> habitsHarmful[position] = habit
        }
        supportFragmentManager.popBackStack()
//        habitListBaseFragment.editHabit(habit, position)
    }

    fun saveHabit(habit: Habit) {
        when(habit.type) {
            HabitType.REGULAR -> habitsRegular.add(habit)
            HabitType.HARMFUL -> habitsHarmful.add(habit)
        }
        supportFragmentManager.popBackStack()
//        habitListBaseFragment.addHabit(habit)
    }

    companion object {
        private const val EDITOR_FRAGMENT_TAG = "Editor"
    }
}
