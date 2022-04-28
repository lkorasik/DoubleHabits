package com.lkorasik.doublehabits.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant


@Entity(tableName = "Habits")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val name: String,
    @ColumnInfo val description: String,
    @ColumnInfo val priority: HabitPriority,
    @ColumnInfo val type: HabitType,
    @ColumnInfo val periodicity: String,
    @ColumnInfo val color: Int,
    @ColumnInfo val count: Int,
    @ColumnInfo val createdAt: Instant,
    @ColumnInfo val lastEditedAt: Instant,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        HabitPriority.values()[parcel.readInt()],
        HabitType.values()[parcel.readInt()],
        parcel.readString().orEmpty(),
        parcel.readInt(),
        parcel.readInt(),
        Instant.ofEpochMilli(parcel.readLong()),
        Instant.ofEpochMilli(parcel.readLong())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(priority.ordinal)
        parcel.writeInt(type.ordinal)
        parcel.writeString(periodicity)
        parcel.writeInt(color)
        parcel.writeInt(count)
        parcel.writeLong(createdAt.toEpochMilli())
        parcel.writeLong(lastEditedAt.toEpochMilli())
    }

    //Ноль, если в классе нет специальных объектов, например, дескриптора файла
    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Habit> {
        override fun createFromParcel(parcel: Parcel) = Habit(parcel)

        override fun newArray(size: Int): Array<Habit?> = arrayOfNulls(size)
    }
}
