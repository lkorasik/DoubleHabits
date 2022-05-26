package com.lkorasik.data.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lkorasik.domain.entities.HabitModel
import com.lkorasik.domain.entities.HabitPriority
import com.lkorasik.domain.entities.HabitType
import kotlinx.parcelize.Parcelize
import java.time.Instant


@Parcelize
@Entity(tableName = "Habits")
data class HabitEntity(
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
    @ColumnInfo val done_dates: List<Int>
): Parcelable {
    companion object {
        fun fromModel(model: HabitModel): HabitEntity = HabitEntity(
            id = model.id,
            title = model.name,
            description = model.description,
            color = model.color,
            createdAt = Instant.now(), //model.date,
            count = model.countRepeats,
            frequency = model.interval.toString(),
            type = model.type,
            priority = model.priority,
            lastEditedAt = Instant.now(),
            done_dates = model.doneDates
        )
    }

    fun toModel(): HabitModel = HabitModel(
        id = id,
        name = title,
        description = description,
        color = color,
        countRepeats = count,
        interval = 0,
        type = type,
        priority = priority,
        date = createdAt.nano,
        doneDates = done_dates
    )
}
