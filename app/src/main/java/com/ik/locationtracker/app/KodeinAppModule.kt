package com.ik.locationtracker.app

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_NO_CREATE
import android.content.Context
import android.content.Intent
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.ik.locationtracker.layers.data.db.AppDatabase
import com.ik.locationtracker.layers.services.LastKnownLocationLiveData
import com.ik.locationtracker.layers.services.LocationRepository
import com.ik.locationtracker.layers.services.LocationRepositoryImpl
import com.ik.locationtracker.layers.services.LocationUpdatesLiveData
import com.ik.locationtracker.layers.usecases.*
import com.ik.locationtracker.util.pendingIntentServiceCompat
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton



/**
 * Created by ihor_kucherenko on 10/2/18.
 * https://github.com/KucherenkoIhor
 */

fun appModule(context: Context) = Kodein.Module("AppModule") {
    bind<PendingIntent>() with provider {
        pendingIntentServiceCompat(context, 0, Intent(context, TrackLocationService::class.java), 0)
    }
    bind<PendingIntent>(::FLAG_NO_CREATE.name) with provider {
        pendingIntentServiceCompat(context, 0, Intent(context, TrackLocationService::class.java), FLAG_NO_CREATE)
    }

    bind<AppDatabase>() with singleton {
        Room.databaseBuilder(context, AppDatabase::class.java, "database")
                .fallbackToDestructiveMigration()
                .build()
    }
    bind<LocationRepository>() with singleton {
        LocationRepositoryImpl(instance<AppDatabase>().locationDao())
    }
    bind<SaveLocationStampUseCase>() with singleton {
        SaveLocationStampUseCaseImpl(instance())
    }
    bind<RetrieveHistoryUseCase>() with singleton {
        RetrieveHistoryUseCaseImpl(instance())
    }
    bind<RetrieveTheLastSavedLocationStampUseCase>() with singleton {
        RetrieveTheLastSavedLocationStampUseCaseImpl(instance())
    }
    bind<FusedLocationProviderClient>() with singleton {
        LocationServices.getFusedLocationProviderClient(context)
    }
    bind<LocationUpdatesLiveData>() with singleton { LocationUpdatesLiveData(instance()) }
    bind<RetrieveLocationUpdatesUseCase>() with singleton {
        RetrieveLocationUpdatesUseCaseImpl(instance())
    }
    bind<LastKnownLocationLiveData>() with provider { LastKnownLocationLiveData(instance()) }
    bind<RetrieveTheLastKnowLocationUseCase>() with provider {
        RetrieveTheLastKnowLocationUseCaseImpl(instance())
    }
    bind<RetrieveCurrentLocationUseCase>() with singleton {
        RetrieveCurrentLocationUseCaseImpl(instance(), instance())
    }
    bind<ScheduleLocationSavingUseCase>() with provider {
        ScheduleLocationSavingUseCaseImpl(instance())
    }
    bind<CancelLocationSavingUseCase>() with provider {
        CancelLocationSavingUseCaseImpl(instance())
    }
    bind<AppLifecycleObserver>() with singleton { AppStateObserverImpl(instance(), instance()) }
}