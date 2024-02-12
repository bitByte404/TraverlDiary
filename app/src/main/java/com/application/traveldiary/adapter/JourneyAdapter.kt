package com.application.traveldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.application.traveldiary.R
import com.application.traveldiary.utils.formatTime
import com.application.traveldiary.models.items.JourneyItem
import com.application.traveldiary.models.Ticket

class JourneyAdapter(private val items: List<JourneyItem>) :
    RecyclerView.Adapter<JourneyAdapter.JourneyViewHolder>() {


    inner class JourneyViewHolder(journeyView: View) : RecyclerView.ViewHolder(journeyView) {
        val nameOrIdTextView: TextView = journeyView.findViewById(R.id.nameOrIdTextView)
        val fromTimeTextView: TextView = journeyView.findViewById(R.id.fromTimeTextView)
        val toTimeTextView: TextView = journeyView.findViewById(R.id.toTimeTextView)
        val fromLocationTextView: TextView = journeyView.findViewById(R.id.fromLocationView)
        val toLocationTextView: TextView = journeyView.findViewById(R.id.toLocationTextView)
        val seatTextView: TextView = journeyView.findViewById(R.id.seatTextView)
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
        items[position].journey.apply {
            holder.nameOrIdTextView.text = nameOrId
            holder.fromTimeTextView.text = formatTime(fromTime)
            holder.toTimeTextView.text = formatTime(toTime)
            if (this is Ticket) {
                holder.fromLocationTextView.text = fromLocation
                holder.toLocationTextView.text = toLocation
                holder.seatTextView.text = seat
            }
        }
    }

}