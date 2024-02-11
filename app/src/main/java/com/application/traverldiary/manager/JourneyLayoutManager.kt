package com.application.traverldiary.manager

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.traverldiary.models.items.JourneyItem

class JourneyLayoutManager(context: Context, private val items: List<JourneyItem>) :
    LinearLayoutManager(context) {

    //修正偏移量implements
    private val correctTranslationDistance: Int = 60
    private var current: Int = 0
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {

        // 如果没有数据，则不进行布局
        if (itemCount == 0 || state?.isPreLayout == true) return

        // 计算RecyclerView的宽度和高度
        val width = width
        val height = height

        // 遍历所有的子视图进行布局
        for (i in 0 until itemCount) {

            current = i
            val view: View = recycler.getViewForPosition(i)
            addView(view)

            // 测量子视图的宽度和高度
            measureChildWithMargins(view, 0, 0)

            val top = (height * items[i].topRatio).toInt() + correctTranslationDistance
            val bottom = (top + height * items[current].heightRatio).toInt()
            // 布局子视图
            layoutDecoratedWithMargins(view, 1, top, width - 1, bottom - 5)
        }
    }
}