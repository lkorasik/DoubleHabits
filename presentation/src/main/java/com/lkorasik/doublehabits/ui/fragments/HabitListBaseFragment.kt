package com.lkorasik.doublehabits.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import com.lkorasik.data.repository.HabitRepositoryImpl
import com.lkorasik.domain.HabitsUseCase
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.FragmentHabitBaseBinding
import com.lkorasik.domain.entities.HabitType
import com.lkorasik.doublehabits.App
import com.lkorasik.doublehabits.component
import com.lkorasik.doublehabits.ui.MainActivity
import com.lkorasik.doublehabits.ui.adapters.HabitListPagerAdapter
import com.lkorasik.doublehabits.ui.custom_views.filter_view.FilterView
import com.lkorasik.doublehabits.view_model.EditorViewModel
import com.lkorasik.doublehabits.view_model.HabitsListViewModel
import com.lkorasik.doublehabits.view_model.ViewModelFactory
import javax.inject.Inject

class HabitListBaseFragment: Fragment() {
    private var fragmentAboutBinding: FragmentHabitBaseBinding? = null
    private val binding
        get() = fragmentAboutBinding!!
    private lateinit var pagerAdapter: HabitListPagerAdapter

    @Inject
    lateinit var repository: HabitRepositoryImpl

    @Inject
    lateinit var habitsUseCase: HabitsUseCase

    private val editorViewModel: EditorViewModel by activityViewModels {
//        ViewModelFactory((requireActivity().application as App).repository, (requireActivity().application as App).habitsUseCase)
        ViewModelFactory(repository, habitsUseCase)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentAboutBinding = FragmentHabitBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).component.inject(this)

        binding.addHabit.setOnClickListener {
            editorViewModel.createHabit()
            findNavController().navigate(R.id.habitEditorFragment)
        }

        pagerAdapter = HabitListPagerAdapter(this)
        binding.pager.offscreenPageLimit = 1
        binding.pager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = HabitType.values()[position].toString()
        }.attach()

        initFilterView(view)
    }

    private fun initFilterView(view: View) {
        val filterView = view.findViewById<FilterView>(R.id.filter)
        filterView.setOnAcceptListener { searchText, comparator, ignoreCase ->
            for(type in HabitType.values()){
                ViewModelProvider(requireActivity())[type.name, HabitsListViewModel::class.java]
                    .setFilter(searchText, ignoreCase)
                    .setHabitComparator(comparator)
            }
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(filterView)

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {}

            //TODO: отключай кнопку
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding
                    .addHabit
                    .animate()
                    .scaleX(1 - slideOffset)
                    .scaleY(1 - slideOffset)
                    .setDuration(0)
                    .start()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentAboutBinding = null
    }
}
