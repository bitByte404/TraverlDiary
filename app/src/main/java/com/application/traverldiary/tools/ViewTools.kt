package com.application.traverldiary.tools

import android.view.View

fun View.dp2px(dp: Int): Int {
    return (context.resources.displayMetrics.density * dp).toInt()
}