package com.lkorasik.doublehabits.model

import android.content.Context
import com.lkorasik.doublehabits.R

enum class HabitType {
    REGULAR, HARMFUL;

    fun getRepresentation(context: Context): String {
        return when (ordinal) {
            REGULAR.ordinal -> context.getString(R.string.add_habit_habit_type_regular)
            HARMFUL.ordinal -> context.getString(R.string.add_habit_habit_type_harmful)
            else -> throw IllegalStateException("Incorrect ordinal value")
        }
    }
}