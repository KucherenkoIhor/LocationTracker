package com.ik.locationtracker.layers.usecases

import androidx.lifecycle.LiveData
import com.ik.locationtracker.layers.domains.LocationStamp
import com.ik.locationtracker.layers.services.LocationUpdatesLiveData

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface RetrieveLocationUpdatesUseCase {
    operator fun invoke(): LiveData<LocationStamp>
}

class RetrieveLocationUpdatesUseCaseImpl(
        private val locationUpdatesLiveData: LocationUpdatesLiveData
): RetrieveLocationUpdatesUseCase {
    override fun invoke(): LiveData<LocationStamp> = locationUpdatesLiveData
}