package com.ik.locationtracker.domains.services

import android.annotation.SuppressLint
import android.os.Looper
import androidx.lifecycle.LiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.ik.locationtracker.domains.entities.LocationStamp
import com.ik.locationtracker.util.locationToLocationTimeStamp
import java.util.concurrent.TimeUnit

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
class LocationUpdatesLiveData(
        private val fusedLocationProviderClient: FusedLocationProviderClient
): LiveData<LocationStamp>() {

    private val interval = TimeUnit.SECONDS.toMillis(20)

    private val request = LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(interval)
            .setFastestInterval(interval)

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            locationResult?.lastLocation?.also { value = locationToLocationTimeStamp(it) }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        fusedLocationProviderClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
    }

    override fun onInactive() {
        super.onInactive()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}