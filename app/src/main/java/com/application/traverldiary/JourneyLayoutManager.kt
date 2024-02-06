package com.application.traverldiary

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.application.traverldiary.items.JourneyItem

class JourneyLayoutManager(private val items: List<JourneyItem>) :
    RecyclerView.LayoutManager() {

    //修正偏移量implements
    private val correctTranslationDistance: Int = 180
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.MATCH_PARENT
        )

    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)

        // 如果没有数据，则不进行布局
        if (itemCount == 0 || state?.isPreLayout == true) return

        // 清除所有已添加的视图
        removeAllViews()

        // 计算RecyclerView的宽度和高度
        val width = width
        val height = height

        // 遍历所有的子视图进行布局
        items.forEachIndexed { index, it ->

            val view: View = recycler.getViewForPosition(index)
            addView(view)

            // 测量子视图的宽度和高度
            measureChildWithMargins(view, 0, 0)

            val top = (height * it.topRatio).toInt() + correctTranslationDistance
            val bottom = (top + height * it.heightRatio).toInt()
            // 布局子视图
            layoutDecoratedWithMargins(view, 1, top, width - 1, bottom)

        }
    }

}