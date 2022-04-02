package com.lkorasik.doublehabits.sort

import com.lkorasik.doublehabits.model.Habit
import java.lang.IllegalStateException

object SortComparatorFactory {
    fun build(type: HabitSort, direction: SortDirection): Comparator<Habit> {
        return when {
            type == HabitSort.NAME && direction == SortDirection.ASCENDING -> Comparator { h1: Habit, h2: Habit -> h1.name.compareTo(h2.name) }
            type == HabitSort.NAME && direction == SortDirection.DESCENDING -> Comparator { h1: Habit, h2: Habit -> h2.name.compareTo(h1.name) }
            type == HabitSort.PRIORITY && direction == SortDirection.ASCENDING -> Comparator { h1: Habit, h2: Habit -> h1.priority.compareTo(h2.priority) }
            type == HabitSort.PRIORITY && direction == SortDirection.DESCENDING -> Comparator { h1: Habit, h2: Habit -> h2.priority.compareTo(h1.priority) }
            type == HabitSort.DATE_CREATION && direction == SortDirection.ASCENDING -> Comparator { h1: Habit, h2: Habit -> h1.createdAt.compareTo(h2.createdAt)}
            type == HabitSort.DATE_CREATION && direction == SortDirection.DESCENDING -> Comparator { h1: Habit, h2: Habit -> h2.createdAt.compareTo(h1.createdAt)}
            type == HabitSort.DATE_EDITING && direction == SortDirection.ASCENDING -> Comparator { h1: Habit, h2: Habit -> h1.lastEditedAt.compareTo(h2.lastEditedAt)}
            type == HabitSort.DATE_EDITING && direction == SortDirection.DESCENDING -> Comparator { h1: Habit, h2: Habit -> h2.lastEditedAt.compareTo(h1.lastEditedAt)}
            type == HabitSort.NO_SORT -> Comparator { _: Habit, _: Habit -> 0 }
            else -> throw IllegalStateException("Incorrect params")
        }
    }
}
