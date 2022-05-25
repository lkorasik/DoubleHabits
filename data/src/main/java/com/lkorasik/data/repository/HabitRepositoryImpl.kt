package com.lkorasik.data.repository

import androidx.lifecycle.MutableLiveData
import com.lkorasik.data.room.HabitDao
import com.lkorasik.data.room.HabitEntity
import com.lkorasik.domain.Repository
import com.lkorasik.domain.entities.HabitModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HabitRepositoryImpl(private val dao: HabitDao): Repository {
    private val database: HabitRepositoryDatabase = HabitRepositoryDatabase(dao)
    private val network: HabitRepositoryServer = HabitRepositoryServer()

    private val liveData = MutableLiveData<List<HabitEntity>>()

    override fun getAllHabits(): Flow<List<HabitModel>> {
        reloadDatabase()

        //todo: верни ld из базы
//        return liveData

        return dao.getAll().map { it.map { entity ->
            HabitModel(
                id = entity.id,
                name = entity.title,
                description = entity.description,
                color = entity.color,
                date = entity.createdAt.nano,
                countRepeats = entity.count,
                interval = entity.frequency.toIntOrNull() ?: 0,
                type = entity.type,
                priority = entity.priority,
            )
        }
        }
    }

    override suspend fun addHabit(habit: HabitModel) {
        TODO("Not yet implemented")
    }

    override suspend fun editHabit(habit: HabitModel) {
        TODO("Not yet implemented")
    }

    private fun reloadDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val habits = network.getAllHabits()

            for (habit in habits) {
                database.update(habit)
            }

            liveData.postValue(habits) //todo: убери лишнюю LD, getAllHabits: LD
        }
    }

    suspend fun addHabit(habit: HabitEntity) {
        network.addHabit(habit)
        reloadDatabase()
    }

    suspend fun editHabit(habit: HabitEntity) {
        network.updateHabit(habit)
        reloadDatabase()
    }

//    suspend fun deleteHabit(habituidDto: HabitUID_DTO) {
//        network.deleteHabit(habituidDto)
//        reloadDatabase()
//    }
}



