package com.application.traveldiary.models

import android.net.Uri
import cn.bmob.v3.BmobObject
import java.util.Date

class Picture(
    var uri: Uri,
    val takeTime: String,
    val location: String
) : BmobObject() {
}