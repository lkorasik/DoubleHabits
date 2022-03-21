package com.lkorasik.doublehabits.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.lkorasik.doublehabits.IntentKeys
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.ActivityMainBinding
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.model.HabitType


class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    var habitsRegular: MutableList<Habit> = mutableListOf()
    var habitsHarmful: MutableList<Habit> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureToolbar()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
        navController = navHostFragment.navController

        binding.navigationView.setupWithNavController(navController)

        val appBarConfig = AppBarConfiguration(navController.graph, drawerLayout = binding.drawerLayout)
        binding.toolbar.setupWithNavController(navController, appBarConfig)
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
        navController.navigate(R.id.habitEditorFragment)
    }

    fun editHabit(habit: Habit, position: Int) {
        navController.navigate(R.id.habitEditorFragment, bundleOf(IntentKeys.Habit to habit, IntentKeys.Position to position))
    }

    fun saveHabit(habit: Habit, position: Int) {
        when(habit.type) {
            HabitType.REGULAR -> habitsRegular[position] = habit
            HabitType.HARMFUL -> habitsHarmful[position] = habit
        }
        navController.popBackStack()
    }

    fun move(type: HabitType, position: Int): Int {
        return when(type) {
            HabitType.REGULAR -> {
                val habit = habitsRegular[position]
                habitsRegular.removeAt(position)
                habitsHarmful.add(habit)
                habitsHarmful.indexOf(habit)
            }
            HabitType.HARMFUL -> {
                val habit = habitsHarmful[position]
                habitsHarmful.removeAt(position)
                habitsRegular.add(habit)
                habitsRegular.indexOf(habit)
            }
        }
    }

    fun saveHabit(habit: Habit) {
        when(habit.type) {
            HabitType.REGULAR -> habitsRegular.add(habit)
            HabitType.HARMFUL -> habitsHarmful.add(habit)
        }
        navController.popBackStack()
    }

    companion object {
        private const val EDITOR_FRAGMENT_TAG = "Editor"
    }
}
