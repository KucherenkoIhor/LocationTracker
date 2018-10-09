package com.ik.locationtracker.layers.usecases

import com.ik.locationtracker.layers.services.JobScheduler

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface CancelLocationSavingUseCase {
    operator fun invoke()
}

class CancelLocationSavingUseCaseImpl(
        private val jobScheduler: JobScheduler
) : CancelLocationSavingUseCase {
    override fun invoke() {
        jobScheduler.cancelScheduledJob()
    }
}
