package com.lkorasik.doublehabits.habit_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lkorasik.doublehabits.HabitType
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.R
import com.lkorasik.doublehabits.databinding.WidgetHabitBinding

class HabitRecycleViewAdapter(
    private val context: Context,
    private val onItemClicked: OnItemClicked
): ListAdapter<Habit, ViewHolder>(HabitDiffCallBack()) {

    //TODO: Почитай про viewBinding

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
