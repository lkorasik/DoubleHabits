package com.lkorasik.doublehabits.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lkorasik.doublehabits.model.Habit

class ScreenSlidePagerAdapter(private val habits: MutableList<Habit>, base: Fragment) : FragmentStateAdapter(base) {
    private val fragments = listOf<Fragment>(HabitsListFragment.newInstance(habits), HabitsListFragment.newInstance(habits))

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}