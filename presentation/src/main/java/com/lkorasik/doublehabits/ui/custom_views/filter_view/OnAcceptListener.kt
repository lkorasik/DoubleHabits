package com.lkorasik.doublehabits.ui.custom_views.filter_view

import com.lkorasik.data.room.HabitEntity

fun interface OnAcceptListener {
    fun onAcceptListener(search_text: String, sortComparator: Comparator<HabitEntity>, ignoreCase: Boolean)
}
