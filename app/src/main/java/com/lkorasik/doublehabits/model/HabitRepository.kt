package com.lkorasik.doublehabits.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lkorasik.doublehabits.net.APIKey
import com.lkorasik.doublehabits.net.RequestContext
import com.lkorasik.doublehabits.net.dto.HabitDTO
import com.lkorasik.doublehabits.room.HabitDao
import java.time.Instant

class CommonRepo(dao: HabitDao): HabitRepository {
    private val database: HabitRepositoryDatabase = HabitRepositoryDatabase(dao)
    private val network: HabitRepositoryServer = HabitRepositoryServer()

    override suspend fun getAllHabits(): LiveData<List<Habit>> {
//        return dao.getAll()
//        return database.getAllHabits()
        return network.getAllHabits()
    }

    override fun getHabit(position: Long): Habit {
//        return dao.getById(position)
        return database.getHabit(position)
    }

    override suspend fun addHabit(habit: Habit) {
//        database.addHabit(habit)
        network.addHabit(habit)
    }

    override fun editHabit(habit: Habit) {
//        dao.update(habit)
        database.editHabit(habit)
    }
}

interface HabitRepository {
    suspend fun getAllHabits(): LiveData<List<Habit>>
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
        )
        RequestContext.API.createOrUpdateHabit(APIKey.authorizationToken, dto)
    }

    suspend fun getAllHabits(): LiveData<List<Habit>> {
        val list = RequestContext.API.getHabits(APIKey.authorizationToken).map {
            Habit(
                id = 0,
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

        return MutableLiveData(list)
    }
}