package com.lkorasik.data.dto

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
    val priority: Int, // Enum: 0, 1, 2
    val type: Int, // Enum: 0, 1
) {
    companion object {
//        fun from(habit: Habit): HabitDTO {
//            return HabitDTO(
//                color = habit.color,
//                count = habit.count,
//                date = Instant.now().toEpochMilli().toInt(),
//                description = habit.description,
//                done_dates = listOf(0),
//                frequency = habit.frequency.toInt(),
//                priority = habit.priority.ordinal,
//                title = habit.title,
//                type = habit.type.ordinal,
//                uid = habit.id.ifEmpty { null }
//            )
//        }
    }
}
