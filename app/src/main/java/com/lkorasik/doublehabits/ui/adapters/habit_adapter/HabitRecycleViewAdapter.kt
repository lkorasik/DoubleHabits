package com.lkorasik.doublehabits.ui.adapters.habit_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.model.Habit

class HabitRecycleViewAdapter(
    private val context: Context,
    private val onItemClicked: OnItemClicked
): ListAdapter<Habit, ViewHolder>(HabitDiffCallBack()) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.widget_habit, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            bind(context, getItem(position))

            itemView.setOnClickListener {
                onItemClicked.onClick(getItem(position), position)
            }
        }
    }
}
