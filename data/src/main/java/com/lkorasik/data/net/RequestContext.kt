package com.lkorasik.data.net

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//TODO: inject it!
object RequestContext {
    private const val BASE_URL = "https://droid-test-server.doubletapp.ru/"

    private val gson = GsonBuilder()
        .setLenient()
        .create()
    private val client = OkHttpClient()
        .newBuilder()
        .addInterceptor(AuthInterceptor())
        .build()
    private val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    private val doubleHabitsAPI: DoubleHabitsAPI = retrofit.create(DoubleHabitsAPI::class.java)

    val API: DoubleHabitsAPI
        get() = doubleHabitsAPI
}
