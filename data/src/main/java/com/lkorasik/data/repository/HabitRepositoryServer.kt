package com.lkorasik.data.repository

import android.util.Log
import com.lkorasik.data.net.RequestContext
import com.lkorasik.data.room.HabitEntity
import com.lkorasik.domain.entities.HabitPriority
import com.lkorasik.domain.entities.HabitType
import com.lkorasik.data.dto.HabitDTO
import com.lkorasik.data.dto.HabitDoneDTO
import java.time.Instant

class HabitRepositoryServer {
    fun getAllHabitsEntity(): List<HabitEntity> {
        val habits = RequestContext.API.getHabits().execute()

        if (!habits.isSuccessful)
            return listOf()

        return habits.body()?.map { habit -> HabitEntity.from(habit) } ?: listOf()
    }

    fun getAllHabitsDTO(): List<HabitDTO>? {
        var response: List<HabitDTO>? = null

        try {
            response = RequestContext.API.getHabits().execute().body()
        } catch (exception: RuntimeException) {
            Log.i(this::class.qualifiedName, "Get error from request! \n $response")
        }

        return response
    }

    suspend fun addHabit(habit: HabitEntity) {
        sendHabit(habit)
    }

    suspend fun updateHabit(habit: HabitEntity) {
        sendHabit(habit)
    }

    fun doneHabit(habit: HabitEntity) {
        val dto = HabitDoneDTO.from(habit)

        RequestContext.API.habitDone(dto).execute()
    }

    private suspend fun sendHabit(habit: HabitEntity) {
        val dto = HabitDTO.from(habit)

        RequestContext.API.saveHabit(dto)
    }
}
