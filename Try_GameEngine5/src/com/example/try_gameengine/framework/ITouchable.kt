package com.example.try_gameengine.framework

import android.view.MotionEvent

interface ITouchable : ISystemTouchDelegate {
    fun onTouchBegan(event: MotionEvent?): Boolean
    fun onTouchMoved(event: MotionEvent?)
    fun onTouchEnded(event: MotionEvent?)
    fun onTouchCancelled(event: MotionEvent?)
}
