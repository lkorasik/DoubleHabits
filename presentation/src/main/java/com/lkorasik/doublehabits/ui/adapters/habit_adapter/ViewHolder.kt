package com.lkorasik.doublehabits.ui.adapters.habit_adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lkorasik.data.room.HabitEntity
import com.lkorasik.domain.HabitPriority
import com.lkorasik.domain.HabitType
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.WidgetHabitBinding

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(context: Context, habit: HabitEntity) {
        val type = when(habit.type) {
            HabitType.REGULAR -> R.string.add_habit_habit_type_regular
            HabitType.HARMFUL -> R.string.add_habit_habit_type_harmful
        }
        val priority = when (habit.priority) {
            HabitPriority.LOW -> R.string.add_habit_habit_priority_low
            HabitPriority.MEDIUM -> R.string.add_habit_habit_priority_medium
            HabitPriority.HIGH -> R.string.add_habit_habit_priority_high
        }

        with(WidgetHabitBinding.bind(itemView)) {
            habitName.text = habit.title
            habitDescription.text = habit.description
//            habitType.text = context.getString(habit.type.resource)
            habitType.text = context.getString(type)
//            habitPriority.text = context.getString(habit.priority.resource)
            habitPriority.text = context.getString(priority)
            coloredPanel.setBackgroundColor(habit.color)
            habitPeriodicity.text = habit.frequency
        }
    }
}
