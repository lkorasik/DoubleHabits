package com.lkorasik.doublehabits.habit_adapter

import com.lkorasik.doublehabits.Habit

fun interface OnItemClicked {
    fun onClick(habit: Habit, position: Int)
}
