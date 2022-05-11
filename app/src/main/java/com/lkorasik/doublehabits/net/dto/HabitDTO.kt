package com.lkorasik.doublehabits.net.dto

import com.lkorasik.doublehabits.model.HabitPriority
import com.lkorasik.doublehabits.model.HabitType

data class HabitDTO(
    val color: Int,
    val count: Int,
    val date: Int,
    val description: String,
    val done_dates: List<Int>,
    val frequency: Int,
//    val priority: HabitPriority,
    val priority: Int, // Enum: 0, 1, 2
    val title: String,
    val type: Int, // Enum: 0, 1
//    val type: HabitType,
    val uid: String?
)
