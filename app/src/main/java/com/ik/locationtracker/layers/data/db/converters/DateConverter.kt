package com.ik.locationtracker.layers.data.db.converters

import androidx.room.TypeConverter
import java.util.*

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}