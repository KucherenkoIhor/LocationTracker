package com.ik.locationtracker.app

import android.app.Application
import com.facebook.stetho.Stetho
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule

/**
 * Created by ihor_kucherenko on 10/2/18.
 * https://github.com/KucherenkoIhor
 */
class LocationTrackerApplication: Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidModule(this@LocationTrackerApplication))
        import(appModule(this@LocationTrackerApplication))
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
    }
}