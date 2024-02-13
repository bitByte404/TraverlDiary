package com.application.traveldiary.models

import android.net.Uri
import cn.bmob.v3.BmobObject

class Picture(
    var uri: Uri,
    val takeTime: String,
    val location: String
) : BmobObject() {
    val picture = ""

//    fun setUri(path: Uri) {
////        uri = path.toString()
//    }
//    fun getUri(): Uri {
////        return Uri.parse(uri)
//    }
}