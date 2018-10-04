package com.ik.locationtracker.domains.usecases

import androidx.lifecycle.LiveData
import com.ik.locationtracker.domains.entities.LocationStamp
import com.ik.locationtracker.domains.services.LocationRepository

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface RetrieveTheLastSavedLocationStampUseCase {
    fun retrieveTheLastLocationStamp(): LiveData<LocationStamp?>
}

class RetrieveTheLastSavedLocationStampUseCaseImpl(
        private val repository: LocationRepository
): RetrieveTheLastSavedLocationStampUseCase {
    override fun retrieveTheLastLocationStamp(): LiveData<LocationStamp?> {
        return repository.getTheLast()
    }
}