package com.lkorasik.doublehabits

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.habit_name)
    private val description: TextView = view.findViewById(R.id.habit_description)
    private val type: TextView = view.findViewById(R.id.habit_type)
    private val priority: TextView = view.findViewById(R.id.habit_priority)
    private val coloredPanel: View = view.findViewById(R.id.colored_panel)
    private val periodicity: TextView = view.findViewById(R.id.habit_periodicity)

    fun bind(context: Context, habit: Habit) {
        name.text = habit.name
        description.text = habit.description
        type.text = habit.type.name
        priority.text = habit.priority.toString()
        coloredPanel.setBackgroundColor(context.getColor(habit.color))
        periodicity.text = habit.periodicity
    }
}
