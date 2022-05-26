package com.lkorasik.data

import android.content.Context
import com.lkorasik.data.repository.HabitRepositoryImpl
import com.lkorasik.data.room.AppDatabase
import com.lkorasik.domain.HabitsUseCase
import com.lkorasik.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [Deps.Declarations::class])
class Deps(private val context: Context) {
    @Provides
    fun provideHabitDao(database: AppDatabase) = database.habitDao()

    @Provides
    fun provideDatabase() = AppDatabase.getInstance(context.applicationContext)

    @Module
    interface Declarations {

        @Binds
        fun bindHabitsRepository(repository: HabitRepositoryImpl): Repository
    }
}
