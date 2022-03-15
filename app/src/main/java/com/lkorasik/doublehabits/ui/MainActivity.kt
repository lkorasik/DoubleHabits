package com.lkorasik.doublehabits.ui

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationView
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
        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            var f: Fragment? = null
            val itemId = menuItem.itemId

            if (itemId == R.id.home) {
                f = HabitsListFragment.newInstance()
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
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_add)
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
