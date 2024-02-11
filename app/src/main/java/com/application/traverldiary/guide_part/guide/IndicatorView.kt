package com.application.traverldiary.guide_part.guide

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.application.traverldiary.R
import com.example.booting.ui.tool.dp2px


class IndicatorViewL(context: Context,attrs:AttributeSet?):View(context,attrs) {
    private var mSelectedWidth = dp2px(40)
    private var mNormalWidth = dp2px(20)
    private var mHeight = dp2px(12)
    private var mSpace = dp2px(6)
    private var mCornerRadius = dp2px(6).toFloat()


    private var mCurrentIndex = 0
    private var mSelectedColor = resources.getColor(R.color.colorA)
    private var mNormalColor = resources.getColor(R.color.colorB)

    private val mPaint = Paint().apply {
        color = mNormalColor
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var m_width = MeasureSpec.getSize(widthMeasureSpec)
        val width_mode = MeasureSpec.getMode(widthMeasureSpec)
        if (width_mode != MeasureSpec.EXACTLY){
            m_width = 1*mNormalWidth + 1*mSelectedWidth +1*mSpace
        }

        var m_height = MeasureSpec.getSize(heightMeasureSpec)
        val height_mode = MeasureSpec.getMode(heightMeasureSpec)
        if (height_mode != MeasureSpec.EXACTLY){
            m_height = mHeight
        }

        //尺寸汇报
        setMeasuredDimension(m_width,m_height)
    }


    private var mStartX = 0f
    private var mTop = 0f
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mStartX = (width - (1*mNormalWidth + 1*mSelectedWidth +1*mSpace)).toFloat()/2
        mTop = (height - mHeight).toFloat()/2
    }

    //提供外部设置当前选中哪一个
    fun select(index:Int){
        if (mCurrentIndex == index)return
        mCurrentIndex = index
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until 2){
            val left = mStartX + if (i > mCurrentIndex){
                mSelectedWidth + mSpace + (i-1)*(mNormalWidth + mSpace)
            }else{
                i*(mNormalWidth + mSpace)
            }
            val right = left + if (i == mCurrentIndex)mSelectedWidth else mNormalWidth

            //确定画笔颜色
            mPaint.color = if (i == mCurrentIndex)mSelectedColor else mNormalColor
            canvas.drawRoundRect(
                left,mTop,right,mTop + mHeight,mCornerRadius,mCornerRadius,mPaint
            )
        }
    }
}