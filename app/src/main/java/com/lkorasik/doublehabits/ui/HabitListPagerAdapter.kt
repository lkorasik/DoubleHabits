package com.lkorasik.doublehabits.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.model.HabitType

class HabitListPagerAdapter(base: Fragment) : FragmentStateAdapter(base) {
    private val fragments = listOf(
        HabitsListFragment.newInstance(HabitType.REGULAR),
        HabitsListFragment.newInstance(HabitType.HARMFUL))

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
