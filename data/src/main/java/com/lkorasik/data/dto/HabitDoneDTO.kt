package com.lkorasik.data.dto

import com.lkorasik.data.room.HabitEntity
import java.time.Instant

data class HabitDoneDTO(
    private val date: Int,
    private val habit_uid: String
) {
    companion object {
        fun from(habit: HabitEntity) = HabitDoneDTO(
            date = Instant.now().toEpochMilli().toInt(),
            habit_uid = habit.id
        )
    }
}
