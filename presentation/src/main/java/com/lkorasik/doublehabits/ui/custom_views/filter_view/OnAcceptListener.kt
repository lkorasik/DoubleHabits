package com.lkorasik.doublehabits.ui.custom_views.filter_view

import com.lkorasik.domain.Habit

fun interface OnAcceptListener {
    fun onAcceptListener(search_text: String, sortComparator: Comparator<com.lkorasik.domain.Habit>, ignoreCase: Boolean)
}
