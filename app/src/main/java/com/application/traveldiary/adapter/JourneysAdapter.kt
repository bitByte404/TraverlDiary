package com.application.traveldiary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.application.traveldiary.R
import com.application.traveldiary.manager.JourneyLayoutManager
import com.application.traveldiary.models.items.JourneyItem


class JourneysAdapter(private val items: List<List<JourneyItem>>) :
    RecyclerView.Adapter<JourneysAdapter.JourneysViewHolder>() {
    private lateinit var context: Context

    inner class JourneysViewHolder(journeyView: View) : RecyclerView.ViewHolder(journeyView) {
        var recyclerView: RecyclerView = journeyView.findViewById(R.id.journeysRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourneysViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_journeys, parent, false)
        return JourneysViewHolder(view)
    }

    override fun onBindViewHolder(holder: JourneysViewHolder, position: Int) {
        val jLayoutManager = JourneyLayoutManager(context, items[position])

        holder.recyclerView.apply {
            layoutManager = jLayoutManager
            adapter = JourneyAdapter(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}