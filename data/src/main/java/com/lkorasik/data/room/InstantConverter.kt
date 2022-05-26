package com.lkorasik.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.Instant

class InstantConverter {
    @TypeConverter fun fromInstantToLong(value: Instant): Long = value.toEpochMilli()
    @TypeConverter fun fromLongToInstant(value: Long): Instant = Instant.ofEpochMilli(value)
}

class DataConverter {
    @TypeConverter
    fun fromListToString(dates: List<Int>): String {
        val gson = Gson()
        val result = gson.toJson(dates)
        return result
    }

    @TypeConverter
    fun fromStringToList(value: String): List<Int> {
        val gson = Gson()
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, type)
    }
}
