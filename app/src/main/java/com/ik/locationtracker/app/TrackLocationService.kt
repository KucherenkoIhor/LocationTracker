package com.ik.locationtracker.app

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
import com.ik.locationtracker.layers.usecases.RetrieveTheLastKnowLocationUseCase
import com.ik.locationtracker.layers.usecases.SaveLocationStampUseCase
import com.ik.locationtracker.layers.usecases.ScheduleLocationSavingUseCase
import com.ik.locationtracker.util.awaitFirstValue
import kotlinx.coroutines.experimental.*
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

    private lateinit var androidJob: Job

    override val coroutineContext: CoroutineContext
        get() =  Dispatchers.Main + androidJob

    override val kodein by closestKodein()

    private val scheduleLocationSavingUseCase: ScheduleLocationSavingUseCase by instance()
    private val saveLocationStampUseCase: SaveLocationStampUseCase by instance()
    private val lastKnownLocationUseCase: RetrieveTheLastKnowLocationUseCase by instance()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        androidJob = Job()
        launch {
            startForeground(NOTIFICATION_ID, buildNotification())
            val location = lastKnownLocationUseCase().awaitFirstValue()
            withContext(Dispatchers.IO) { saveLocationStampUseCase(location) }
            finish()
        }
    }

    private fun finish() {
        scheduleLocationSavingUseCase()
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        androidJob.cancel()
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