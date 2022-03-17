package com.lkorasik.doublehabits.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.scaleMatrix
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.lkorasik.doublehabits.HabitType
import com.lkorasik.doublehabits.databinding.FragmentHabitBaseBinding
import com.lkorasik.doublehabits.habit_adapter.HabitRecycleViewAdapter
import com.lkorasik.doublehabits.model.Habit

class HabitListBaseFragment: Fragment() {
    private var fragmentAboutBinding: FragmentHabitBaseBinding? = null
    private val binding
        get() = fragmentAboutBinding!!

    private var habits: MutableList<Habit> = mutableListOf()
    private lateinit var adapter: HabitRecycleViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentAboutBinding = FragmentHabitBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HabitRecycleViewAdapter(habits, binding.root.context) { data, position ->
            (activity as MainActivity).editHabit(data, position)
        }

        val pagerAdapter = ScreenSlidePagerAdapter(habits, adapter, this)
        binding.pager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = HabitType.values()[position].toString()
        }.attach()
    }

    fun editHabit(habit: Habit, position: Int) {
        adapter.editHabit(habit, position)
    }

    fun addHabit(habit: Habit) {
        adapter.addHabit(habit)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentAboutBinding = null
    }

    companion object {
        fun newInstance(): HabitListBaseFragment {
            return HabitListBaseFragment()
        }
    }
}
