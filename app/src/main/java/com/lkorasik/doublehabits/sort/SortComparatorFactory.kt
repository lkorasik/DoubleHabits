package com.lkorasik.doublehabits.sort

import com.lkorasik.doublehabits.model.Habit

object SortComparatorFactory {
    fun build(type: SortTypes, direction: SortDirection): Comparator<Habit> {
        val flag = if(direction == SortDirection.ASCENDING) 1 else -1

        return when (type) {
            SortTypes.NAME -> Comparator { h1: Habit, h2: Habit ->
                h1.name.compareTo(h2.name) * flag
            }
            SortTypes.PRIORITY -> Comparator { h1: Habit, h2: Habit ->
                h1.priority.compareTo(h2.priority) * flag
            }
            SortTypes.DATE_CREATION -> Comparator { h1: Habit, h2: Habit ->
                h1.createdAt.compareTo(h2.createdAt) * flag
            }
            SortTypes.DATE_EDITING -> Comparator { h1: Habit, h2: Habit ->
                h2.lastEditedAt.compareTo(h1.lastEditedAt) * flag
            }
            SortTypes.NO_SORT -> Comparator { _: Habit, _: Habit -> 0 }
        }
    }
}
