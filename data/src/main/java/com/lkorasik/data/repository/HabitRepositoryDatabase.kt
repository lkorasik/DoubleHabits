package com.lkorasik.data.repository

import com.lkorasik.data.room.HabitDao
import com.lkorasik.data.room.HabitEntity

class HabitRepositoryDatabase(private val dao: HabitDao) {
    fun update(habit: HabitEntity) {
        dao.update(habit)
    }
}
