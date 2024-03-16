package com.application.traveldiary.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class DateAlbumDecoration: RecyclerView.ItemDecoration() {
    val drawTextPaint:Paint by lazy { Paint().apply {
        color = Color.BLACK
    }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.adapter is DateAlbumAdapter){
            val adapter = parent.adapter
            //当前屏幕上的
            val count = parent.childCount
            //实现itemview的宽度和分割线的宽度一样
            val left = parent.paddingLeft
            val right = parent.paddingRight
            for (i in 0 until count){
                val view = parent.getChildAt(i)
                //当前item位置
                val position = parent.getChildLayoutPosition(view)
                //判断是否是头部

            }
        }

    }

}