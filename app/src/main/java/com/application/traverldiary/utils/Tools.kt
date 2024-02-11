package com.application.traverldiary.utils

import android.view.View
import com.application.traverldiary.models.items.JourneyItem
import com.application.traverldiary.models.items.TimeItem
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
    for (i in 1..3) {
        val destination = Destination(
            name = "Destination $i",
            fromTime = 6.2f * i,
            toTime = 1.5f + 6.3f * i,
            isHappened = true
        )
        destinationList.add(JourneyItem(destination))
    }

    // 生成五个以上的 Ticket 对象
    for (i in 0..3) {
        val ticket = Ticket(
            ticketId = "Ticket $i",
            fromLocation = "Location $i",
            toLocation = "Location ${i + 1}",
            seat = "Seat $i",
            fromTime = 4 + 6.01f * i,
            toTime = 5 + 6.11f * i,
            isHappened = true
        )
        destinationList.add(JourneyItem(ticket))
    }
    destinationList.sortBy { it.journey.fromTime }
    return destinationList
}

/**
 * 将Float类型的时间格式化为00:00
 */
fun formatTime(time: Float): String {
    val hour = time.toInt()
    val minute = (60 * (time - time.toInt())).toInt()
    val hourCondition: Boolean = hour < 10
    val minuteCondition: Boolean = minute < 10
    val hourString = when (hourCondition) {
        true -> "0${hour}"
        false -> "$hour"
    }
    val minuteString = when (minuteCondition) {
        true -> "0${minute}"
        false -> "$minute"
    }
    return "${hourString}:${minuteString}"
}

fun View.dp2px(dp: Int): Int {
    return (context.resources.displayMetrics.density * dp).toInt()
}

