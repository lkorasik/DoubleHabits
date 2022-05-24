package com.lkorasik.doublehabits.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lkorasik.doublehabits.model.HabitRepository

class ViewModelFactory(private val habitRepository: HabitRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            HabitsListViewModel::class.java -> HabitsListViewModel(habitRepository)
            EditorViewModel::class.java -> EditorViewModel(habitRepository)
            else -> throw IllegalStateException("Unknown ViewModel class")
        }

        return viewModel as T
    }
}
