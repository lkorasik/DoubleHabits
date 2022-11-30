package com.lkorasik.domain

import com.lkorasik.domain.entities.HabitModel
import javax.inject.Inject

//TODO: Rename Interactor (interactor -- несколько use case)
class HabitsUseCase @Inject constructor(private val habitRepository: Repository) {
    fun getAllHabits() = habitRepository.getAllHabits()
    suspend fun loadHabits() = habitRepository.loadHabits()

    suspend fun updateHabit(habitModel: HabitModel) {
        habitRepository.editHabit(habitModel)
    }

    suspend fun doneHabit(habitModel: HabitModel): String {
        return habitRepository.doneHabit(habitModel)
    }
}