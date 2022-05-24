package com.lkorasik.doublehabits.net.dto

import com.lkorasik.domain.Habit

data class HabitUID_DTO(
    private val uid: String
) {
    companion object {
        fun from(habit: com.lkorasik.domain.Habit): HabitUID_DTO {
            return HabitUID_DTO(habit.id)
        }
    }
}
