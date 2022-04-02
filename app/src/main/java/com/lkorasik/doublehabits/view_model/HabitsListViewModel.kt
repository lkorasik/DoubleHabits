package com.lkorasik.doublehabits.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.model.HabitRepository
import com.lkorasik.doublehabits.model.HabitType

class HabitsListViewModel: ViewModel() {
    private val data = MutableLiveData(listOf<Habit>())
    private lateinit var type: HabitType

    private var filter = { h: Habit -> true }
    private var habitComparator: Comparator<Habit> = Comparator { _: Habit, _: Habit -> 0 }

    private fun acceptFilter() {
        data.postValue(HabitRepository.getHabits(type).filter(filter).sortedWith(habitComparator))
    }

    fun setHabitComparator(comparator: Comparator<Habit>): HabitsListViewModel {
        habitComparator = comparator
        acceptFilter()

        return this
    }

    fun setFilter(func: (Habit) -> Boolean): HabitsListViewModel {
        filter = func
        acceptFilter()

        return this
    }

    fun getHabits(): LiveData<List<Habit>> {
        val list = data.value?.filter(filter)?.sortedWith(habitComparator)
        data.value = list
        return data
    }

    fun loadHabits(type: HabitType) {
        this.type = type
        acceptFilter()
    }
}