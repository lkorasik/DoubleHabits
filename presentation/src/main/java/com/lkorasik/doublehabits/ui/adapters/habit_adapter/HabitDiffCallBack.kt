package com.lkorasik.doublehabits.ui.adapters.habit_adapter

import androidx.recyclerview.widget.DiffUtil
import com.lkorasik.data.room.HabitEntity

class HabitDiffCallBack: DiffUtil.ItemCallback<HabitEntity>() {
    override fun areItemsTheSame(oldItem: HabitEntity, newItem: HabitEntity): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: HabitEntity, newItem: HabitEntity): Boolean {
        return oldItem == newItem
    }
}
