package com.lkorasik.doublehabits.view_model

import androidx.lifecycle.*
import com.lkorasik.doublehabits.extensions.addLiveData
import com.lkorasik.doublehabits.extensions.map
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.model.HabitRepository
import com.lkorasik.doublehabits.model.HabitType

class HabitsListViewModel(repository: HabitRepository): ViewModel() {
    private val emptyPair = "" to false
    private val emptyComparator = Comparator { _: Habit, _: Habit -> 0 }

    private var filter = MutableLiveData(emptyPair)
    private var habitComparator = MutableLiveData(emptyComparator)

    private var data: LiveData<List<Habit>> = MutableLiveData(listOf())

    init {
        data = repository
            .getAllHabits()
            .addLiveData(filter)
            .addLiveData(habitComparator)
            .map { (pair, comparator) ->
                    val habits = pair?.first ?: listOf()
                    val filter = pair?.second ?: emptyPair

                    val searchLine = filter.first
                    val ignoreCase = filter.second

                    val habitComparator = comparator ?: emptyComparator
                    habits.filter { it.name.contains(searchLine, ignoreCase) }
                        .sortedWith(habitComparator)
                }
    }

    fun getHabits(type: HabitType): LiveData<List<Habit>> {
        return data.map { item -> item.filter { it.type == type } } 
    }

    fun setHabitComparator(comparator: Comparator<Habit>): HabitsListViewModel {
        habitComparator.postValue(comparator)

        return this
    }

    fun setFilter(name: String, ignoreCase: Boolean): HabitsListViewModel {
        filter.postValue(name to ignoreCase)

        return this
    }
}
