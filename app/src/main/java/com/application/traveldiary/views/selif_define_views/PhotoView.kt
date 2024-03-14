package com.application.traveldiary.views.selif_define_views

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Picture
import android.net.Uri
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.OverScroller
import com.application.traveldiary.R
import java.io.File

class PhotoView (context: Context, attributeSet: AttributeSet?): View(context,attributeSet){
    private var mBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.vertical)
    //    private var mBitmap: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.horizental)
//    private var mBitmap: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.small)
    private val mPaint by lazy {
        Paint()
    }
    //偏移值
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    //偏移量 移动滑动
    private var offsetX = 0f
    private var offsetY = 0f

    private var overScroller: OverScroller
    private var flingRunner:FlingRunner

    //一边全屏 一边留白
    private var smallScale = 0f
    //两边全屏
    private var bigScale = 0f
    private var currentScale = 0.0000f
    private val minScale:Float
        get(){
            return smallScale * 0.5f
        }
    private val maxScale:Float
        get(){
            return bigScale * 2f
        }

    //放大倍数
    private val OVER_SCALE_FACTOR = 2f
    //已经是放大？
    private val isSmall:Boolean
        get(){
            return  currentScale >= smallScale && currentScale < bigScale
        }

    //手势监听 和属性动画
    private val gestureDetector: GestureDetector
    private var scaleAnimator: ObjectAnimator?
    private var bounceScaleAnimator: ObjectAnimator?
    private val scaleGestureDetector: ScaleGestureDetector

    init {
        gestureDetector = GestureDetector(context,PhotoViewGestureDetectorListener())
        scaleGestureDetector = ScaleGestureDetector(context,PhotoViewScaleGestureDetectorListener())
        scaleAnimator = getScaleAnimator()
        bounceScaleAnimator = getBounceScaleAnimator()
        overScroller = OverScroller(context)
        flingRunner = FlingRunner()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //滑动移动
        canvas.translate(offsetX ,offsetY  )

        //放大缩小 坐标系缩放
        canvas.scale(currentScale,currentScale,width/2f,height/2f)

        //绘制bitmap
        canvas.drawBitmap(mBitmap,originalOffsetX,originalOffsetY,mPaint)

    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        originalOffsetX = (width.toFloat() - mBitmap.width) / 2f
        originalOffsetY = (height.toFloat() - mBitmap.height) / 2f

        //计算和屏幕的宽高比 是vertical还是horizental 从而区分横向显示还是竖向显示
        if(mBitmap.width / mBitmap.height >= width / height){
            smallScale = (width.toFloat() / mBitmap.width)
            bigScale = (height.toFloat() / mBitmap.height) * OVER_SCALE_FACTOR
        }else{
            smallScale = (height.toFloat() / mBitmap.height)
            bigScale = (width.toFloat() / mBitmap.width) * OVER_SCALE_FACTOR
        }
        currentScale = smallScale

    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //优先消费双指放大事件 双指操作优先
        var result = scaleGestureDetector.onTouchEvent(event!!)
        if (!scaleGestureDetector.isInProgress) {
            result = gestureDetector.onTouchEvent(event)
        }
        return result
    }

    fun fixOffsets(){
        offsetX = Math.min(offsetX,(mBitmap.width * currentScale - width) / 2)
        offsetX = Math.max(offsetX,-(mBitmap.width * currentScale - width) / 2)
        offsetY = Math.min(offsetY,(mBitmap.height * currentScale - height) / 2)
        offsetY = Math.max(offsetY,-(mBitmap.height * currentScale - height) / 2)
        if (width - mBitmap.width * currentScale > 0f) offsetX = 0f
        if (height - mBitmap.height * currentScale > 0f) offsetY = 0f
    }


    private fun getScaleAnimator(): ObjectAnimator {
        if (scaleAnimator == null){
            scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale",0f).apply {
                interpolator = AccelerateInterpolator()
                duration = 256
            }
        }
        if (isSmall) {
            scaleAnimator!!.setFloatValues(currentScale, bigScale)
        }else{
            scaleAnimator!!.setFloatValues(currentScale,smallScale)
        }
        return scaleAnimator!!
    }

    private fun getBounceScaleAnimator(): ObjectAnimator {
        if (bounceScaleAnimator == null){
            bounceScaleAnimator = ObjectAnimator.ofFloat(this, "currentScale",bigScale).apply {
                interpolator = AccelerateInterpolator()
                duration = 256
            }
        }
        if (currentScale>=maxScale) {
            bounceScaleAnimator!!.setFloatValues(currentScale, bigScale)
        }
        if (currentScale<=smallScale){
            bounceScaleAnimator!!.setFloatValues(currentScale,smallScale)
        }

        return bounceScaleAnimator!!
    }
    //和属性动画配合 提供方法设置currentScale  通过反射调用方法
    fun setCurrentScale(currentScale:Float){
        this.currentScale = currentScale
        invalidate()
    }



    internal inner class FlingRunner():Runnable{
        override fun run() {
            //动画还在运行就返回True
            if(overScroller.computeScrollOffset()){
                offsetX = overScroller.currX.toFloat()
                offsetY = overScroller.currY.toFloat()
                invalidate()
                postOnAnimation(this)            //每帧执行一次 效果更好
            }else{
                fixOffsets()
                invalidate()
            }
        }
    }

    internal inner class PhotoViewGestureDetectorListener : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return super.onSingleTapUp(e)
        }

        override fun onLongPress(e: MotionEvent) {}

        //类似于move事件
        /*
        * e1
        * e2
        * distanceX 在X轴上划过的距离 （单位时间）  x oldX - newX  向右负 向左正
        * distanceY 在Y周上划过的距离 （）        y               下负   上正
        * */
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            offsetX -= distanceX
            offsetY -= distanceY
            fixOffsets()
            invalidate()
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (currentScale > smallScale) {
                //只会调用一次 因此需要run多次调用
                overScroller.fling(
                    offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                    -(mBitmap.width * currentScale - width).toInt() / 2,
                    (mBitmap.width * currentScale - width).toInt() / 2,
                    -(mBitmap.height * currentScale - height).toInt() / 2,
                    (mBitmap.height * currentScale - height).toInt() / 2,
                    0, 0
                )
                postOnAnimation(flingRunner)    //抛出开始执行
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }

        override fun onShowPress(e: MotionEvent) {}

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            if (isSmall) {
                offsetX = (e.x - width / 2f) - (e.x - width / 2) * bigScale / currentScale
                offsetY = (e.y - height / 2f) - (e.y - height / 2)  * bigScale / currentScale
            }
            getScaleAnimator().start()

            return super.onDoubleTap(e)
        }

        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
            return super.onDoubleTapEvent(e)
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return super.onSingleTapConfirmed(e)
        }

        override fun onContextClick(e: MotionEvent): Boolean {
            return super.onContextClick(e)
        }

    }

    internal inner class PhotoViewScaleGestureDetectorListener: ScaleGestureDetector.OnScaleGestureListener{
        private var initialScale = 0f
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            currentScale = initialScale * detector.scaleFactor
            if (currentScale <= minScale) currentScale = minScale
            if (currentScale >= maxScale) currentScale = maxScale
            invalidate()
            return false
        }

        //返回true 消费事件
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            initialScale = currentScale
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {

        }

    }
}