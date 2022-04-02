package com.lkorasik.doublehabits.sort

import com.lkorasik.doublehabits.model.Habit
import java.lang.IllegalStateException

object SortComparatorFactory {
    fun build(type: HabitSort, direction: SortDirection): Comparator<Habit> {
        val flag = if(direction == SortDirection.ASCENDING) 1 else -1

        return when (type) {
            HabitSort.NAME -> Comparator { h1: Habit, h2: Habit -> h1.name.compareTo(h2.name) * flag }
            HabitSort.PRIORITY -> Comparator { h1: Habit, h2: Habit -> h1.priority.compareTo(h2.priority) * flag }
            HabitSort.DATE_CREATION -> Comparator { h1: Habit, h2: Habit -> h1.createdAt.compareTo(h2.createdAt) * flag }
            HabitSort.DATE_EDITING -> Comparator { h1: Habit, h2: Habit -> h1.lastEditedAt.compareTo(h2.lastEditedAt) * flag }
            HabitSort.NO_SORT -> Comparator { _: Habit, _: Habit -> 0 }
        }
    }
}
