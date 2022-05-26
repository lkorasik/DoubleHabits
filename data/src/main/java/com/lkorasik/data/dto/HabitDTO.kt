package com.lkorasik.data.dto

import com.lkorasik.domain.entities.HabitModel
import com.lkorasik.domain.entities.HabitPriority
import com.lkorasik.domain.entities.HabitType

data class HabitDTO(
    val uid: String?,
    val title: String,
    val color: Int,
    val count: Int,
    val date: Int,
    val description: String,
    val done_dates: List<Int>,
    val frequency: Int,
    val priority: Int,
    val type: Int
) {
    fun toHabitModel() = HabitModel(
        id = uid ?: "",
        title = title,
        description = description,
        color = color,
        date = date,
        count = count,
        frequency = frequency,
        type = HabitType.values()[type],
        priority = HabitPriority.values()[priority],
        doneDates = done_dates
    )
}
