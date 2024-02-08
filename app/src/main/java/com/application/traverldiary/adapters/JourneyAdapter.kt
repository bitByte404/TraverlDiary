package com.application.traverldiary.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.application.traverldiary.R
import com.application.traverldiary.formatTime
import com.application.traverldiary.items.JourneyItem

class JourneyAdapter(private val items: List<JourneyItem>) :
    RecyclerView.Adapter<JourneyAdapter.JourneyViewHolder>() {


    inner class JourneyViewHolder(journeyView: View) : RecyclerView.ViewHolder(journeyView) {
        val fromTextView: TextView = journeyView.findViewById(R.id.fromTextView)
        val toTextView: TextView = journeyView.findViewById(R.id.toTextView)
        val journeyTextView: TextView = journeyView.findViewById(R.id.journeyTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourneyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_journey, parent, false)
        return JourneyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: JourneyViewHolder, position: Int) {
        holder.journeyTextView.text = items[position].journey.nameOrId
        holder.fromTextView.text = formatTime(items[position].journey.fromTime)
        holder.toTextView.text = formatTime(items[position].journey.toTime)
    }

}