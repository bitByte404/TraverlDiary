package com.application.traverldiary.models

import android.graphics.Bitmap
import java.text.SimpleDateFormat
import java.util.Date

class Picture(
    val picture: Bitmap,
    val takeTime: Date,
    val title:String,
    val location: String            //fileDir +
) {

}