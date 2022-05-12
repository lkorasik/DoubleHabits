package com.lkorasik.doublehabits.model.repository

import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.net.RequestContext
import com.lkorasik.doublehabits.net.dto.HabitDTO

class HabitRepositoryServer {
    fun getAllHabits(): List<Habit> {
        val habits = RequestContext.API.getHabits().execute()

        if (!habits.isSuccessful)
            return listOf()

        return habits.body()?.map { Habit.from(it) } ?: listOf()
    }

    suspend fun addHabit(habit: Habit) {
        sendHabit(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        sendHabit(habit)
    }

    private suspend fun sendHabit(habit: Habit) {
        val dto = HabitDTO.from(habit)
        RequestContext.API.createOrUpdateHabit(dto)
    }
}
