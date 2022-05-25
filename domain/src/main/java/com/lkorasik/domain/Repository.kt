package com.lkorasik.domain

import com.lkorasik.domain.entities.HabitModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getAllHabits(): Flow<List<HabitModel>>
    suspend fun loadHabits()
    suspend fun addHabit(habit: HabitModel)
    suspend fun editHabit(habit: HabitModel)
}