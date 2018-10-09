package com.ik.locationtracker.layers.usecases

import androidx.lifecycle.LiveData
import com.ik.locationtracker.layers.domains.LocationStamp
import com.ik.locationtracker.layers.services.LocationRepository

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface RetrieveHistoryUseCase {
    operator fun invoke(): LiveData<List<LocationStamp>>
}

class RetrieveHistoryUseCaseImpl(private val repository: LocationRepository) : RetrieveHistoryUseCase {
    override fun invoke(): LiveData<List<LocationStamp>> = repository.getAll()
}