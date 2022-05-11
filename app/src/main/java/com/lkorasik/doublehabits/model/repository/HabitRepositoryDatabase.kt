package com.lkorasik.doublehabits.model.repository

import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.room.HabitDao

class HabitRepositoryDatabase(private val dao: HabitDao) {
//    fun getAllHabits(): LiveData<List<Habit>> = dao.getAll()
    fun getHabit(id: String): Habit = dao.getById(id)

    fun update(habit: Habit) {
        dao.update(habit)
    }
}
