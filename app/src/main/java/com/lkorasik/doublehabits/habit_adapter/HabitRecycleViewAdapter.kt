package com.lkorasik.doublehabits.habit_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lkorasik.doublehabits.HabitType
import com.lkorasik.doublehabits.model.Habit
import com.lkorasik.doublehabits.R

class HabitRecycleViewAdapter(
    private val list: MutableList<Habit>,
    private val context: Context,
    private val onItemClicked: OnItemClicked,
): RecyclerView.Adapter<ViewHolder>() {

    //TODO: юзай diffUtils, он эффективно обновляет
    //TODO: Почитай про viewBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.widget_habit, viewGroup, false)

        return ViewHolder(view)
    }

    fun addHabit(habit: Habit) {
        list.add(habit)
        notifyItemInserted(list.size - 1)
    }

    fun editHabit(habit: Habit, position: Int) {
        list[position] = habit
        notifyItemChanged(position)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            bind(context, list[position])

            itemView.setOnClickListener {
                onItemClicked.onClick(list[position], position)
            }
        }
    }

    override fun getItemCount() = list.size
}
