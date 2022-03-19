package com.lkorasik.doublehabits.model

import android.content.Context
import com.lkorasik.doublehabits.R

enum class HabitPriority {
    LOW, MEDIUM, HIGH;

    fun getRepresentation(context: Context): String {
        return when (ordinal) {
            LOW.ordinal -> context.getString(R.string.add_habit_habit_priority_low)
            MEDIUM.ordinal -> context.getString(R.string.add_habit_habit_priority_medium)
            HIGH.ordinal -> context.getString(R.string.add_habit_habit_priority_high)
            else -> throw IllegalStateException("Incorrect ordinal value")
        }
    }
}
