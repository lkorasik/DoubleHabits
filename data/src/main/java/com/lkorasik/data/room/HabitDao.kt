package com.lkorasik.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAll(): Flow<List<HabitEntity>>

    @Insert
    fun insertAll(vararg habit: HabitEntity)

    @Update
    fun update(habit: HabitEntity)

    @Query("SELECT * FROM habits WHERE id=:id ")
    fun getById(id: String): HabitEntity

    @Query("DELETE FROM habits")
    fun clear()
}
