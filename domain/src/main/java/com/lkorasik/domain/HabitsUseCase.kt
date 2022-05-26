package com.lkorasik.domain

import javax.inject.Inject

class HabitsUseCase @Inject constructor(private val habitRepository: Repository) {
    fun getAllHabits() = habitRepository.getAllHabits()
    suspend fun loadHabits() = habitRepository.loadHabits()
}