package com.ik.locationtracker.app.map

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bluelinelabs.conductor.RestoreViewOnCreateController
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.archlifecycle.ControllerLifecycleOwner
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.ik.locationtracker.R
import com.ik.locationtracker.app.history.HistoryController
import com.ik.locationtracker.util.checkAcceesFineLocationPermission
import kotlinx.android.synthetic.main.screen_map.view.*
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
class MapController : RestoreViewOnCreateController(), CoroutineScope, LifecycleOwner, OnMapReadyCallback {

    private val lifecycleOwner = ControllerLifecycleOwner(this)

    init {
        retainViewMode = RetainViewMode.RETAIN_DETACH
    }

    override fun getLifecycle(): Lifecycle = lifecycleOwner.lifecycle

    private var marker: Marker? = null

    private lateinit var androidJob: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + androidJob

    private var isFirstAttach = true

    private var googleMap: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View {
        return inflater.inflate(R.layout.screen_map, container, false).also {
            it.mapView.onCreate(savedViewState)
            it.mapView.getMapAsync(this)
            initToolBar(it.toolBar)
        }
    }

    private fun initToolBar(toolbar: Toolbar) = with(toolbar) {
        inflateMenu(R.menu.menu_screen_map)
        setOnMenuItemClickListener(::onMenuItemClickListener)
        setTitleTextColor(Color.WHITE)
        setTitle(R.string.app_name)
    }

    private fun onMenuItemClickListener(item: MenuItem) = when (item.itemId) {
        R.id.itemHistory -> {
            pushHistoryScreen()
            true
        }
        else -> false
    }

    private fun pushHistoryScreen() {
        router.pushController(
                RouterTransaction.with(HistoryController())
                        .pushChangeHandler(HorizontalChangeHandler())
                        .popChangeHandler(HorizontalChangeHandler()))
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        androidJob = Job()
        view.mapView.onResume()
        launch {
            val isGranted = view.context.checkAcceesFineLocationPermission()
            if (isGranted && isFirstAttach) {
                subscribeToViewModel()
            }
            isFirstAttach = false
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
    }

    private fun subscribeToViewModel() {
        val model = ViewModelProviders.of(this.activity as FragmentActivity).get(MapViewModel::class.java)
        model.getLocation().observe(this, Observer {
            googleMap?.showLocation(LatLng(it.latitude, it.longitude))
        })
    }

    private fun GoogleMap?.showLocation(latLng: LatLng) {
        val cameraPosition = CameraPosition.fromLatLngZoom(latLng, 20F)
        this?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        if (marker == null) {
            marker = this?.addMarker(MarkerOptions().position(latLng))
        } else {
            marker?.position = latLng
        }
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        view.mapView.onPause()
        androidJob.cancel()
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        view.mapView.onDestroy()
    }

    override fun onSaveViewState(view: View, outState: Bundle) {
        super.onSaveViewState(view, outState)
        view.mapView.onSaveInstanceState(outState)
    }
}
