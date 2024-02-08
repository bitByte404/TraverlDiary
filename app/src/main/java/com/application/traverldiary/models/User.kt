package com.application.traverldiary.models

import android.graphics.Bitmap
import androidx.annotation.IntDef
import cn.bmob.v3.BmobObject
import com.application.traverldiary.application.BmobApp

class User(
    val userId: Int,
    var name: String,
    var password: String,
    var phone: String,
    var sex: Int, //TODO 为了测试进行了修改
    var head: String, //TODO 修改头像为链接地址
    var IP: String,
) : BmobObject() {
}