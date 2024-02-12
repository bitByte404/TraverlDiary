package com.application.traveldiary.utils

import android.icu.util.Calendar
import java.util.Date

//日期工具类
object DateUtils {

    //根据Date对象，获得2023-03-29格式的string
    fun getStringFromDate(date: Date): String {
        val calendar = Calendar.getInstance().apply {
            time = date
        }

        val year = calendar.get(Calendar.YEAR)
        //月份从0开始，需要加1
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return "$year-$month-$day"
    }
}