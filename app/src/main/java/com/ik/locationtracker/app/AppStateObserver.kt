package com.ik.locationtracker.app

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.ik.locationtracker.layers.usecases.CancelLocationSavingUseCase
import com.ik.locationtracker.layers.usecases.ScheduleLocationSavingUseCase


/**
 * Created by ihor_kucherenko on 10/2/18.
 * https://github.com/KucherenkoIhor
 */
interface AppLifecycleObserver: LifecycleObserver {
    fun onForeground()
    fun onBackground()
}

class AppStateObserverImpl(
        private val scheduleLocationSavingUseCase: ScheduleLocationSavingUseCase,
        private val cancelLocationSavingUseCase: CancelLocationSavingUseCase
): AppLifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onForeground() {
        cancelLocationSavingUseCase()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onBackground() {
        scheduleLocationSavingUseCase()
    }
}