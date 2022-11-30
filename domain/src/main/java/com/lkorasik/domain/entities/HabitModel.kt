package com.lkorasik.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HabitModel(
    var id: String,
    var title: String,
    var description: String,
    var color: Int,
    var date: Int,
    var count: Int,
    var frequency: Int,
    var type: HabitType,
    var priority: HabitPriority,
    var doneDates: List<Int>
) : Parcelable
