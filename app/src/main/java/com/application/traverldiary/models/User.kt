package com.application.traverldiary.models

import android.graphics.Bitmap
import androidx.annotation.IntDef

class User(
    val userId: Int,
    var name: String,
    var password: String,
    var phone: String,
    var sex: IntDef,
    var head: Bitmap,
    var IP: String,
) {
}