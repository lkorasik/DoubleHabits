package com.lkorasik.doublehabits.model

import androidx.lifecycle.MutableLiveData
import com.lkorasik.doublehabits.model.repository.HabitRepositoryDatabase
import com.lkorasik.doublehabits.model.repository.HabitRepositoryServer
import com.lkorasik.doublehabits.room.HabitDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitRepository(dao: HabitDao) {
    private val database: HabitRepositoryDatabase = HabitRepositoryDatabase(dao)
    private val network: HabitRepositoryServer = HabitRepositoryServer()

    private val liveData = MutableLiveData<List<Habit>>()

    fun getAllHabits(): MutableLiveData<List<Habit>> {
        reloadDatabase()

        return liveData
    }

    private fun reloadDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val habits = network.getAllHabits()

            for (habit in habits) {
                database.update(habit)
            }

            liveData.postValue(habits)
        }
    }

    suspend fun addHabit(habit: Habit) {
        network.addHabit(habit)
        reloadDatabase()
    }

    suspend fun editHabit(habit: Habit) {
        network.updateHabit(habit)
        reloadDatabase()
    }
}



