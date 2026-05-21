package com.example.try_gameengine.remotecontroller.custome

import android.graphics.Bitmap
import android.view.MotionEvent
import com.example.try_gameengine.framework.Sprite

class Key(bitmap: Bitmap?, x: Float, y: Float, scale: Int, autoAdd: Boolean) :
    Sprite(bitmap, x, y, scale, autoAdd) {
    fun setEnableMultiTouch(enableMultiTouch: Boolean) {
        isEnableMultiTouch = enableMultiTouch
    }

    fun pressDown(x: Float, y: Float, event: MotionEvent?): Boolean {
        return onTouchEvent(event)
    }

    fun pressUp(x: Float, y: Float, event: MotionEvent?): Boolean {
        return onTouchEvent(event)
    }
}
