package com.ik.locationtracker.layers.usecases

import androidx.lifecycle.LiveData
import com.ik.locationtracker.layers.domains.LocationStamp
import com.ik.locationtracker.layers.services.LocationRepository

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface RetrieveTheLastSavedLocationStampUseCase {
    operator fun invoke(): LiveData<LocationStamp?>
}

class RetrieveTheLastSavedLocationStampUseCaseImpl(
        private val repository: LocationRepository
): RetrieveTheLastSavedLocationStampUseCase {
    override fun invoke(): LiveData<LocationStamp?> =  repository.getTheLast()
}