package com.ik.locationtracker.domains.usecases

import com.ik.locationtracker.domains.entities.LocationStamp
import com.ik.locationtracker.domains.services.LocationRepository

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface SaveLocationStampUseCase {
    fun save(location: LocationStamp)
}

class SaveLocationStampUseCaseImpl(private val repository: LocationRepository): SaveLocationStampUseCase {
    override fun save(location: LocationStamp) {
        repository.add(location)
    }
}