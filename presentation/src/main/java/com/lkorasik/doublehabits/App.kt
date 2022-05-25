package com.lkorasik.doublehabits

import android.app.Application
import android.content.Context
import com.lkorasik.data.repository.HabitRepositoryImpl
import com.lkorasik.data.room.AppDatabase
import com.lkorasik.domain.HabitsUseCase

class App : Application() {
    private val database by lazy { AppDatabase.getInstance(this) }
    val repository: HabitRepositoryImpl by lazy { HabitRepositoryImpl(database.habitDao()) }
    val habitsUseCase: HabitsUseCase by lazy { HabitsUseCase(HabitRepositoryImpl(database.habitDao())) }

    companion object {
        private var instance: Application? = null

        fun getInstance(): Application? = instance
        fun getContext(): Context? = instance?.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}