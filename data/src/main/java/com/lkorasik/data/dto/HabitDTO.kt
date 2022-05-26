package com.lkorasik.data.dto

import com.lkorasik.data.room.HabitEntity
import com.lkorasik.domain.entities.HabitModel
import com.lkorasik.domain.entities.HabitPriority
import com.lkorasik.domain.entities.HabitType
import java.time.Instant

data class HabitDTO(
    val uid: String?,
    val title: String,
    val color: Int,
    val count: Int,
    val date: Int,
    val description: String,
    val done_dates: List<Int>,
    val frequency: Int,
    val priority: Int,
    val type: Int
) {
    companion object {
        fun from(habit: HabitEntity) = HabitDTO(
            color = habit.color,
            count = habit.count,
            date = Instant.now().toEpochMilli().toInt(),
            description = habit.description,
            done_dates = listOf(0),
            frequency = habit.frequency.toInt(),
            priority = habit.priority.ordinal,
            title = habit.title,
            type = habit.type.ordinal,
            uid = habit.id.ifEmpty { null }
        )
    }

    fun toHabitModel() = HabitModel(
        id = uid ?: "",
        title = title,
        description = description,
        color = color,
        date = date,
        count = count,
        frequency = frequency,
        type = HabitType.values()[type],
        priority = HabitPriority.values()[priority],
        doneDates = done_dates
    )
}
