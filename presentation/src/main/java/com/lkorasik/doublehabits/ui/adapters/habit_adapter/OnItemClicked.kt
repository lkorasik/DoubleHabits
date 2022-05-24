package com.lkorasik.doublehabits.ui.adapters.habit_adapter

import com.lkorasik.doublehabits.model.Habit

fun interface OnItemClicked {
    fun onClick(habit: Habit, position: Int)
}
