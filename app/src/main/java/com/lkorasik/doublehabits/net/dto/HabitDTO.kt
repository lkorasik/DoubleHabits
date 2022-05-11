package com.lkorasik.doublehabits.net.dto

import com.lkorasik.doublehabits.model.HabitPriority
import com.lkorasik.doublehabits.model.HabitType

data class HabitDTO(
    private val color: Int,
    private val count: Int,
    private val date: Int,
    private val description: String,
    private val done_dates: List<Int>,
    private val frequency: Int,
//    private val priority: HabitPriority,
    private val priority: Int, // Enum: 0, 1, 2
    private val title: String,
    private val type: Int, // Enum: 0, 1
//    private val type: HabitType,
    private val uid: String?
)
