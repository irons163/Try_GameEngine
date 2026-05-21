package com.example.try_gameengine.framework

import android.view.MotionEvent

interface ISystemTouchDelegate {
    fun onTouchEvent(event: MotionEvent?): Boolean
}
