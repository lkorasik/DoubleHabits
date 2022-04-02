package com.lkorasik.doublehabits.model

object HabitRepository {
    private val regularHabits = mutableListOf<Habit>()
    private val harmfulHabits = mutableListOf<Habit>()

    private fun selectLiveData(type: HabitType): MutableList<Habit> {
        return when(type) {
            HabitType.REGULAR -> regularHabits
            HabitType.HARMFUL -> harmfulHabits
        }
    }

    fun getHabits(type: HabitType): List<Habit> = selectLiveData(type)

    fun addHabit(habit: Habit){
        selectLiveData(habit.type).add(habit)
    }

    fun getHabit(type: HabitType, position: Int): Habit {
        return selectLiveData(type)[position]
    }

    fun editHabit(habit: Habit, position: Int) {
        selectLiveData(habit.type)[position] = habit
    }

    fun deleteHabit(habit: Habit, position: Int) {
        selectLiveData(habit.type).removeAt(position)
    }
}