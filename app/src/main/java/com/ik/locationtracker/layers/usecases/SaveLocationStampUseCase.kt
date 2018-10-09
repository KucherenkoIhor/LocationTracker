package com.ik.locationtracker.layers.usecases

import com.ik.locationtracker.layers.domains.LocationStamp
import com.ik.locationtracker.layers.services.LocationRepository

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface SaveLocationStampUseCase {
    operator fun invoke(location: LocationStamp)
}

class SaveLocationStampUseCaseImpl(private val repository: LocationRepository): SaveLocationStampUseCase {
    override fun invoke(location: LocationStamp) {
        repository.add(location)
    }
}