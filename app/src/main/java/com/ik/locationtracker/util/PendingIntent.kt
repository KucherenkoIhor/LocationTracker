package com.ik.locationtracker.util

import android.app.PendingIntent
import android.os.Build

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
val pendingIntentServiceCompat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    PendingIntent::getForegroundService
} else {
    PendingIntent::getService
}