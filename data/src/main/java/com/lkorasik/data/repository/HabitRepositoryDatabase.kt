package com.lkorasik.data.repository

import android.util.Log
import com.lkorasik.data.room.HabitDao
import com.lkorasik.data.room.HabitEntity
import com.lkorasik.domain.entities.HabitModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HabitRepositoryDatabase(private val dao: HabitDao) {
    fun update(habit: HabitEntity) {
        dao.update(habit)
    }

    fun getAllHabits(): Flow<List<HabitModel>> = dao
        .getAll()
        .map { it.map { entity -> entity.toModel() } }

    suspend fun update(habit: HabitModel) {
        val existingHabit = dao.checkExistHabit(habit.id)

        if (existingHabit != null) {
            if (existingHabit.date < habit.date) {
                dao.update(HabitEntity.from(habit))
            }
        } else {
            Log.i(HabitRepositoryImpl::class.java.name, "${habit.title} new habit.")
            dao.insertAll(HabitEntity.from(habit))
        }
    }
}
