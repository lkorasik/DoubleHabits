package com.lkorasik.doublehabits.view_model

import androidx.lifecycle.*
import com.lkorasik.doublehabits.extensions.addLiveData
import com.lkorasik.doublehabits.extensions.map
import com.lkorasik.data.repository.HabitRepositoryImpl
import com.lkorasik.data.room.HabitEntity
import com.lkorasik.domain.entities.HabitType
import com.lkorasik.domain.HabitsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant

class HabitsListViewModel(private val useCase: HabitsUseCase): ViewModel() {
    private val emptyPair = "" to false
    private val emptyComparator = Comparator { _: HabitEntity, _: HabitEntity -> 0 }

    private var filter = MutableLiveData(emptyPair)
    private var habitComparator = MutableLiveData(emptyComparator)

    private var data: LiveData<List<HabitEntity>> = MutableLiveData(listOf())

    init {
        data = useCase
            .getAllHabits()
            .asLiveData(viewModelScope.coroutineContext)
            .addLiveData(filter)
            .addLiveData(habitComparator)
            .map { (pair, comparator) ->
                    val habits = pair?.first ?: listOf()
                    val filter = pair?.second ?: emptyPair

                    val searchLine = filter.first
                    val ignoreCase = filter.second

                    val habitComparator = comparator ?: emptyComparator
                habits.map {
                    HabitEntity(
                        id = it.id,
                        title = it.name,
                        color = it.color,
                        count = it.countRepeats,
                        description = it.description,
                        frequency = it.interval.toString(),
                        priority = it.priority,
                        type = it.type,
                        createdAt = Instant.now(),
                        lastEditedAt = Instant.now()
                    )
                }
                 .filter { it.title.contains(searchLine, ignoreCase) }
                        .sortedWith(habitComparator)
                }
    }

    fun loadHabits() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.loadHabits()
        }
    }

    fun getHabits(type: HabitType): LiveData<List<HabitEntity>> {
        return data.map { item -> item.filter { it.type == type } } 
    }

    fun setHabitComparator(comparator: Comparator<HabitEntity>): HabitsListViewModel {
        habitComparator.postValue(comparator)

        return this
    }

    fun deleteHabit(habit: HabitEntity) {
        viewModelScope.launch {
//            repository.deleteHabit(HabitUID_DTO.from(habit))
        }
    }

    fun setFilter(name: String, ignoreCase: Boolean): HabitsListViewModel {
        filter.postValue(name to ignoreCase)

        return this
    }
}
