package com.lkorasik.doublehabits.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lkorasik.data.repository.HabitRepositoryImpl
import com.lkorasik.domain.HabitsUseCase


//todo: inject it!
class ViewModelFactory(private val habitRepository: HabitRepositoryImpl, private val useCase: HabitsUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            HabitsListViewModel::class.java -> HabitsListViewModel(useCase)
            EditorViewModel::class.java -> EditorViewModel(habitRepository)
            else -> throw IllegalStateException("Unknown ViewModel class")
        }

        return viewModel as T
    }
}
