package com.lkorasik.doublehabits

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lkorasik.doublehabits.databinding.ActivityMainBinding

class HabitsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var habits: MutableList<Habit> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addHabit.setOnClickListener {
            val intent = Intent(this, AddHabitActivity::class.java)
            getNewHabit.launch(intent)
        }

        binding.habitsList.layoutManager = LinearLayoutManager(this)
        setAdapter()
    }

    private val getNewHabit = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            val habit = it.data?.getParcelableExtra<Habit>(IntentKeys.Habit) as Habit
            habits.add(habit)

            setAdapter()
        }
    }

    private val editExistingHabit = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            val habit = it.data?.getParcelableExtra<Habit>(IntentKeys.Habit) as Habit
            val position = it.data?.getIntExtra(IntentKeys.Position, -1)

            habits[position!!] = habit

            setAdapter()
        }
    }

    private fun setAdapter() {
        binding.habitsList.adapter = HabitRecycleViewAdapter(habits, this) { data, position ->
            val intent = Intent(this, AddHabitActivity::class.java).apply {
                putExtra(IntentKeys.Habit, data)
                putExtra(IntentKeys.Position, position)
            }

            editExistingHabit.launch(intent)
        }
    }
}