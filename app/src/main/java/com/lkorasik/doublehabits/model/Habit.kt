package com.lkorasik.doublehabits.model

import android.os.Parcel
import android.os.Parcelable
import java.time.Instant

data class Habit(
    val name: String,
    val description: String,
    val priority: HabitPriority,
    val type: HabitType,
    val periodicity: String,
    val color: Int,
    val count: Int,
    val createdAt: Instant,
    val lastEditedAt: Instant
): Parcelable {

    constructor(parcel: Parcel) : this(
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
