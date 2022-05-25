package com.lkorasik.doublehabits.view_model

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lkorasik.data.repository.HabitRepositoryImpl
import com.lkorasik.data.room.HabitEntity
import com.lkorasik.domain.entities.HabitType
import kotlinx.coroutines.launch
import java.time.Instant

class EditorViewModel(private val repository: HabitRepositoryImpl): ViewModel() {
    private val selectedHabit: MutableLiveData<HabitEntity> = MutableLiveData(null)
    private val selectedColor = MutableLiveData(Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f)))
    private var old: HabitType? = null
    private var position: Int? = null
    var createdAt: Instant? = null

    fun getSelectedHabit(): LiveData<HabitEntity> = selectedHabit
    fun getPosition() = position

    fun setHabitType(type: HabitType) {
        old = type
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    fun loadHabit(habit: HabitEntity) {
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

    fun addHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.addHabit(habit)
        }
    }

    fun editHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.editHabit(habit)
        }
    }
}
