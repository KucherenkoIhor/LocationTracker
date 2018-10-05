package com.ik.locationtracker.domains.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_NO_CREATE
import androidx.core.app.AlarmManagerCompat
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit


/**
 * Created by ihor_kucherenko on 10/2/18.
 * https://github.com/KucherenkoIhor
 */
const val UPDATE_INTERVAL_IN_BACKGROUND: Long = 15

interface JobScheduler {
    fun schedule()

    fun cancelScheduledJob()
}

class JobSchedulerImpl(override val kodein: Kodein): JobScheduler, KodeinAware {

    private val alarmManager: AlarmManager by kodein.instance()

    override fun schedule() {
        val pendingIntent: PendingIntent by kodein.instance()
        val interval = TimeUnit.MINUTES.toMillis(UPDATE_INTERVAL_IN_BACKGROUND)
        AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + interval,
                pendingIntent)
    }

    override fun cancelScheduledJob() {
        val pendingIntent: PendingIntent? by kodein.instance(::FLAG_NO_CREATE.name)
        pendingIntent?.also {
            alarmManager.cancel(it)
        }
    }
}