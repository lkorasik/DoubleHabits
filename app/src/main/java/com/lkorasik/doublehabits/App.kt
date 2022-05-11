package com.lkorasik.doublehabits

import android.app.Application
import android.content.Context
import com.lkorasik.doublehabits.model.CommonRepo
import com.lkorasik.doublehabits.model.HabitRepository
import com.lkorasik.doublehabits.room.AppDatabase

class App : Application() {
    private val database by lazy { AppDatabase.getInstance(this) }
//    val repository: HabitRepository by lazy { HabitRepository(database.habitDao()) }
    val repository: HabitRepository by lazy { CommonRepo(database.habitDao()) }

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