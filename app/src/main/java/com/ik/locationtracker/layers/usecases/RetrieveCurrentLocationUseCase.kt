package com.ik.locationtracker.layers.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ik.locationtracker.layers.domains.LocationStamp
import java.util.concurrent.TimeUnit

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface RetrieveCurrentLocationUseCase {
    operator fun invoke(): LiveData<LocationStamp>
}

class RetrieveCurrentLocationUseCaseImpl(
        private val retrieveTheLastKnowLocationUseCase: RetrieveTheLastKnowLocationUseCase,
        private val retrieveTheLastSavedLocationStampUseCase: RetrieveTheLastSavedLocationStampUseCase
): RetrieveCurrentLocationUseCase {
    override fun invoke(): LiveData<LocationStamp> {
        val liveDataMerger = MediatorLiveData<LocationStamp>()
        liveDataMerger.addSource(retrieveTheLastSavedLocationStampUseCase()) { locationStamp ->
            if (locationStamp != null) {
                val isLessThanOneHour = (System.currentTimeMillis() - locationStamp.time.time) < TimeUnit.HOURS.toMillis(1)
                if (isLessThanOneHour) {
                    liveDataMerger.value = locationStamp
                } else {
                    liveDataMerger.addSource(retrieveTheLastKnowLocationUseCase()) { liveDataMerger.value = it }
                }
            } else {
                liveDataMerger.addSource(retrieveTheLastKnowLocationUseCase()) {
                    liveDataMerger.value = it }
            }
        }

        return liveDataMerger
    }
}