package com.lkorasik.doublehabits.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lkorasik.doublehabits.net.RequestContext
import com.lkorasik.doublehabits.net.dto.HabitDTO
import com.lkorasik.doublehabits.room.HabitDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant

class HabitRepository(private val dao: HabitDao) {
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

            //TODO("Лови исколючения сети")

            for (habit in habits) {
                dao.update(habit)
            }

            liveData.postValue(habits)
        }
    }

    suspend fun addHabit(habit: Habit) {
        Log.i("APP", "Add ${habit.id}")
        network.addHabit(habit)
        reloadDatabase()
    }

    fun getHabit(position: Int): Habit {
//        return dao.getById(position)
//        return database.getHabit(position)
//        return network.getAllHabits()[position]
        return database.getHabit(position.toLong())
    }

    fun editHabit(habit: Habit) {
        Log.i("APP", "Edit ${habit.id}")
//        dao.update(habit)
//        database.editHabit(habit)
    }
}

class HabitRepositoryDatabase(private val dao: HabitDao) {
    fun clear() = dao.clear()
    fun getAllHabits(): LiveData<List<Habit>> = dao.getAll()
    fun getHabit(position: Long): Habit = dao.getById(position)

    fun addHabit(habit: Habit) {
        dao.insertAll(habit)
    }

    fun editHabit(habit: Habit) {
        dao.update(habit)
    }
}

class HabitRepositoryServer {
    suspend fun addHabit(habit: Habit) {
        val dto = HabitDTO(
            color = 0,
            count = 0,
            date = 0,
            description = habit.description,
            done_dates = listOf(0),
            frequency = 0,
            priority = 0,
            title = habit.name,
            type = 0,
            uid = null
        )
        RequestContext.API.createOrUpdateHabit(dto)
    }

    suspend fun getAllHabits(): List<Habit> {
        return RequestContext.API.getHabits().map {
            Habit(
                id = it.uid!!,
                name = it.title,
                description = it.description,
                priority = HabitPriority.values()[it.priority],
                type = HabitType.values()[it.type],
                periodicity = it.frequency.toString(),
                color = it.color,
                count = it.count,
                createdAt = Instant.now(),
                lastEditedAt = Instant.now()
            )
        }
    }
}