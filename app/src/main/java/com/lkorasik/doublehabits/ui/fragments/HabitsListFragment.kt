package com.lkorasik.doublehabits.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lkorasik.doublehabits.IntentKeys
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.model.HabitType
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.databinding.FragmentHabitListBinding
import com.lkorasik.doublehabits.ui.adapters.habit_adapter.HabitRecycleViewAdapter
import com.lkorasik.doublehabits.view_model.EditorViewModel
import com.lkorasik.doublehabits.view_model.HabitsListViewModel

class HabitsListFragment: Fragment() {
    private var fragmentHabitListBinding: FragmentHabitListBinding? = null
    private val binding
        get() = fragmentHabitListBinding!!

    private lateinit var adapter: HabitRecycleViewAdapter

    private val editorViewModel: EditorViewModel by activityViewModels()
    private lateinit var vm: HabitsListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentHabitListBinding = FragmentHabitListBinding.inflate(inflater, container, false)

        adapter = HabitRecycleViewAdapter(binding.root.context) { data, position ->
            editHabit(data, position)
        }

        val mode = arguments?.get("Mode") as HabitType

        vm = ViewModelProvider(requireActivity())[mode.name, HabitsListViewModel::class.java]

        vm.loadHabits(mode)
        adapter.submitList(vm.getHabits().value)

        vm.getHabits().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.habitsList.layoutManager = LinearLayoutManager(binding.root.context)
        binding.habitsList.adapter = adapter

        return binding.root
    }

    private fun editHabit(habit: Habit, position: Int) {
        //TODO: Читай про SafeArgs
        val mode = arguments?.get("Mode") as HabitType
        editorViewModel.loadHabit(mode, position)
        findNavController().navigate(R.id.habitEditorFragment, bundleOf(IntentKeys.Habit to habit, IntentKeys.Position to position))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentHabitListBinding = null
    }

    companion object {
        fun newInstance(mode: HabitType): HabitsListFragment {
            return HabitsListFragment().apply {
                arguments = bundleOf("Mode" to mode)
            }
        }
    }
}
