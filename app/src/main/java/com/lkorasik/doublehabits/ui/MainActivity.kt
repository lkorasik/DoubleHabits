package com.lkorasik.doublehabits.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.lkorasik.doublehabits.Habit
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.ActivityMainRBinding


class MainActivity: AppCompatActivity() {
    private lateinit var habitsListFragment: HabitsListFragment
    private lateinit var binding: ActivityMainRBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRBinding.inflate(layoutInflater)
        setContentView(binding.root)

        habitsListFragment = HabitsListFragment.newInstance()

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.fragment_host, habitsListFragment)
                .commit()
        }

        configureNavigationDrawer();
        configureToolbar();
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle {
        return ActionBarDrawerToggle(this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.add_habit_incorrect_count,
            R.string.dialog_color_picker_cancel)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return true
    }

    private fun configureNavigationDrawer() {
        drawerLayout = binding.drawerLayout
        val navView = binding.navigationView

        val drawerToggle = setupDrawerToggle()

        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()

        binding.drawerLayout.addDrawerListener(drawerToggle)

        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            var f: Fragment? = null
            val itemId = menuItem.itemId

            if (itemId == R.id.home) {
                f = HabitsListFragment.newInstance()
            }
            else if(itemId == R.id.about){
                f = AboutFragment.newInstance()
            }

            if (f != null) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_host, f)
                transaction.commit()
                drawerLayout.closeDrawers()
                return@OnNavigationItemSelectedListener true
            }
            false
        })
    }

    private fun configureToolbar() {
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1){
            finish()
        }
        else {
            super.onBackPressed()
        }
    }

    fun createHabit() {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.fragment_host, HabitFragment.newInstance())
        }
    }

    fun editHabit(habit: Habit, position: Int) {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.fragment_host, HabitFragment.newInstance(habit, position))
        }
    }

    fun saveHabit(habit: Habit, position: Int) {
        supportFragmentManager.popBackStack()
        habitsListFragment.editHabit(habit, position)
    }

    fun saveHabit(habit: Habit) {
        supportFragmentManager.popBackStack()
        habitsListFragment.addHabit(habit)
    }

    fun setTitle(title: String) {
        this.title = title
    }
}
