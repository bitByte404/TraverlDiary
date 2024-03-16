package com.application.traveldiary.manager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyGridLayoutManager(context: Context,spanCount:Int,):GridLayoutManager(context,spanCount) {
    private var checkScroll:(position:Int)->Boolean = {false }

    init {
        spanSizeLookup = object :SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return if (checkScroll(position + 1)){
                    val column = position % spanCount
                    spanCount - column
                }else {
                    1
                }
            }

        }
    }

    fun setCheckScroll(callback: (position:Int)->Boolean){
        checkScroll = callback
    }


    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        for(i in 1 until childCount){
            //获取子view
            val view = getChildAt(i)
            //检查是否头

        }
    }

}