package com.ik.locationtracker.domains.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ik.locationtracker.domains.entities.LocationStamp
import java.util.concurrent.TimeUnit

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface RetrieveCurrentLocationUseCase {
    fun getCurrentLocation(): LiveData<LocationStamp>
}

class RetrieveCurrentLocationUseCaseImpl(
        private val retrieveTheLastKnowLocationUseCase: RetrieveTheLastKnowLocationUseCase,
        private val retrieveTheLastSavedLocationStampUseCase: RetrieveTheLastSavedLocationStampUseCase
): RetrieveCurrentLocationUseCase {
    override fun getCurrentLocation(): LiveData<LocationStamp> {

        val liveDataMerger = MediatorLiveData<LocationStamp>()
        liveDataMerger.addSource(retrieveTheLastSavedLocationStampUseCase.retrieveTheLastLocationStamp()) { locationStamp ->
            if (locationStamp != null) {
                val isLessThanOneHour = (System.currentTimeMillis() - locationStamp.time.time) < TimeUnit.HOURS.toMillis(1)
                if (isLessThanOneHour) {
                    liveDataMerger.value = locationStamp
                } else {
                    liveDataMerger.addSource(retrieveTheLastKnowLocationUseCase.getLastKnownLocation()) { liveDataMerger.value = it }
                }
            } else {
                liveDataMerger.addSource(retrieveTheLastKnowLocationUseCase.getLastKnownLocation()) {
                    liveDataMerger.value = it }
            }
        }

        return liveDataMerger
    }
}