package com.lkorasik.doublehabits.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lkorasik.domain.HabitType
import com.lkorasik.doublehabits.ui.fragments.HabitsListFragment

class HabitListPagerAdapter(base: Fragment) : FragmentStateAdapter(base) {
    private val fragments = mutableListOf<HabitsListFragment>().apply {
        HabitType.values().forEach { add(HabitsListFragment.newInstance(it)) }
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
