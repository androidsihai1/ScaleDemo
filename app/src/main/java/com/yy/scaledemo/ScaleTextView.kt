package com.yy.scaledemo

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by andy on 2020/11/24.
 */
class ScaleTextView(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet), ScaleListener {
    override fun setInitScale() {
        scaleX = 1.0f
        scaleY = 1.0f
        setPivot((width / 2).toFloat(), (height / 2).toFloat())
    }

    override fun setScale(scale: Float) {
        scaleX = scale
        scaleY = scale
    }

    override fun setPivot(x: Float, y: Float) {
        pivotX = x
        pivotY = y
    }

    override fun setIsCanTouch(canTouch: Boolean) {
    }

}