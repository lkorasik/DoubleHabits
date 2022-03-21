package com.lkorasik.doublehabits.model

import com.lkorasik.doublehabits.R

enum class HabitPriority {
    LOW, MEDIUM, HIGH;

    val resource: Int
        get() {
            return when (this) {
                LOW -> R.string.add_habit_habit_priority_low
                MEDIUM -> R.string.add_habit_habit_priority_medium
                HIGH -> R.string.add_habit_habit_priority_high
            }
        }
}
