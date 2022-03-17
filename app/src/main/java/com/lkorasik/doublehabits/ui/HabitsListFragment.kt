package com.lkorasik.doublehabits.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.databinding.ActivityMainBinding
import com.lkorasik.doublehabits.databinding.FragmentHabitListBinding
import com.lkorasik.doublehabits.habit_adapter.HabitRecycleViewAdapter

class HabitsListFragment(private val habits: MutableList<Habit>): Fragment() {
    private lateinit var binding: FragmentHabitListBinding

    private lateinit var adapter: HabitRecycleViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHabitListBinding.inflate(inflater, container, false)
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

    private fun setAdapter() {
        adapter = HabitRecycleViewAdapter(habits, binding.root.context)
        adapter.setOnItemClick { data, position ->
            (activity as MainActivity).editHabit(data, position)
        }

        binding.habitsList.adapter = adapter
    }

    companion object {
        fun newInstance(habits: MutableList<Habit>): HabitsListFragment {
            return HabitsListFragment(habits)
        }
    }
}
