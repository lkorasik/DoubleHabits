package com.lkorasik.doublehabits.habit_adapter

import androidx.recyclerview.widget.DiffUtil
import com.lkorasik.doublehabits.model.Habit

class HabitDiffCallBack: DiffUtil.ItemCallback<Habit>() {
    override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem == newItem
    }
}
