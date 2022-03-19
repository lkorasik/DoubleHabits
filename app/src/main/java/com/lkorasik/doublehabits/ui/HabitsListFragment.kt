package com.lkorasik.doublehabits.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lkorasik.doublehabits.HabitType
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.databinding.FragmentHabitListBinding
import com.lkorasik.doublehabits.habit_adapter.HabitRecycleViewAdapter

class HabitsListFragment(private val mode: HabitType): Fragment() {
    private var fragmentHabitListBinding: FragmentHabitListBinding? = null
    private val binding
        get() = fragmentHabitListBinding!!

    private var habits: MutableList<Habit> = mutableListOf()
    private lateinit var adapter: HabitRecycleViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentHabitListBinding = FragmentHabitListBinding.inflate(inflater, container, false)

        adapter = HabitRecycleViewAdapter(binding.root.context) { data, position ->
            (activity as MainActivity).editHabit(data, position)
        }

        when(mode) {
            HabitType.REGULAR -> habits = (activity as MainActivity).habitsRegular
            HabitType.HARMFUL -> habits = (activity as MainActivity).habitsHarmful
        }
        adapter.submitList(habits)

        Log.i("DT", "Adapter inited, $mode")

        binding.habitsList.layoutManager = LinearLayoutManager(binding.root.context)
        binding.habitsList.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentHabitListBinding = null
    }

    companion object {
        fun newInstance(mode: HabitType): HabitsListFragment {
            return HabitsListFragment(mode)
        }
    }
}
