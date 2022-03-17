package com.lkorasik.doublehabits.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lkorasik.doublehabits.habit_adapter.HabitRecycleViewAdapter
import com.lkorasik.doublehabits.model.Habit

class ScreenSlidePagerAdapter(adapter: HabitRecycleViewAdapter, base: Fragment) : FragmentStateAdapter(base) {
    private val fragments = listOf(HabitsListFragment.newInstance(adapter), HabitsListFragment.newInstance(adapter))

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}