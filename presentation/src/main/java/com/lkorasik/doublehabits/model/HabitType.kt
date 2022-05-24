package com.lkorasik.doublehabits.model

import com.lkorasik.doublehabits.R

enum class HabitType {
    REGULAR, HARMFUL;

    val resource: Int
        get() {
            return when (this) {
                REGULAR -> R.string.add_habit_habit_type_regular
                HARMFUL -> R.string.add_habit_habit_type_harmful
            }
        }
}
