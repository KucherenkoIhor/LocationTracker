package com.ik.locationtracker.util

import androidx.lifecycle.LiveData
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * Created by ihor_kucherenko on 10/5/18.
 * https://github.com/KucherenkoIhor
 */
suspend fun<T> LiveData<T>.awaitFirstValue() = suspendCoroutine<T>  { continuation ->
    observeForever(continuation::resume)
}