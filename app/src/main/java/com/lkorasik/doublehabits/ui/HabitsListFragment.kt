package com.lkorasik.doublehabits.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lkorasik.doublehabits.Habit
import com.lkorasik.doublehabits.databinding.ActivityMainBinding
import com.lkorasik.doublehabits.habit_adapter.HabitRecycleViewAdapter

class HabitsListFragment: Fragment() {
    private lateinit var binding: ActivityMainBinding

    private var habits: MutableList<Habit> = mutableListOf()
    private lateinit var adapter: HabitRecycleViewAdapter

    //TODO: юзай diffUtils, он эффективно обновляет
    //TODO: notify в адаптер
    fun editHabit(habit: Habit, position: Int) {
        habits[position] = habit
        adapter.notifyItemChanged(position)
    }

    fun addHabit(habit: Habit) {
        habits.add(habit)
        adapter.notifyItemInserted(habits.size - 1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ActivityMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.let {
            it.addHabit.setOnClickListener {
                (activity as MainActivity).createHabit()
            }
            it.habitsList.layoutManager = LinearLayoutManager(binding.root.context)
            setAdapter()
        }
    }

    private fun setAdapter() {
        adapter = HabitRecycleViewAdapter(habits, binding.root.context)
        adapter.setOnItemClick { data, position ->
            (activity as MainActivity).editHabit(data, position)
        }
        binding.habitsList.adapter = adapter
    }

    companion object {
        //TODO: Зачем тут метод, который по сути конструктор?
        //TODO: Это же фабричный метод?
        fun newInstance(): HabitsListFragment {
            return HabitsListFragment()
        }
    }
}
