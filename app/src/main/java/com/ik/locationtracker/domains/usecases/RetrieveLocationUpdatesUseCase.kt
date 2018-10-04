package com.ik.locationtracker.domains.usecases

import androidx.lifecycle.LiveData
import com.ik.locationtracker.domains.entities.LocationStamp
import com.ik.locationtracker.domains.services.LocationUpdatesLiveData

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface RetrieveLocationUpdatesUseCase {
    fun getLocationUpdates(): LiveData<LocationStamp>
}

class RetrieveLocationUpdatesUseCaseImpl(
        private val locationUpdatesLiveData: LocationUpdatesLiveData
): RetrieveLocationUpdatesUseCase {
    override fun getLocationUpdates(): LiveData<LocationStamp> = locationUpdatesLiveData
}