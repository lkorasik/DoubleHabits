package com.lkorasik.data

import android.content.Context
import com.lkorasik.data.room.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class Deps(private val context: Context) {
    @Provides
    fun provideHabitDao(database: AppDatabase) = database.habitDao()

    @Provides
    fun provideDatabase() = AppDatabase.getInstance(context.applicationContext)
}
