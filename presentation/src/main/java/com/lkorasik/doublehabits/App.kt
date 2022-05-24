package com.lkorasik.doublehabits

import android.app.Application
import android.content.Context
import com.lkorasik.data.repository.HabitRepository
import com.lkorasik.data.room.AppDatabase

class App : Application() {
    private val database by lazy { AppDatabase.getInstance(this) }
    val repository: HabitRepository by lazy { HabitRepository(database.habitDao()) }

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