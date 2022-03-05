package com.lkorasik.doublehabits

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lkorasik.doublehabits.databinding.WidgetHabitBinding

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(context: Context, habit: Habit) {
        with(WidgetHabitBinding.bind(itemView)) {
            this.habitType
            habitName.text = habit.name
            habitDescription.text = habit.description
            habitType.text = habit.type.getRepresentation(context)
            habitPriority.text = habit.priority.getRepresentation(context)
            coloredPanel.setBackgroundColor(context.getColor(habit.color))
            habitPeriodicity.text = habit.periodicity
        }
    }
}
