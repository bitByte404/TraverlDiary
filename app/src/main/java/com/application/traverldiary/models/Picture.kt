package com.application.traverldiary.models

import android.graphics.Bitmap
import java.util.Date

class Picture(
    val picture: Bitmap,
    val takeTime: Date,
    val location: String
) {
}