package com.yy.scaledemo

import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.sqrt

/**
 * Created by pengsihai@yy.com on 2019/12/9.
 */
object ScaleCreateUtil {


    var TAG = "ScaleCreateUtil"
    var mScaleListener: ScaleListener? = null
    const val SCALE_MAX = 2.0f //最大的缩放比例
    const val SCALE_MIN = 1.0f

    /**
     * 触摸使用的移动事件
     *
     * @param lessX
     * @param lessY
     */
    fun setSelfPivot(lessX: Float, lessY: Float, touchView: View) {
        var setPivotX: Float
        var setPivotY: Float
        setPivotX = touchView.pivotX + lessX
        setPivotY = touchView.pivotY + lessY
        Log.d(
            TAG, "setPivotX:" + setPivotX + "  setPivotY:" + setPivotY +
                    "  getWidth:" + touchView.width + "  getHeight:" + touchView.height
        )
        if (setPivotX < 0 && setPivotY < 0) {
            setPivotX = 0f
            setPivotY = 0f
        } else if (setPivotX > 0 && setPivotY < 0) {
            setPivotY = 0f
            if (setPivotX > touchView.width) {
                setPivotX = touchView.width.toFloat()
            }
        } else if (setPivotX < 0 && setPivotY > 0) {
            setPivotX = 0f
            if (setPivotY > touchView.height) {
                setPivotY = touchView.height.toFloat()
            }
        } else {
            if (setPivotX > touchView.width) {
                setPivotX = touchView.width.toFloat()
            }
            if (setPivotY > touchView.height) {
                setPivotY = touchView.height.toFloat()
            }
        }
        setPivot(setPivotX, setPivotY, touchView)
    }

    /**
     * 计算两个点的距离
     *
     * @param event
     * @return
     */
    fun spacing(event: MotionEvent): Double {
        return if (event.pointerCount == 2) {
            val x = event.getX(0) - event.getX(1)
            val y = event.getY(0) - event.getY(1)
            sqrt((x * x + y * y).toDouble())
        } else {
            0.0
        }
    }

    /**
     * 平移画面，当画面的宽或高大于屏幕宽高时，调用此方法进行平移
     *
     * @param x
     * @param y
     */
    fun setPivot(x: Float, y: Float, touchView: View) {
        touchView.pivotX = x
        touchView.pivotY = y
        mScaleListener?.setPivot(x, y)
    }

    /**
     * 设置放大缩小
     *
     * @param scale
     */
    fun setScale(scale: Float, touchView: View) {
        touchView.scaleX = scale
        touchView.scaleY = scale
        mScaleListener?.setScale(scale)
    }

    /**
     * 初始化比例，也就是原始比例
     */
    fun setInitScale(touchView: View) {
        touchView.scaleX = 1.0f
        touchView.scaleY = 1.0f
        setPivot((touchView.width / 2).toFloat(), (touchView.height / 2).toFloat(), touchView)
        mScaleListener?.setInitScale()
    }
}