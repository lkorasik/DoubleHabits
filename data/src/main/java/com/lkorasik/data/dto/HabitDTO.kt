package com.lkorasik.data.dto

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
)
