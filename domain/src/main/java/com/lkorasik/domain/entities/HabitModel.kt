package com.lkorasik.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HabitModel(
    var id: String,
    var name: String,
    var description: String,
    var color: Int,
    var date: Int,
    var countRepeats: Int,
    var interval: Int,
    var type: HabitType,
    var priority: HabitPriority,
    var doneDates: List<Int>
) : Parcelable
