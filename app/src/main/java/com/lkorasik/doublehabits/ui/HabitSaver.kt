package com.lkorasik.doublehabits.ui

import com.lkorasik.doublehabits.model.Habit

interface HabitSaver { //HabitViewContainer
    fun saveHabit(habit: Habit, position: Int)
    fun saveHabit(habit: Habit)
}