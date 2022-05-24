package com.lkorasik.data

import com.lkorasik.data.dto.HabitDTO
import com.lkorasik.data.dto.HabitDoneDTO
import com.lkorasik.data.dto.HabitUID_DTO
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface DoubleHabitsAPI {
    @PUT("api/habit")
    suspend fun createOrUpdateHabit(@Body habit: HabitDTO): HabitUID_DTO

    @GET("api/habit")
    fun getHabits(): Call<List<HabitDTO>?> //TOOD: use coroutine

//    @DELETE("api/habit")
    @HTTP(method = "DELETE", path = "api/habit", hasBody = true)
    fun deleteHabit(@Body habit: HabitUID_DTO): Call<ResponseBody?>

    @POST("api/habit_done")
    fun habitDone(habit: HabitDoneDTO)
}
