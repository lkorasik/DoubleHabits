package com.lkorasik.doublehabits.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.lkorasik.doublehabits.databinding.FragmentHabitBaseBinding
import com.lkorasik.doublehabits.model.Habit

class HabitListBaseFragment: Fragment() {
    private lateinit var binding: FragmentHabitBaseBinding

    private var habits: MutableList<Habit> = mutableListOf()

    private lateinit var first: HabitsListFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHabitBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = ScreenSlidePagerAdapter(habits, this)
        first = pagerAdapter.createFragment(0) as HabitsListFragment
        binding.pager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
    }

    fun editHabit(habit: Habit, position: Int) {
        first.editHabit(habit, position)
//        habits[position] = habit
//        adapter.notifyItemChanged(position)
    }

    fun addHabit(habit: Habit) {
        first.addHabit(habit)
//        habits.add(habit)
//        adapter.notifyItemInserted(habits.size - 1)
    }

//    override fun onBackPressed() {
//        if (binding.pager.currentItem == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed()
//        } else {
//            // Otherwise, select the previous step.
//            binding.pager.currentItem = binding.pager.currentItem - 1
//        }
//    }

    companion object {
        fun newInstance(): HabitListBaseFragment {
            return HabitListBaseFragment()
        }
    }
}
