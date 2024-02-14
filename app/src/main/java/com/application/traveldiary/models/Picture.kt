package com.application.traveldiary.models

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import cn.bmob.v3.BmobObject

class Picture(
    val bitmap: Bitmap ,  //缩略图
    var uri: Uri,         //照片资源地址
    val takeTime: String,
    val location: String
) : BmobObject() {
    val picture = ""

}