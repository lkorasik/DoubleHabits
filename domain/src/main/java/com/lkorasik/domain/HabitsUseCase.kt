package com.lkorasik.domain

class HabitsUseCase(private val habitRepository: Repository) {
    fun getAllHabits() = habitRepository.getAllHabits()
}