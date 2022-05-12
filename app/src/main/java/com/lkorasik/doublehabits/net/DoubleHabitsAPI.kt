package com.lkorasik.doublehabits.net

import com.lkorasik.doublehabits.net.dto.HabitDTO
import com.lkorasik.doublehabits.net.dto.HabitDoneDTO
import com.lkorasik.doublehabits.net.dto.HabitUID_DTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface DoubleHabitsAPI {
    @PUT("api/habit")
    suspend fun createOrUpdateHabit(@Body habit: HabitDTO): HabitUID_DTO

    @GET("api/habit")
    fun getHabits(): Call<List<HabitDTO>?>

//    @DELETE("api/habit")
    @HTTP(method = "DELETE", path = "api/habit", hasBody = true)
    fun deleteHabit(@Body habit: HabitUID_DTO): Call<ResponseBody?>

    @POST("api/habit_done")
    fun habitDone(habit: HabitDoneDTO)
}
