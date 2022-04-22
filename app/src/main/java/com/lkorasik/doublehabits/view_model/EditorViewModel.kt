package com.lkorasik.doublehabits.view_model

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.model.HabitRepository
import com.lkorasik.doublehabits.model.HabitType
import java.time.Instant

class EditorViewModel(private val repository: HabitRepository): ViewModel() {
    private val selectedHabit: MutableLiveData<Habit> = MutableLiveData(null)
    private val selectedColor = MutableLiveData(Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f)))
    private var old: HabitType? = null //todo: убери, все в куче в бд
    private var position: Int? = null
    var createdAt: Instant? = null

    //TODO: передавай сюдай не Habit а ее id.
    fun getSelectedHabit(): LiveData<Habit> = selectedHabit
    fun getPosition() = position

    fun setHabitType(type: HabitType) {
        old = type
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    fun loadHabit(position: Long) {
        val habit = repository.getHabit(position)
        selectedHabit.postValue(habit)
    }

    fun createHabit() {
        old = null
        position = null
        createdAt = null
        selectedHabit.postValue(null)
    }

    fun setSelectedColor(color: Int) {
        selectedColor.postValue(color)
    }

    fun getSelectedColor(): Int {
        return selectedColor.value!!
    }

    fun addHabit(habit: Habit) {
        repository.addHabit(habit)
    }

    fun editHabit(habit: Habit) {
        repository.editHabit(habit)
    }
}
