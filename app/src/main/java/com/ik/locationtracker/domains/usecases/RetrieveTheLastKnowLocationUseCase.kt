package com.ik.locationtracker.domains.usecases

import androidx.lifecycle.LiveData
import com.ik.locationtracker.domains.entities.LocationStamp
import com.ik.locationtracker.domains.services.LastKnownLocationLiveData

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface RetrieveTheLastKnowLocationUseCase {
    fun getLastKnownLocation(): LiveData<LocationStamp>
}

class RetrieveTheLastKnowLocationUseCaseImpl(
        private val lastKnownLocationLiveData: LastKnownLocationLiveData
): RetrieveTheLastKnowLocationUseCase {
    override fun getLastKnownLocation(): LiveData<LocationStamp> = lastKnownLocationLiveData
}