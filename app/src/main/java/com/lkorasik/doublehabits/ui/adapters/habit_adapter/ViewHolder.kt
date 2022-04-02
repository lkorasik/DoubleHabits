package com.lkorasik.doublehabits.ui.adapters.habit_adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.databinding.WidgetHabitBinding

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(context: Context, habit: Habit) {
        with(WidgetHabitBinding.bind(itemView)) {
            habitName.text = habit.name
            habitDescription.text = habit.description
            habitType.text = context.getString(habit.type.resource)
            habitPriority.text = context.getString(habit.priority.resource)
            coloredPanel.setBackgroundColor(habit.color)
            habitPeriodicity.text = habit.periodicity
        }
    }
}
