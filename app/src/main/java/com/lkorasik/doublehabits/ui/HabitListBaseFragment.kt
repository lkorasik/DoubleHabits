package com.lkorasik.doublehabits.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.lkorasik.doublehabits.HabitType
import com.lkorasik.doublehabits.databinding.FragmentHabitBaseBinding

class HabitListBaseFragment: Fragment() {
    private var fragmentAboutBinding: FragmentHabitBaseBinding? = null
    private val binding
        get() = fragmentAboutBinding!!
    private lateinit var pagerAdapter: ScreenSlidePagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentAboutBinding = FragmentHabitBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addHabit.setOnClickListener {
            (activity as MainActivity).createHabit()
        }

        pagerAdapter = ScreenSlidePagerAdapter(this)
        binding.pager.offscreenPageLimit = 1
        binding.pager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = HabitType.values()[position].toString()
        }.attach()
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
