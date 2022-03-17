package com.lkorasik.doublehabits.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.databinding.ActivityMainBinding
import com.lkorasik.doublehabits.databinding.FragmentHabitListBinding
import com.lkorasik.doublehabits.habit_adapter.HabitRecycleViewAdapter

class HabitsListFragment(private val habits: MutableList<Habit>, private val adapter: HabitRecycleViewAdapter): Fragment() {
    private lateinit var binding: FragmentHabitListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addHabit.setOnClickListener {
            (activity as MainActivity).createHabit()
        }

        binding.habitsList.layoutManager = LinearLayoutManager(binding.root.context)
        binding.habitsList.adapter = adapter
    }

    companion object {
        fun newInstance(habits: MutableList<Habit>, adapter: HabitRecycleViewAdapter): HabitsListFragment {
            return HabitsListFragment(habits, adapter)
        }
    }
}
