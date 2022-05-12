package com.lkorasik.doublehabits.view_model

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.model.HabitRepository
import com.lkorasik.doublehabits.model.HabitType
import kotlinx.coroutines.launch
import java.time.Instant

class EditorViewModel(private val repository: HabitRepository): ViewModel() {
    private val selectedHabit: MutableLiveData<Habit> = MutableLiveData(null)
    private val selectedColor = MutableLiveData(Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f)))
    private var old: HabitType? = null
    private var position: Int? = null
    var createdAt: Instant? = null

    fun getSelectedHabit(): LiveData<Habit> = selectedHabit
    fun getPosition() = position

    fun setHabitType(type: HabitType) {
        old = type
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    fun loadHabit(habit: Habit) {
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
        viewModelScope.launch {
            repository.addHabit(habit)
        }
    }

    fun editHabit(habit: Habit) {
        viewModelScope.launch {
            repository.editHabit(habit)
        }
    }
}
