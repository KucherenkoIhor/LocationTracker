package com.ik.locationtracker.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ProcessLifecycleOwner
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.ik.locationtracker.R
import com.ik.locationtracker.app.map.MapController
import com.ik.locationtracker.util.checkAcceesFineLocationPermission
import kotlinx.android.synthetic.main.activity_launch.*
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import kotlin.coroutines.experimental.CoroutineContext


class LaunchActivity : AppCompatActivity(), KodeinAware, CoroutineScope {

    private lateinit var androidJob: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + androidJob

    override val kodein by closestKodein()

    private val appStateObserver: AppLifecycleObserver by instance()

    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        androidJob = Job()

        launch {
            val isGranted = checkAcceesFineLocationPermission()
            if (isGranted) {
                ProcessLifecycleOwner.get().lifecycle.addObserver(appStateObserver)
            }
        }

        router = Conductor.attachRouter(this, controller_container , savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(MapController()))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        androidJob.cancel()
    }
}
