package com.ik.locationtracker.domains.usecases

import com.ik.locationtracker.domains.services.JobScheduler

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface ScheduleLocationSavingUseCase {
    fun schedule()
}

class ScheduleLocationSavingUseCaseImpl(
        private val jobScheduler: JobScheduler
): ScheduleLocationSavingUseCase {
    override fun schedule() {
        jobScheduler.schedule()
    }
}