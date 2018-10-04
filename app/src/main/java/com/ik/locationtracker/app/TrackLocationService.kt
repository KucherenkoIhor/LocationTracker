package com.ik.locationtracker.app

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.ik.locationtracker.BuildConfig
import com.ik.locationtracker.domains.usecases.RetrieveTheLastKnowLocationUseCase
import com.ik.locationtracker.domains.usecases.SaveLocationStampUseCase
import com.ik.locationtracker.domains.usecases.ScheduleLocationSavingUseCase
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by ihor_kucherenko on 10/2/18.
 * https://github.com/KucherenkoIhor
 */
const val NOTIFICATION_ID = 101

class TrackLocationService : Service(), KodeinAware, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    override val kodein by closestKodein()

    private val scheduleLocationSavingUseCase: ScheduleLocationSavingUseCase by instance()
    private val saveLocationStampUseCase: SaveLocationStampUseCase by instance()
    private val lastKnownLocationUseCase: RetrieveTheLastKnowLocationUseCase by instance()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("MissingPermission")
    override fun onCreate() {
        super.onCreate()
        startForeground(NOTIFICATION_ID, buildNotification())
        lastKnownLocationUseCase.getLastKnownLocation().observeForever { location ->
            launch {
                withContext(Dispatchers.IO) { saveLocationStampUseCase.save(location) }
                scheduleLocationSavingUseCase.schedule()
                stopForeground(true)
                stopSelf()
            }
        }
    }

    private fun buildNotification() = getNotificationBuilder().build()

    @Suppress("DEPRECATION")
    private fun getNotificationBuilder() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        NotificationCompat.Builder(this, createNotificationChannel())
    else {
        NotificationCompat.Builder(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val channelId = BuildConfig.APPLICATION_ID
        val channelName = TrackLocationService::class.java.simpleName
        val chan = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
}