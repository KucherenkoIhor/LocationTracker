package com.ik.locationtracker.domains.usecases

import androidx.lifecycle.LiveData
import com.ik.locationtracker.domains.entities.LocationStamp
import com.ik.locationtracker.domains.services.LocationRepository

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface RetrieveHistoryUseCase {
    fun retrieveHistory(): LiveData<List<LocationStamp>>
}

class RetrieveHistoryUseCaseImpl(private val repository: LocationRepository) : RetrieveHistoryUseCase {
    override fun retrieveHistory(): LiveData<List<LocationStamp>> = repository.getAll()
}