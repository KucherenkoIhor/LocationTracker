package com.ik.locationtracker.domains.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ik.locationtracker.domains.data.db.daos.LocationStampDao
import com.ik.locationtracker.domains.entities.LocationStamp
import com.ik.locationtracker.util.locationTimeStampConverter
import com.ik.locationtracker.util.locationTimeStampsConverter

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface LocationRepository {
    fun add(locationStamp: LocationStamp)
    fun getAll(): LiveData<List<LocationStamp>>
    fun getTheLast(): LiveData<LocationStamp?>
}

class LocationRepositoryImpl(private val locationStampDao: LocationStampDao): LocationRepository {
    override fun getAll(): LiveData<List<LocationStamp>> {
        return Transformations.map(locationStampDao.getAll(), locationTimeStampsConverter)
    }

    override fun add(locationStamp: LocationStamp) {
        locationStampDao.insert(locationTimeStampConverter(locationStamp))
    }

    override fun getTheLast(): LiveData<LocationStamp?> {
        return locationStampDao.getTheLast().let {  Transformations.map(it, ::locationTimeStampConverter) }
    }
}