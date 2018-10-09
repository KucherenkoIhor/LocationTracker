package com.ik.locationtracker.layers.usecases

import androidx.lifecycle.LiveData
import com.ik.locationtracker.layers.domains.LocationStamp
import com.ik.locationtracker.layers.services.LastKnownLocationLiveData

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface RetrieveTheLastKnowLocationUseCase {
    operator fun invoke(): LiveData<LocationStamp>
}

class RetrieveTheLastKnowLocationUseCaseImpl(
        private val lastKnownLocationLiveData: LastKnownLocationLiveData
): RetrieveTheLastKnowLocationUseCase {
    override fun invoke(): LiveData<LocationStamp> = lastKnownLocationLiveData
}