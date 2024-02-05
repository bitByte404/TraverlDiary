package com.application.traverldiary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.application.traverldiary.items.JourneyItem
import com.application.traverldiary.items.TimeItem

class TimelineAdapter(private val items: List<JourneyItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class TimeAxisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeText: TextView = itemView.findViewById(R.id.timeTextView)
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 绑定任务卡片数据到视图
    }

    inner class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 绑定车票卡片数据到视图
    }

    //创建ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.TIME.value -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_time, parent, false)
                TimeAxisViewHolder(view)
            }

            ItemType.TASK.value -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
                TimeAxisViewHolder(view)
            }

            ItemType.TICKET.value -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_ticket, parent, false)
                TimeAxisViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TimeAxisViewHolder -> holder.timeText.text =
                (items[position] as TimeItem).hour
        }
    }
}