package com.lkorasik.doublehabits.net

import com.lkorasik.doublehabits.net.dto.HabitDTO
import com.lkorasik.doublehabits.net.dto.HabitDoneDTO
import com.lkorasik.doublehabits.net.dto.HabitUID_DTO
import retrofit2.http.*

interface DoubleHabitsAPI {
    @PUT("api/habit")
    suspend fun createOrUpdateHabit(@Header(HeadersKeys.AUTHORIZATION) token: String, @Body habit: HabitDTO): HabitUID_DTO

    @GET("api/habit")
    suspend fun getHabits(@Header(HeadersKeys.AUTHORIZATION) token: String): List<HabitDTO>

    @DELETE("api/habit")
    fun deleteHabit(habit: HabitUID_DTO)

    @POST("api/habit_done")
    fun habitDone(habit: HabitDoneDTO)
}

object HeadersKeys {
    const val AUTHORIZATION = "Authorization"
}
