package com.lkorasik.doublehabits.ui.adapters.habit_adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import com.lkorasik.data.room.HabitEntity
import com.lkorasik.doublehabits.R


class HabitRecycleViewAdapter(
    private val context: Context,
    private val onItemClicked: OnItemClicked,
    private val onItemDoneClicked: OnItemClicked
): ListAdapter<HabitEntity, ViewHolder>(HabitDiffCallBack()) {

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

            setOnClickListener {
                onItemDoneClicked.onClick(getItem(position), position)

                Log.i("App", "click ${getItem(position)} $position")
            }
        }
    }
}
