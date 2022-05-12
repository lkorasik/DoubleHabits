package com.lkorasik.doublehabits.model.repository

import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.model.HabitPriority
import com.lkorasik.doublehabits.model.HabitType
import com.lkorasik.doublehabits.net.RequestContext
import com.lkorasik.doublehabits.net.dto.HabitDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant

class HabitRepositoryServer {
    suspend fun addHabit(habit: Habit) {
        val dto = HabitDTO(
            color = habit.color,
            count = habit.count,
            date = Instant.now().toEpochMilli().toInt(),
            description = habit.description,
            done_dates = listOf(0),
            frequency = habit.periodicity.toInt(),
            priority = habit.priority.ordinal,
            title = habit.name,
            type = habit.type.ordinal,
            uid = null
        )
        RequestContext.API.createOrUpdateHabit(dto)
    }

    fun getAllHabits(): List<Habit> {
        val habits = RequestContext.API.getHabits().execute()

        if (habits.isSuccessful) {
            return habits.body()?.map {
                Habit(
                    id = it.uid!!,
                    name = it.title,
                    description = it.description,
                    priority = HabitPriority.values()[it.priority],
                    type = HabitType.values()[it.type],
                    periodicity = it.frequency.toString(),
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
        val dto = HabitDTO(
            color = habit.color,
            count = habit.count,
            date = Instant.now().toEpochMilli().toInt(),
            description = habit.description,
            done_dates = listOf(0),
            frequency = habit.periodicity.toInt(),
            priority = habit.priority.ordinal,
            title = habit.name,
            type = habit.type.ordinal,
            uid = habit.id
        )
        RequestContext.API.createOrUpdateHabit(dto)
    }
}
