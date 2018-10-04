package com.ik.locationtracker.domains.services

import android.app.AlarmManager
import android.app.PendingIntent
import androidx.core.app.AlarmManagerCompat
import java.util.concurrent.TimeUnit


/**
 * Created by ihor_kucherenko on 10/2/18.
 * https://github.com/KucherenkoIhor
 */
const val UPDATE_INTERVAL_IN_BACKGROUND: Long = 15

interface JobScheduler {
    fun schedule(alarmManager: AlarmManager, pendingIntent: PendingIntent)

    fun cancelScheduledJob(alarmManager: AlarmManager, pendingIntent: PendingIntent)
}

class JobSchedulerImpl: JobScheduler {

    override fun schedule(alarmManager: AlarmManager, pendingIntent: PendingIntent) {
        val interval = TimeUnit.MINUTES.toMillis(UPDATE_INTERVAL_IN_BACKGROUND)
        AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + interval,
                pendingIntent)
    }

    override fun cancelScheduledJob(alarmManager: AlarmManager, pendingIntent: PendingIntent) {
        alarmManager.cancel(pendingIntent)
    }
}