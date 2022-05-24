package com.lkorasik.doublehabits.ui.adapters.habit_adapter

import com.lkorasik.domain.Habit

fun interface OnItemClicked {
    fun onClick(habit: com.lkorasik.domain.Habit, position: Int)
}
