package com.lkorasik.doublehabits.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.lkorasik.doublehabits.Habit
import com.lkorasik.doublehabits.R

class MainActivity: AppCompatActivity() {
    private lateinit var habitsListFragment: HabitsListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_r)

        habitsListFragment = HabitsListFragment.newInstance()

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.fragment_host, habitsListFragment)
                .commit()
        }
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
