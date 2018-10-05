package com.ik.locationtracker.domains.usecases

import com.ik.locationtracker.domains.services.JobScheduler

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface CancelLocationSavingUseCase {
    fun cancel()
}

class CancelLocationSavingUseCaseImpl(
        private val jobScheduler: JobScheduler
) : CancelLocationSavingUseCase {
    override fun cancel() {
        jobScheduler.cancelScheduledJob()
    }
}
