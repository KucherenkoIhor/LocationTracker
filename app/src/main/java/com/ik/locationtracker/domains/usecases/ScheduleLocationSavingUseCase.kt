package com.ik.locationtracker.domains.usecases

import android.app.AlarmManager
import android.app.PendingIntent
import com.ik.locationtracker.domains.services.JobScheduler

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
interface ScheduleLocationSavingUseCase {
    fun schedule()
}

class ScheduleLocationSavingUseCaseImpl(
        private val alarmManager: AlarmManager,
        private val jobScheduler: JobScheduler,
        private val pendingIntent: PendingIntent
): ScheduleLocationSavingUseCase {
    override fun schedule() {
        jobScheduler.schedule(alarmManager, pendingIntent)
    }
}