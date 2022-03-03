package com.lkorasik.doublehabits

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HabitRecycleViewAdapter(
    private val list: MutableList<Habit>,
    private val context: Context,
    private val onItemClicked: (Habit, Int) -> Unit
): RecyclerView.Adapter<ViewHolder>() {

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
                onItemClicked(list[position], position)
            }
        }
    }

    override fun getItemCount() = list.size
}
