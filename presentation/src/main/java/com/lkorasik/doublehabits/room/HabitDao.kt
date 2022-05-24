package com.lkorasik.doublehabits.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.lkorasik.domain.Habit

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAll(): LiveData<List<com.lkorasik.domain.Habit>>

    @Insert
    fun insertAll(vararg habit: com.lkorasik.domain.Habit)

    @Update
    fun update(habit: com.lkorasik.domain.Habit)

    @Query("SELECT * FROM habits WHERE id=:id ")
    fun getById(id: String): com.lkorasik.domain.Habit

    @Query("DELETE FROM habits")
    fun clear()
}
