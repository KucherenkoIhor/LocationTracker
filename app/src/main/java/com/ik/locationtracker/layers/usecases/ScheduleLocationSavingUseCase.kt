package com.ik.locationtracker.layers.usecases

import com.ik.locationtracker.layers.services.JobScheduler

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface ScheduleLocationSavingUseCase {
    operator fun invoke()
}

class ScheduleLocationSavingUseCaseImpl(
        private val jobScheduler: JobScheduler
): ScheduleLocationSavingUseCase {
    override fun invoke() = jobScheduler.schedule()
}