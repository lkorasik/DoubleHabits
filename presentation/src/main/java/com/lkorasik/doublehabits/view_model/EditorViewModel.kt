package com.lkorasik.doublehabits.view_model

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lkorasik.data.repository.HabitRepository
import com.lkorasik.domain.HabitType
import kotlinx.coroutines.launch
import java.time.Instant

class EditorViewModel(private val repository: HabitRepository): ViewModel() {
    private val selectedHabit: MutableLiveData<com.lkorasik.domain.Habit> = MutableLiveData(null)
    private val selectedColor = MutableLiveData(Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f)))
    private var old: HabitType? = null
    private var position: Int? = null
    var createdAt: Instant? = null

    fun getSelectedHabit(): LiveData<com.lkorasik.domain.Habit> = selectedHabit
    fun getPosition() = position

    fun setHabitType(type: HabitType) {
        old = type
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    fun loadHabit(habit: com.lkorasik.domain.Habit) {
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

    fun addHabit(habit: com.lkorasik.domain.Habit) {
        viewModelScope.launch {
            repository.addHabit(habit)
        }
    }

    fun editHabit(habit: com.lkorasik.domain.Habit) {
        viewModelScope.launch {
            repository.editHabit(habit)
        }
    }
}
