package com.lkorasik.doublehabits.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.model.HabitType

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAll(): LiveData<List<Habit>>

    @Insert
    fun insertAll(vararg habit: Habit) //todo: overload list + single item

    @Update
    fun update(habit: Habit)

    @Query("SELECT * FROM habits WHERE id=:id ")
    fun getById(id: Long): Habit

    @Query("DELETE FROM habits")
    fun clear()
}
