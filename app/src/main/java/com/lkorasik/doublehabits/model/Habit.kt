package com.lkorasik.doublehabits.model

import android.os.Parcel
import android.os.Parcelable
import com.lkorasik.doublehabits.HabitType
import com.lkorasik.doublehabits.Priority

data class Habit(
    val name: String,
    val description: String,
    val priority: Priority,
    val type: HabitType,
    val periodicity: String,
    val color: Int,
    val count: Int
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        Priority.values()[parcel.readInt()],
        HabitType.values()[parcel.readInt()],
        parcel.readString().orEmpty(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(priority.ordinal)
        parcel.writeInt(type.ordinal)
        parcel.writeString(periodicity)
        parcel.writeInt(color)
        parcel.writeInt(count)
    }

    override fun describeContents(): Int {
        return 0 //TODO: Прочитай почему так
    }

    companion object CREATOR : Parcelable.Creator<Habit> {
        override fun createFromParcel(parcel: Parcel): Habit {
            return Habit(parcel)
        }

        override fun newArray(size: Int): Array<Habit?> {
            return arrayOfNulls(size)
        }
    }
}
