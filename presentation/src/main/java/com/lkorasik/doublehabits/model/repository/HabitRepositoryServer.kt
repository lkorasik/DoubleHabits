package com.lkorasik.doublehabits.model.repository

import com.lkorasik.domain.Habit
import com.lkorasik.domain.HabitPriority
import com.lkorasik.domain.HabitType
import com.lkorasik.doublehabits.net.RequestContext
import com.lkorasik.doublehabits.net.dto.HabitDTO
import com.lkorasik.doublehabits.net.dto.HabitUID_DTO
import java.time.Instant

class HabitRepositoryServer {
    fun getAllHabits(): List<com.lkorasik.domain.Habit> {
        val habits = RequestContext.API.getHabits().execute()

        if (!habits.isSuccessful)
            return listOf()

        return habits.body()?.map { habit ->
            Habit(
                id = habit.uid ?: "",
                title = habit.title,
                description = habit.description,
                priority = HabitPriority.values()[habit.priority],
                type = HabitType.values()[habit.type],
                frequency = habit.frequency.toString(),
                color = habit.color,
                count = habit.count,
                createdAt = Instant.now(),
                lastEditedAt = Instant.now()
            )
        } ?: listOf()
//        return habits.body()?.map { com.lkorasik.domain.Habit.from(it) } ?: listOf()
    }

    suspend fun addHabit(habit: com.lkorasik.domain.Habit) {
        sendHabit(habit)
    }

    suspend fun updateHabit(habit: com.lkorasik.domain.Habit) {
        sendHabit(habit)
    }

    private suspend fun sendHabit(habit: com.lkorasik.domain.Habit) {
        val dto = HabitDTO.from(habit)
        RequestContext.API.createOrUpdateHabit(dto)
    }

    suspend fun deleteHabit(habit: HabitUID_DTO) {
        RequestContext.API.deleteHabit(habit)
    }
}
