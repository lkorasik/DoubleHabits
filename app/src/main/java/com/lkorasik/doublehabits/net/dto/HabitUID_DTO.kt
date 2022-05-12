package com.lkorasik.doublehabits.net.dto

import com.lkorasik.doublehabits.model.Habit

data class HabitUID_DTO(
    private val uid: String
) {
    companion object {
        fun from(habit: Habit): HabitUID_DTO {
            return HabitUID_DTO(habit.id)
        }
    }
}
