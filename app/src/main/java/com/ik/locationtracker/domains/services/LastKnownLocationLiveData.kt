package com.ik.locationtracker.domains.services

import android.annotation.SuppressLint
import android.location.Location
import androidx.lifecycle.LiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnSuccessListener
import com.ik.locationtracker.domains.entities.LocationStamp
import com.ik.locationtracker.util.locationToLocationTimeStamp

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
class LastKnownLocationLiveData(
        private val fusedLocationProviderClient: FusedLocationProviderClient
): LiveData<LocationStamp>() {

    private val onSuccessListener = OnSuccessListener<Location> { location ->
        location?.also { value = locationToLocationTimeStamp(it) }
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(onSuccessListener)
    }
}