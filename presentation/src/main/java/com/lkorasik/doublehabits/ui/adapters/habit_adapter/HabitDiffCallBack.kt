package com.lkorasik.doublehabits.ui.adapters.habit_adapter

import androidx.recyclerview.widget.DiffUtil
import com.lkorasik.domain.Habit

class HabitDiffCallBack: DiffUtil.ItemCallback<com.lkorasik.domain.Habit>() {
    override fun areItemsTheSame(oldItem: com.lkorasik.domain.Habit, newItem: com.lkorasik.domain.Habit): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: com.lkorasik.domain.Habit, newItem: com.lkorasik.domain.Habit): Boolean {
        return oldItem == newItem
    }
}
