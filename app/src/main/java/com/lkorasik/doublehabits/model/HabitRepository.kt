package com.lkorasik.doublehabits.model

import androidx.lifecycle.LiveData
import com.lkorasik.doublehabits.net.APIKey
import com.lkorasik.doublehabits.net.RequestContext
import com.lkorasik.doublehabits.net.dto.HabitDTO
import com.lkorasik.doublehabits.room.HabitDao

class CommonRepo(private val dao: HabitDao): HabitRepository {
    private val database: HabitRepositoryDatabase = HabitRepositoryDatabase(dao)
    private val network: HabitRepositoryServer = HabitRepositoryServer()

    override fun getAllHabits(): LiveData<List<Habit>> {
        return dao.getAll()
    }

    override fun getHabit(position: Long): Habit {
        return dao.getById(position)
    }

    override suspend fun addHabit(habit: Habit) {
//        database.addHabit(habit)
        network.addHabit(habit)
    }

    override fun editHabit(habit: Habit) {
        dao.update(habit)
    }
}

interface HabitRepository {
    fun getAllHabits(): LiveData<List<Habit>>
    fun getHabit(position: Long): Habit
    suspend fun addHabit(habit: Habit)
    fun editHabit(habit: Habit)
}

class HabitRepositoryDatabase(private val dao: HabitDao) {
    fun getAllHabits(): LiveData<List<Habit>> = dao.getAll()
    fun getHabit(position: Long): Habit = dao.getById(position)

    fun addHabit(habit: Habit) {
        dao.insertAll(habit)
    }

    fun editHabit(habit: Habit) {
        dao.update(habit)
    }
}

class HabitRepositoryServer() {
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
//            color = habit.color,
//            count = habit.count,
//            date = habit.lastEditedAt.toEpochMilli().toInt(),
//            description = habit.description,
//            done_dates = listOf(0),
//            frequency = habit.periodicity.toInt(),
//            priority = habit.priority,
//            title = habit.name,
//            type = habit.type,
//            uid = null
        )
        RequestContext.API.createOrUpdateHabit(APIKey.authorizationToken, dto)
    }
}