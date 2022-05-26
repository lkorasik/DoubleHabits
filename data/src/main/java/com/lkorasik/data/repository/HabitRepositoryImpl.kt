package com.lkorasik.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.lkorasik.data.R
import com.lkorasik.data.net.RequestContext
import com.lkorasik.data.room.HabitDao
import com.lkorasik.data.room.HabitEntity
import com.lkorasik.domain.Repository
import com.lkorasik.domain.entities.HabitModel
import com.lkorasik.domain.entities.HabitType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.RuntimeException
import java.time.Instant
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(private val dao: HabitDao): Repository {
    private val database: HabitRepositoryDatabase = HabitRepositoryDatabase(dao)
    private val network: HabitRepositoryServer = HabitRepositoryServer()

    private val liveData = MutableLiveData<List<HabitEntity>>()

    override fun getAllHabits(): Flow<List<HabitModel>> {
        return dao.getAll().map { it.map { entity -> entity.toModel() } }
    }

    override suspend fun loadHabits() {
        try {
            val response = RequestContext.API.getHabits().execute().body()
            Log.i(HabitRepositoryImpl::class.java.name, response.toString())

            response?.forEach {
                updateLocalDatabase(it.toHabitModel())
            }
        } catch (e: RuntimeException) { }
    }

    private suspend fun updateLocalDatabase(habit: HabitModel) {
        val existingHabit = dao.checkExistHabit(habit.id)
        if (existingHabit != null) {
            if (existingHabit.date < habit.date) {
                dao.update(HabitEntity.fromModel(habit))
            }
        } else {
            Log.i(HabitRepositoryImpl::class.java.name, "${habit.title} new habit.")
            dao.insertAll(HabitEntity.fromModel(habit))
        }
    }

    override suspend fun addHabit(habit: HabitModel) {
        val h = HabitEntity.fromModel(habit)
        addHabit(h)
    }

    override suspend fun editHabit(habit: HabitModel) {
        val h = HabitEntity.fromModel(habit)
        editHabit(h)
    }

    override suspend fun doneHabit(habit: HabitModel): String {
        network.doneHabit(HabitEntity.fromModel(habit))
        reloadDatabase()

        val habit = dao.getById(habit.id)
        val hours = habit.frequency.toInt()
        val now = Instant.now().nano
        val list = habit.done_dates.sorted().filter { it - now < hours }

        if (habit.type == HabitType.REGULAR) {
            return if (list.size <= habit.count){
                val message = "Можете выполнить еще несколько раз"
                message
            } else {
                val message = "Хватит это делать"
                message
            }
        }
        else {
            return if (list.size <= habit.count){
                val message = "Стоит выполнить еще несколько раз"
                message
            } else {
                val message = "You are breathtaking!"
                message
            }
        }
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
}



