package com.lkorasik.doublehabits.view_model

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.model.HabitRepository
import com.lkorasik.doublehabits.model.HabitType
import java.time.Instant

class EditorViewModel: ViewModel() {
    private val selectedHabit: MutableLiveData<Habit> = MutableLiveData(null)
    private val selectedColor = MutableLiveData(Color.HSVToColor(floatArrayOf(11.25f, 1f, 1f)))
    private var old: HabitType? = null
    private var position: Int? = null
    var createdAt: Instant? = null

    fun getSelectedHabit(): LiveData<Habit> = selectedHabit
    fun getPosition() = position
    fun getType() = old

    fun setHabitType(type: HabitType) {
        old = type
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    fun loadHabit(type: HabitType, position: Int) {
        val habit = HabitRepository.getHabit(type, position)
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
        HabitRepository.addHabit(habit)
    }

    fun moveHabit(habit: Habit) {
        when (habit.type) {
            HabitType.REGULAR -> HabitRepository.deleteHabit(habit.copy(type = HabitType.HARMFUL), getPosition()!!)
            HabitType.HARMFUL -> HabitRepository.deleteHabit(habit.copy(type = HabitType.REGULAR), getPosition()!!)
        }
        HabitRepository.addHabit(habit)
    }

    fun editHabit(habit: Habit) {
        HabitRepository.editHabit(habit, getPosition()!!)
    }
}
