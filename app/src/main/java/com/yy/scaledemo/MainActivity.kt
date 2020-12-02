package com.yy.scaledemo

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    var mIsTwoHandle = false
    var mIsSliding = false
    var oldDist: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //touch_view?.loadUrl("https://baidu.com")
        touch_view?.setIsCanTouch(true)
    }
}
