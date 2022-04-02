package com.lkorasik.doublehabits.model

object HabitRepository {
    private val regularHabits = mutableListOf<Habit>()
    private val harmfulHabits = mutableListOf<Habit>()

    private fun selectSource(type: HabitType): MutableList<Habit> {
        return when(type) {
            HabitType.REGULAR -> regularHabits
            HabitType.HARMFUL -> harmfulHabits
        }
    }

    fun getHabits(type: HabitType): List<Habit> = selectSource(type)

    fun addHabit(habit: Habit){
        selectSource(habit.type).add(habit)
    }

    fun getHabit(type: HabitType, position: Int): Habit {
        return selectSource(type)[position]
    }

    fun editHabit(habit: Habit, position: Int) {
        selectSource(habit.type)[position] = habit
    }

    fun deleteHabit(habit: Habit, position: Int) {
        selectSource(habit.type).removeAt(position)
    }
}