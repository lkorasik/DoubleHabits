package com.lkorasik.data.repository

import com.lkorasik.data.room.HabitDao

class HabitRepositoryDatabase(private val dao: HabitDao) {
    fun update(habit: com.lkorasik.domain.Habit) {
        dao.update(habit)
    }
}
