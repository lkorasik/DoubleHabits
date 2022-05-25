package com.lkorasik.domain.entities

import android.os.Parcelable
import com.lkorasik.domain.HabitPriority
import com.lkorasik.domain.HabitType
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
) : Parcelable
