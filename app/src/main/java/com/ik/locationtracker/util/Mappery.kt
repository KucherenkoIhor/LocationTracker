package com.ik.locationtracker.util

import android.location.Location
import com.ik.locationtracker.layers.domains.LocationStamp
import java.util.*
import com.ik.locationtracker.layers.data.db.entities.LocationStamp as DatabaseLocationStamp

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
fun locationTimeStampConverter(databaseLocationStamp: DatabaseLocationStamp?): LocationStamp? {
    if (databaseLocationStamp === null) return null
    return LocationStamp(
            latitude = databaseLocationStamp.latitude,
            longitude = databaseLocationStamp.longitude,
            time = databaseLocationStamp.time,
            accuracy = databaseLocationStamp.accuracy,
            provider = databaseLocationStamp.provider
    )
}

fun locationTimeStampConverter(locationStamp: LocationStamp): DatabaseLocationStamp {
    return DatabaseLocationStamp(
            latitude = locationStamp.latitude,
            longitude = locationStamp.longitude,
            time = locationStamp.time,
            accuracy = locationStamp.accuracy,
            provider = locationStamp.provider
    )
}

val locationTimeStampsConverter: (List<DatabaseLocationStamp>) -> List<LocationStamp> = { list ->
    list.map { locationTimeStampConverter(it)!! }
}

fun locationToLocationTimeStamp(location: Location): LocationStamp {
    return LocationStamp(
            latitude = location.latitude,
            longitude = location.longitude,
            time = Date(),
            accuracy = location.accuracy.toString(),
            provider = location.provider)
}