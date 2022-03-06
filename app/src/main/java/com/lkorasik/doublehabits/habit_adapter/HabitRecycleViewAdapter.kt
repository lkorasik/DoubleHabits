package com.lkorasik.doublehabits.habit_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lkorasik.doublehabits.Habit
import com.lkorasik.doublehabits.R

class HabitRecycleViewAdapter(
    private val list: MutableList<Habit>,
    private val context: Context
): RecyclerView.Adapter<ViewHolder>() {

    private var onItemClick: OnItemClicked? = null

    fun setOnItemClick(listener: (habit: Habit, position: Int) -> Unit) {
        onItemClick = OnItemClicked { habit, position -> listener(habit, position) }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.widget_habit, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            bind(context, list[position])

            itemView.setOnClickListener {
                onItemClick?.onClick(list[position], position)
            }
        }
    }

    override fun getItemCount() = list.size
}
