package com.lkorasik.doublehabits.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lkorasik.doublehabits.HabitType
import com.lkorasik.doublehabits.habit_adapter.HabitRecycleViewAdapter
import com.lkorasik.doublehabits.model.Habit

class ScreenSlidePagerAdapter(base: Fragment) : FragmentStateAdapter(base) {
    private val fragments = listOf(HabitsListFragment.newInstance(HabitType.REGULAR), HabitsListFragment.newInstance(HabitType.HARMFUL))

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun getFragment(type: HabitType): HabitsListFragment {
        return when(type) {
            HabitType.REGULAR -> fragments[0]
            HabitType.HARMFUL -> fragments[1]
        }
    }
}