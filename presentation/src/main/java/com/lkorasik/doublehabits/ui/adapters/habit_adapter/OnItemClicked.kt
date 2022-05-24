package com.lkorasik.doublehabits.ui.adapters.habit_adapter

import com.lkorasik.data.room.HabitEntity

fun interface OnItemClicked {
    fun onClick(habit: HabitEntity, position: Int)
}
