package com.lkorasik.data.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lkorasik.domain.entities.HabitModel
import com.lkorasik.domain.entities.HabitPriority
import com.lkorasik.domain.entities.HabitType
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "Habits")
data class HabitEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val title: String,
    @ColumnInfo val color: Int,
    @ColumnInfo val count: Int,
    @ColumnInfo val description: String,
    @ColumnInfo val frequency: String,
    @ColumnInfo val priority: HabitPriority,
    @ColumnInfo val type: HabitType,
    @ColumnInfo val date: Int,
    @ColumnInfo val done_dates: List<Int>
): Parcelable {
    companion object {
        fun fromModel(model: HabitModel): HabitEntity = HabitEntity(
            id = model.id,
            title = model.title,
            color = model.color,
            count = model.count,
            description = model.description,
            frequency = model.frequency.toString(),
            priority = model.priority,
            type = model.type,
            date = model.date,
            done_dates = model.doneDates
        )
    }

    fun toModel(): HabitModel = HabitModel(
        id = id,
        title = title,
        color = color,
        count = count,
        description = description,
        frequency = 0,
        priority = priority,
        type = type,
        date = date,
        doneDates = done_dates
    )
}
