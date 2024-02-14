package com.application.traveldiary.manager

import android.content.Context
import android.media.ExifInterface
import android.net.Uri
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LocationTimeManager private constructor() {
    companion object {
        private var instance: LocationTimeManager? = null

        //经典单例模式
        fun getInstance(): LocationTimeManager {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = LocationTimeManager()
                    }
                }
            }
            return instance!!
        }
    }

    //获取照片位置
    fun getPhotoLocationFromUri(context: Context, uri: Uri, exifInterface: ExifInterface?) {
        val latitude = exifInterface?.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
        val longitude = exifInterface?.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)
        if (latitude != null && longitude != null) {
            convertExifToDecimal(latitude)
            convertExifToDecimal(longitude)
        } else {
            // 照片没有位置信息，进行适当的处理
        }
    }
    //经纬度 转为标准的数字格式
    private fun convertExifToDecimal(exifCoord: String): Double {
        val split = exifCoord.split(",")

        val degrees = split[0].split("/").let { it[0].toDouble() / it[1].toDouble() }
        val minutes = split[1].split("/").let { it[0].toDouble() / it[1].toDouble() }
        val seconds = split[2].split("/").let { it[0].toDouble() / it[1].toDouble() }

        return degrees + minutes / 60.0 + seconds / 3600.0
    }

    //获取照片拍摄时间
    fun getPhotoTakeTime(exifInterface: ExifInterface?):String{
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val dateTimeString = exifInterface?.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL)
        val format = SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.getDefault())
        val takeDate = dateTimeString?.let { format.parse(it) }
        return if (takeDate==null){
            dateFormat.format(Date())
        }else{
            dateFormat.format(takeDate)
        }
    }


    fun aMap(context: Context){
//        AMapLocationClient.updatePrivacyShow(context,true,true);
//        AMapLocationClient.updatePrivacyAgree(context,true);
    }
}