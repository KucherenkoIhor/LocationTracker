package com.ik.locationtracker.layers.domains

import java.util.*

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
data class LocationStamp(
        val latitude: Double,
        val longitude: Double,
        val time: Date,
        val accuracy: String,
        val provider: String
)