package com.ik.locationtracker.domains.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ik.locationtracker.domains.data.db.entities.LocationStamp





/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
@Dao
interface LocationStampDao {
    @Query("SELECT * FROM location ORDER BY time DESC")
    fun getAll(): LiveData<List<LocationStamp>>

    @Query("""SELECT * FROM location ORDER BY time DESC LIMIT 1""")
    fun getTheLast(): LiveData<LocationStamp?>

    @Insert
    fun insert(locationStamp: LocationStamp)
}