package com.lkorasik.doublehabits.sort

import com.lkorasik.domain.Habit

object SortComparatorFactory {
    fun build(type: SortTypes, direction: SortDirection): Comparator<com.lkorasik.domain.Habit> {
        val flag = if(direction == SortDirection.ASCENDING) 1 else -1

        return when (type) {
            SortTypes.NAME -> Comparator { h1: com.lkorasik.domain.Habit, h2: com.lkorasik.domain.Habit ->
                h1.title.compareTo(h2.title) * flag
            }
            SortTypes.PRIORITY -> Comparator { h1: com.lkorasik.domain.Habit, h2: com.lkorasik.domain.Habit ->
                h1.priority.compareTo(h2.priority) * flag
            }
            SortTypes.DATE_CREATION -> Comparator { h1: com.lkorasik.domain.Habit, h2: com.lkorasik.domain.Habit ->
                h1.createdAt.compareTo(h2.createdAt) * flag
            }
            SortTypes.DATE_EDITING -> Comparator { h1: com.lkorasik.domain.Habit, h2: com.lkorasik.domain.Habit ->
                h2.lastEditedAt.compareTo(h1.lastEditedAt) * flag
            }
            SortTypes.NO_SORT -> Comparator { _: com.lkorasik.domain.Habit, _: com.lkorasik.domain.Habit -> 0 }
        }
    }
}
