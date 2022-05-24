package com.lkorasik.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [HabitEntity::class], version = 1)
@TypeConverters(InstantConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "DoubleHabit.db"

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null)
                        INSTANCE = createDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun createDatabase(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                .allowMainThreadQueries()
                .build()
        }
    }
}
