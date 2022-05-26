package com.lkorasik.data.repository

import androidx.lifecycle.MutableLiveData
import com.lkorasik.data.room.HabitDao
import com.lkorasik.data.room.HabitEntity
import com.lkorasik.domain.Repository
import com.lkorasik.domain.entities.HabitModel
import com.lkorasik.domain.entities.HabitType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(private val dao: HabitDao): Repository {
    private val database: HabitRepositoryDatabase = HabitRepositoryDatabase(dao)
    private val network: HabitRepositoryServer = HabitRepositoryServer()

    private val liveData = MutableLiveData<List<HabitEntity>>()

    override fun getAllHabits(): Flow<List<HabitModel>> = database.getAllHabits()

    override suspend fun loadHabits() {
        network.getAllHabits()?.forEach { database.update(it.toHabitModel()) }
    }

    override suspend fun addHabit(habit: HabitModel) {
//        addHabit(HabitEntity.from(habit))
        val convertedHabit = HabitEntity.from(habit)
        network.addHabit(convertedHabit)
        reloadDatabase()
    }

    override suspend fun editHabit(habit: HabitModel) {
        editHabit(HabitEntity.from(habit))
    }

    override suspend fun doneHabit(habit: HabitModel): String {
        network.doneHabit(HabitEntity.from(habit))
        reloadDatabase()

        val habit = dao.getById(habit.id)
        val hours = habit.frequency.toInt()
        val now = Instant.now().nano
        val list = habit.done_dates.sorted().filter { it - now < hours }

        if (habit.type == HabitType.REGULAR) {
            return if (list.size <= habit.count){
                "Можете выполнить еще несколько раз"
            } else {
                "Хватит это делать"
            }
        }
        else {
            return if (list.size <= habit.count){
                "Стоит выполнить еще несколько раз"
            } else {
                "You are breathtaking!"
            }
        }
    }

    private fun reloadDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val habits = network.getAllHabits2()

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



