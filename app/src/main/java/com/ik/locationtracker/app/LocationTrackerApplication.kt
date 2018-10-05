package com.ik.locationtracker.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.ik.locationtracker.domains.services.JobScheduler
import com.ik.locationtracker.domains.services.JobSchedulerImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

/**
 * Created by ihor_kucherenko on 10/2/18.
 * https://github.com/KucherenkoIhor
 */
class LocationTrackerApplication: Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidModule(this@LocationTrackerApplication))
        import(appModule(this@LocationTrackerApplication))
        bind<JobScheduler>() with singleton { JobSchedulerImpl(kodein) }
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
    }
}