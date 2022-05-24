package com.lkorasik.data.repository

import androidx.lifecycle.MutableLiveData
import com.lkorasik.data.room.HabitDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitRepository(dao: HabitDao) {
    private val database: HabitRepositoryDatabase = HabitRepositoryDatabase(dao)
    private val network: HabitRepositoryServer = HabitRepositoryServer()

    private val liveData = MutableLiveData<List<com.lkorasik.domain.Habit>>()

    fun getAllHabits(): MutableLiveData<List<com.lkorasik.domain.Habit>> {
        reloadDatabase()

        //todo: верни ld из базы
        return liveData
    }

    private fun reloadDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val habits = network.getAllHabits()

            for (habit in habits) {
                database.update(habit)
            }

            liveData.postValue(habits)//todo: убери лишнюю LD, getAllHabits: LD
        }
    }

    suspend fun addHabit(habit: com.lkorasik.domain.Habit) {
        network.addHabit(habit)
        reloadDatabase()
    }

    suspend fun editHabit(habit: com.lkorasik.domain.Habit) {
        network.updateHabit(habit)
        reloadDatabase()
    }

//    suspend fun deleteHabit(habituidDto: HabitUID_DTO) {
//        network.deleteHabit(habituidDto)
//        reloadDatabase()
//    }
}



