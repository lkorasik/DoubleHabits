package com.lkorasik.doublehabits.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.Instant


@Parcelize
@Entity(tableName = "Habits")
data class Habit(
    @PrimaryKey val id: String,
    @ColumnInfo val name: String,
    @ColumnInfo val description: String,
    @ColumnInfo val priority: HabitPriority,
    @ColumnInfo val type: HabitType,
    @ColumnInfo val periodicity: String,
    @ColumnInfo val color: Int,
    @ColumnInfo val count: Int,
    @ColumnInfo val createdAt: Instant,
    @ColumnInfo val lastEditedAt: Instant,
): Parcelable
