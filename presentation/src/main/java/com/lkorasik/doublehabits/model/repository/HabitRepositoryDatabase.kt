package com.lkorasik.doublehabits.model.repository

import com.lkorasik.domain.Habit
import com.lkorasik.doublehabits.room.HabitDao

class HabitRepositoryDatabase(private val dao: HabitDao) {
    fun update(habit: com.lkorasik.domain.Habit) {
        dao.update(habit)
    }
}
