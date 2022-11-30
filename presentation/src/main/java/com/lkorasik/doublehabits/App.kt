package com.lkorasik.doublehabits

import android.app.Application
import android.content.Context
import android.util.Log
import com.lkorasik.data.Deps
import com.lkorasik.data.repository.HabitRepositoryImpl
import com.lkorasik.data.room.AppDatabase
import com.lkorasik.domain.HabitsUseCase

class App : Application() {
//    private val database by lazy { AppDatabase.getInstance(this) }
//    val repository: HabitRepositoryImpl by lazy { HabitRepositoryImpl(database.habitDao()) }
//    val habitsUseCase: HabitsUseCase by lazy { HabitsUseCase(HabitRepositoryImpl(database.habitDao())) }

    lateinit var component: AppComponent

    companion object {
        private var instance: Application? = null

//        fun getInstance(): Application? = instance
//        fun getContext(): Context? = instance?.applicationContext
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder().deps(Deps(applicationContext)).build()

//        component = DaggerAppComponent.create()

        Log.i("APP", component.habit.toString())

        instance = this
    }
}

val Context.component: AppComponent
    get() = when(this) {
        is App -> component
        else -> applicationContext.component
    }