package com.ik.locationtracker.app.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ik.locationtracker.domains.entities.LocationStamp
import com.ik.locationtracker.domains.usecases.RetrieveCurrentLocationUseCase
import com.ik.locationtracker.domains.usecases.RetrieveLocationUpdatesUseCase
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
class MapViewModel(application: Application) : AndroidViewModel(application), KodeinAware {

    override val kodein by closestKodein(application.applicationContext)

    private val retrieveCurrentLocationUseCase: RetrieveCurrentLocationUseCase by instance()
    private val retrieveLocationUpdatesUseCase: RetrieveLocationUpdatesUseCase by instance()

    fun getLocation(): LiveData<LocationStamp> {
        val liveDateMerger = MediatorLiveData<LocationStamp>()
        liveDateMerger.addSource(retrieveCurrentLocationUseCase.getCurrentLocation()) {
            liveDateMerger.value = it
        }
        liveDateMerger.addSource(retrieveLocationUpdatesUseCase.getLocationUpdates()) {
            liveDateMerger.value = it
        }
        return liveDateMerger
    }
}