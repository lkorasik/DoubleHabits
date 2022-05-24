package com.lkorasik.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RequestContext {
    private val doubleHabitsAPI: DoubleHabitsAPI
    private const val BASE_URL = "https://droid-test-server.doubletapp.ru/"

    val API: DoubleHabitsAPI
        get() = doubleHabitsAPI

    private const val AUTHORIZATION_HEADER = "Authorization"

    init {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val client = OkHttpClient()
            .newBuilder()
            .addInterceptor {
                val newRequest = it
                    .request()
                    .newBuilder()
                    .addHeader(AUTHORIZATION_HEADER, APIKey.authorizationToken)
                    .build()

                it.proceed(newRequest)
            }
            .build()

        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        doubleHabitsAPI = retrofit.create(DoubleHabitsAPI::class.java)
    }
}
