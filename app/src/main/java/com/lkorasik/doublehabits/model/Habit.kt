package com.lkorasik.doublehabits.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lkorasik.doublehabits.net.dto.HabitDTO
import kotlinx.parcelize.Parcelize
import java.time.Instant


@Parcelize
@Entity(tableName = "Habits")
data class Habit(
    @PrimaryKey val id: String,
    @ColumnInfo val title: String,
    @ColumnInfo val color: Int,
    @ColumnInfo val count: Int,
    @ColumnInfo val description: String,
    //TODO: Add done dates
    @ColumnInfo val frequency: String,
    @ColumnInfo val priority: HabitPriority,
    @ColumnInfo val type: HabitType,
    //TODO: Make single date
    @ColumnInfo val createdAt: Instant, //TODO: Delete
    @ColumnInfo val lastEditedAt: Instant, //TODO: Delete
): Parcelable {
    companion object {
        fun from(habit: HabitDTO): Habit {
            return Habit(
                id = habit.uid ?: "",
                title = habit.title,
                description = habit.description,
                priority = HabitPriority.values()[habit.priority],
                type = HabitType.values()[habit.type],
                frequency = habit.frequency.toString(),
                color = habit.color,
                count = habit.count,
                createdAt = Instant.now(),
                lastEditedAt = Instant.now()
            )
        }
    }
}
