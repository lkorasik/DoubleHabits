package com.lkorasik.doublehabits.model.repository

import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.room.HabitDao

class HabitRepositoryDatabase(private val dao: HabitDao) {
    fun update(habit: Habit) {
        dao.update(habit)
    }
}
