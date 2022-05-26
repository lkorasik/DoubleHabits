package com.lkorasik.doublehabits.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lkorasik.data.repository.HabitRepositoryImpl
import com.lkorasik.data.room.AppDatabase
import com.lkorasik.data.room.HabitEntity
import com.lkorasik.domain.HabitsUseCase
import com.lkorasik.doublehabits.IntentKeys
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.FragmentHabitListBinding
import com.lkorasik.domain.entities.HabitType
import com.lkorasik.doublehabits.App
import com.lkorasik.doublehabits.component
import com.lkorasik.doublehabits.ui.MainActivity
import com.lkorasik.doublehabits.ui.adapters.habit_adapter.HabitRecycleViewAdapter
import com.lkorasik.doublehabits.view_model.EditorViewModel
import com.lkorasik.doublehabits.view_model.HabitsListViewModel
import com.lkorasik.doublehabits.view_model.ViewModelFactory
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class HabitsListFragment: Fragment() {
    private var fragmentHabitListBinding: FragmentHabitListBinding? = null
    private val binding
        get() = fragmentHabitListBinding!!

    private lateinit var adapter: HabitRecycleViewAdapter

//    @Inject
//    lateinit var database: AppDatabase

    @Inject
    lateinit var repository: HabitRepositoryImpl

    @Inject
    lateinit var habitsUseCase: HabitsUseCase

    private val editorViewModel: EditorViewModel by activityViewModels {
//        ViewModelFactory((requireActivity().application as App).repository, (requireActivity().application as App).habitsUseCase)
//        ViewModelFactory(repository, (requireActivity().application as App).habitsUseCase)
        ViewModelFactory(repository, habitsUseCase)
    }
    private lateinit var vm: HabitsListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentHabitListBinding = FragmentHabitListBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).component.inject(this)

        adapter = HabitRecycleViewAdapter(binding.root.context, { habit, position ->
            editHabit(habit, position)
        }, { habit, position ->
            doneHabit(habit, position)
            Toast.makeText(context, "Some text special for u ${habit.id}", Toast.LENGTH_LONG).show()
        })

        val mode = arguments?.get(IntentKeys.Mode) as HabitType

//        val factory = ViewModelFactory((requireActivity().application as App).repository, (requireActivity().application as App).habitsUseCase)
//        val factory = ViewModelFactory(repository, (requireActivity().application as App).habitsUseCase)
        val factory = ViewModelFactory(repository, habitsUseCase)
        vm = ViewModelProvider(requireActivity(), factory)[mode.name, HabitsListViewModel::class.java]

        vm.loadHabits()

        vm.getHabits(mode).observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        vm.toast.observe(viewLifecycleOwner) {
            if (it.isNotEmpty())
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        binding.habitsList.layoutManager = LinearLayoutManager(binding.root.context)
        binding.habitsList.adapter = adapter

        return binding.root
    }

    private fun doneHabit(habit: HabitEntity, position: Int) {
        vm.doneHabit(habit, position)
    }

    private fun editHabit(habit: HabitEntity, position: Int) {
        editorViewModel.loadHabit(habit)
        findNavController().navigate(R.id.habitEditorFragment, bundleOf(IntentKeys.Habit to habit, IntentKeys.Position to position))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentHabitListBinding = null
    }

    companion object {
        fun newInstance(mode: HabitType): HabitsListFragment {
            return HabitsListFragment().apply {
                arguments = bundleOf(IntentKeys.Mode to mode)
            }
        }
    }
}
