package com.ik.locationtracker.app.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ik.locationtracker.R
import com.ik.locationtracker.layers.domains.LocationStamp
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_history.view.*

/**
 * Created by ihor_kucherenko on 10/3/18.
 * https://github.com/KucherenkoIhor
 */
class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    var dataSource: List<LocationStamp> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int = dataSource.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(dataSource[position])
    }

    class HistoryViewHolder(
            override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(locationStamp: LocationStamp) {
            containerView.tvLongitude.text = "Longitude: ${locationStamp.longitude}"
            containerView.tvLatitude.text = "Latitude: ${locationStamp.latitude}"
            containerView.tvTime.text = "Time: ${locationStamp.time.toGMTString()}"
            containerView.tvAccuracy.text = "Accuracy: ${locationStamp.accuracy}"
            containerView.tvProvider.text = "Provider: ${locationStamp.provider}"
        }
    }
}