package com.ik.locationtracker.domains.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ik.locationtracker.domains.data.db.converters.DateConverter
import com.ik.locationtracker.domains.data.db.daos.LocationStampDao
import com.ik.locationtracker.domains.data.db.entities.LocationStamp


/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
@Database(entities = [LocationStamp::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationStampDao
}