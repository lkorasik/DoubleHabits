package com.lkorasik.doublehabits.ui.custom_views.filter_view

import com.lkorasik.doublehabits.model.Habit

fun interface OnAcceptListener {
    fun onAcceptListener(search_text: String, sortComparator: Comparator<Habit>, ignoreCase: Boolean)
}
