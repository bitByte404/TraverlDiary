package com.application.traveldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.application.traveldiary.R
import com.application.traveldiary.models.items.TimeItem


class TimelineAdapter(private val items: List<TimeItem>) :
    RecyclerView.Adapter<TimelineAdapter.TimeAxisViewHolder>() {

    inner class TimeAxisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeText: TextView = itemView.findViewById(R.id.timeTextView)
    }


    //创建ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeAxisViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_time, parent, false)
        return TimeAxisViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TimeAxisViewHolder, position: Int) {
        holder.timeText.text = items[position].hour
    }

}