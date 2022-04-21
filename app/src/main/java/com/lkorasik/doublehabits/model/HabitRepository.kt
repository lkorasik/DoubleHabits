package com.lkorasik.doublehabits.model

import androidx.lifecycle.LiveData
import com.lkorasik.doublehabits.room.HabitDao

class HabitRepository(private val dao: HabitDao) {
    fun getAllHabits(): LiveData<List<Habit>> = dao.getAll()
    fun addHabit(habit: Habit) = dao.insertAll(habit)
    fun editHabit(habit: Habit) = dao.update(habit)
    fun getHabit(position: Long): Habit = dao.getById(position) // TODO: LiveData
}
