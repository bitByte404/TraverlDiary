package com.example.booting.ui.tool

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationSet
import android.view.animation.BounceInterpolator
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation

object AnimationTools {
    fun addScaleAndAlphaAnimation(
        target: View,
        fromScale: Float,
        toScale: Float,
        fromAlpha: Float,
        toAlpha: Float,
        interpolator: Interpolator = LinearInterpolator(),
        duration: Long = 500,
        onStart: () -> Unit = {},
        onEnd: () -> Unit = {},
        onRepeat: () -> Unit = {}
    ) {
        //创建动画效果淡入淡出
            val alphaAnim = AlphaAnimation(fromAlpha,toAlpha)
            val scaleAnim = ScaleAnimation(
                fromScale,
                toScale,
                fromScale,
                toScale,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
        AnimationSet(true).apply {
            addAnimation(alphaAnim)
            addAnimation(scaleAnim)
            this.duration = duration
            this.interpolator = interpolator
            setAnimationListener(object:AnimationListener{
                override fun onAnimationStart(p0: Animation?) { onStart() }
                override fun onAnimationEnd(p0: Animation?) { onEnd() }
                override fun onAnimationRepeat(p0: Animation?) { onRepeat() }
            })
            target.startAnimation(this)
        }
    }

    //创建动画效果旋转
    fun addRotateAndAlphaAnimation(
        target: View,
        fromAlpha: Float = 0f,toAlpha: Float = 1f,
        fromDegree:Float = 180f,toDegree:Float = 360f,
        onStart: () -> Unit = {},
        onEnd: () -> Unit = {},
        onRepeat: () -> Unit = {},
        onCancel: () -> Unit = {},
        ){
        val rotate = ObjectAnimator.ofFloat(target,"rotationX",fromDegree,toDegree)
        val alpha = ObjectAnimator.ofFloat(target,"alpha",fromAlpha,toAlpha)
        AnimatorSet().apply {
            duration = 1000
            interpolator = BounceInterpolator()
            playTogether(rotate,alpha)
            addListener(object :AnimatorListener{
                override fun onAnimationStart(p0: Animator) {
                     onStart()
                }

                override fun onAnimationEnd(p0: Animator) {
                    onEnd()
                }

                override fun onAnimationCancel(p0: Animator) {
                    onCancel()
                }

                override fun onAnimationRepeat(p0: Animator) {
                    onRepeat()
                }
            })
            start()
        }
    }
}