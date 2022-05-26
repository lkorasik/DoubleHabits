package com.lkorasik.data.repository

import com.lkorasik.data.room.HabitDao
import com.lkorasik.data.room.HabitEntity
import com.lkorasik.domain.Repository
import com.lkorasik.domain.entities.HabitModel
import com.lkorasik.domain.entities.HabitType
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(private val dao: HabitDao): Repository {
    private val database: HabitRepositoryDatabase = HabitRepositoryDatabase(dao)
    private val network: HabitRepositoryServer = HabitRepositoryServer()

    override fun getAllHabits(): Flow<List<HabitModel>> = database.getAllHabits()
    override suspend fun addHabit(habit: HabitModel) = addHabit(HabitEntity.from(habit))
    override suspend fun editHabit(habit: HabitModel) = editHabit(HabitEntity.from(habit))

    override suspend fun loadHabits() {
        network.getAllHabitsDTO()?.forEach { database.update(it.toHabitModel()) }
    }

    override suspend fun doneHabit(habit: HabitModel): String {
        network.doneHabit(HabitEntity.from(habit))
        reloadDatabase()

        val storedHabit = database.getHabitById(habit.id)
        val hours = storedHabit.frequency.toInt()
        val now = Instant.now().nano
        val list = storedHabit.done_dates.sorted().filter { it - now < hours }

        return selectMessage(storedHabit, list)
    }

    private fun <T> selectMessage(habit: HabitEntity, list: List<T>): String {
        return if (habit.type == HabitType.REGULAR) {
            if (list.size <= habit.count)
                "Можете выполнить еще несколько раз"
            else
                "Хватит это делать"
        } else {
            if (list.size <= habit.count)
                "Стоит выполнить еще несколько раз"
            else
                "You are breathtaking!"
        }
    }

    private fun reloadDatabase() = network.getAllHabitsEntity().forEach { database.update(it) }

    suspend fun addHabit(habit: HabitEntity) {
        network.addHabit(habit)
        reloadDatabase()
    }

    suspend fun editHabit(habit: HabitEntity) {
        network.updateHabit(habit)
        reloadDatabase()
    }
}



