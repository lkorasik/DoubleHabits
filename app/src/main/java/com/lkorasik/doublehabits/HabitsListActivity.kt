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
    private lateinit var adapter: HabitRecycleViewAdapter

    private val getNewHabit = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            val habit = it.data?.getParcelableExtra<Habit>(IntentKeys.Habit) as Habit
            habits.add(habit)
            adapter.notifyItemInserted(habits.size - 1)
        }
    }

    private val editExistingHabit = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            val habit = it.data?.getParcelableExtra<Habit>(IntentKeys.Habit) as Habit
            val position = it.data?.getIntExtra(IntentKeys.Position, -1)

            habits[position!!] = habit
            adapter.notifyItemChanged(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity()
        initViews()
    }

    private fun initActivity() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initViews() {
        binding.let {
            it.addHabit.setOnClickListener {
                val intent = Intent(this, AddHabitActivity::class.java)
                getNewHabit.launch(intent)
            }
            it.habitsList.layoutManager = LinearLayoutManager(this)
            setAdapter()
        }
    }

    private fun setAdapter() {
        adapter = HabitRecycleViewAdapter(habits, this) { data, position ->
            val intent = Intent(this, AddHabitActivity::class.java).apply {
                putExtra(IntentKeys.Habit, data)
                putExtra(IntentKeys.Position, position)
            }

            editExistingHabit.launch(intent)
        }
        binding.habitsList.adapter = adapter
    }
}
