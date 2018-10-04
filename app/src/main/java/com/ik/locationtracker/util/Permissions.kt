package com.ik.locationtracker.util

import android.Manifest
import android.content.Context
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import java.util.*
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * Created by ihor_kucherenko on 10/4/18.
 * https://github.com/KucherenkoIhor
 */
suspend fun Context.checkAcceesFineLocationPermission() = suspendCoroutine<Boolean> { continuation ->
    val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    val rationale = "Please provide location permission so that you can ..."
    val options = Permissions.Options()
            .setRationaleDialogTitle("Info")
            .setSettingsDialogTitle("Warning")

    Permissions.check(this, permissions, rationale, options, object : PermissionHandler() {
        override fun onGranted() {
           continuation.resume(true)
        }

        override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
            super.onDenied(context, deniedPermissions)
            continuation.resume(false)
        }
    })
}