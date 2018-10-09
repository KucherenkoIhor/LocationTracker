package com.ik.locationtracker.layers.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
@Entity(tableName = "location")
class LocationStamp(
        @PrimaryKey
        @ColumnInfo(name = "time")
        val time: Date,
        @ColumnInfo(name = "latitude")
        val latitude: Double,
        @ColumnInfo(name = "longitude")
        val longitude: Double,
        @ColumnInfo(name = "accuracy")
        val accuracy: String,
        @ColumnInfo(name = "provider")
        val provider: String
)