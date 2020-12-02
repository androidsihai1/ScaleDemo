package com.yy.scaledemo

/**
 * Created by pengsihai@yy.com on 2019/12/9.
 */
interface ScaleListener {
    fun setInitScale()
    fun setScale(scale: Float)
    fun setPivot(x: Float, y: Float)
    fun setIsCanTouch(canTouch: Boolean)
}