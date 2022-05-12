package com.lkorasik.doublehabits.model.repository

import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.model.HabitPriority
import com.lkorasik.doublehabits.model.HabitType
import com.lkorasik.doublehabits.net.RequestContext
import com.lkorasik.doublehabits.net.dto.HabitDTO
import java.sql.Timestamp
import java.time.Instant

class HabitRepositoryServer {
    suspend fun addHabit(habit: Habit) {
        val dto = HabitDTO.from(habit)
        RequestContext.API.createOrUpdateHabit(dto)
    }

    fun getAllHabits(): List<Habit> {
        val habits = RequestContext.API.getHabits().execute()

        if (habits.isSuccessful) {
            return habits.body()?.map {
                Habit(
                    id = it.uid!!,
                    title = it.title,
                    description = it.description,
                    priority = HabitPriority.values()[it.priority],
                    type = HabitType.values()[it.type],
                    frequency = it.frequency.toString(),
                    color = it.color,
                    count = it.count,
                    createdAt = Instant.now(),
                    lastEditedAt = Instant.now()
                )
            } ?: listOf()
        }
        else {
            return listOf()
        }
    }

    suspend fun updateHabit(habit: Habit) {
        val dto = HabitDTO.from(habit)
        RequestContext.API.createOrUpdateHabit(dto)
    }
}