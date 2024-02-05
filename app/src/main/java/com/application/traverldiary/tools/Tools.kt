package com.application.traverldiary.tools
import com.application.traverldiary.items.TimeItem

object Tools {

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

}