package com.lkorasik.doublehabits.net

import com.lkorasik.doublehabits.net.dto.HabitDTO
import com.lkorasik.doublehabits.net.dto.HabitDoneDTO
import com.lkorasik.doublehabits.net.dto.HabitUID_DTO
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface DoubleHabitsAPI {
    @PUT("api/habit")
    fun createOrUpdateHabit(habit: HabitDTO)

    @GET("api/habit")
    fun getHabits(): List<HabitDTO>

    @DELETE("api/habit")
    fun deleteHabit(habit: HabitUID_DTO)

    @POST("api/habit_done")
    fun habitDone(habit: HabitDoneDTO)
}