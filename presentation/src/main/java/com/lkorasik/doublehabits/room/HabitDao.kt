package com.lkorasik.doublehabits.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.lkorasik.doublehabits.model.Habit

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAll(): LiveData<List<Habit>>

    @Insert
    fun insertAll(vararg habit: Habit)

    @Update
    fun update(habit: Habit)

    @Query("SELECT * FROM habits WHERE id=:id ")
    fun getById(id: String): Habit

    @Query("DELETE FROM habits")
    fun clear()
}
