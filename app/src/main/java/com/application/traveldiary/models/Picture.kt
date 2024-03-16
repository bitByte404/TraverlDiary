package com.application.traveldiary.models

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import cn.bmob.v3.BmobObject
import java.util.Date

class Picture(
    var uri: Uri,         //照片资源地址
    val takeTime: String,
    val location: String?
) : BmobObject() {
    val picture = ""
    val date:Date
        get() {
            TODO()
        }
}