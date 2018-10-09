package com.ik.locationtracker.app.history

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluelinelabs.conductor.archlifecycle.LifecycleController
import com.ik.locationtracker.R
import com.ik.locationtracker.layers.domains.LocationStamp
import kotlinx.android.synthetic.main.screen_history.view.*



/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
class HistoryController: LifecycleController() {

    private var isFirstAttach = true

    private val historyAdapter by lazy(LazyThreadSafetyMode.NONE) { HistoryAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.screen_history, container, false)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        with(view) {
            initToolBar(toolBar)
            initRecyclerView(rvHistory)
        }
        if (isFirstAttach) {
           subscribeToViewModel()
        }
        isFirstAttach = false
    }

    private fun initToolBar(toolbar: Toolbar) = with(toolbar) {
        setNavigationIcon(R.drawable.arrow_left)
        setNavigationOnClickListener { router.handleBack() }
        setTitle(R.string.history)
        setTitleTextColor(Color.WHITE)
    }

    private fun initRecyclerView(recyclerView: RecyclerView) = with(recyclerView) {
        adapter = historyAdapter
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        setHasFixedSize(true)
    }

    private fun subscribeToViewModel() {
        val model = ViewModelProviders.of(this.activity as FragmentActivity).get(HistoryViewModel::class.java)
        model.getHistory().observe(this, Observer<List<LocationStamp>> {
            historyAdapter.dataSource = it
        })
    }
}