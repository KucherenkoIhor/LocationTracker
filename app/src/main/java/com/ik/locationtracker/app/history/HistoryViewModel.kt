package com.ik.locationtracker.app.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ik.locationtracker.domains.entities.LocationStamp
import com.ik.locationtracker.domains.usecases.RetrieveHistoryUseCase
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
class HistoryViewModel(application: Application) : AndroidViewModel(application), KodeinAware {

    override val kodein by closestKodein(application.applicationContext)

    private val retrieveHistoryUseCase: RetrieveHistoryUseCase by instance()

    fun getHistory(): LiveData<List<LocationStamp>> {
        return retrieveHistoryUseCase.retrieveHistory()
    }
}