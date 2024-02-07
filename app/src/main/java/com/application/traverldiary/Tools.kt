package com.application.traverldiary

import android.view.View
import com.application.traverldiary.items.JourneyItem
import com.application.traverldiary.items.TimeItem
import com.application.traverldiary.models.Destination
import com.application.traverldiary.models.Ticket

/**
 * 生成时间轴时间
 */
fun timeCreator(): List<TimeItem> {
    val startHour = 0
    val endHour = 23
    val timeArray = List(endHour - startHour + 1) { index ->
        val currentHour = startHour + index
        TimeItem(String.format("%02d:00", currentHour))
    }
    return timeArray
}

fun journeyCreator(): List<JourneyItem> {
    // 生成五个以上的 Destination 对象
    val destinationList = mutableListOf<JourneyItem>()
    for (i in 0..3) {
        val destination = Destination(
            name = "Destination $i",
            fromTime = 5.2f * i,
            toTime = 2 + 5.3f * i,
            isHappened = true
        )
        destinationList.add(JourneyItem(destination))
    }

    // 生成五个以上的 Ticket 对象
    for (i in 1..5) {
        val ticket = Ticket(
            ticketId = "Ticket $i",
            fromLocation = "Location $i",
            toLocation = "Location ${i + 1}",
            seat = "Seat $i",
            fromTime = 5 + 3f * i,
            toTime = 6 + 3f * i,
            isHappened = true
        )
        destinationList.add(JourneyItem(ticket))
    }
    destinationList.sortBy { it.journey.fromTime }
    return destinationList
}

fun View.dp2px(dp: Int): Int {
    return (context.resources.displayMetrics.density * dp).toInt()
}

