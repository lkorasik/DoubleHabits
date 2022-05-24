package com.lkorasik.data.room

import androidx.room.TypeConverter
import java.time.Instant

class InstantConverter {
    @TypeConverter fun fromInstantToLong(value: Instant): Long = value.toEpochMilli()
    @TypeConverter fun fromLongToInstant(value: Long): Instant = Instant.ofEpochMilli(value)
}
