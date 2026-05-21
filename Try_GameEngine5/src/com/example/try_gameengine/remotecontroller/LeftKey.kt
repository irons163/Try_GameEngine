package com.example.try_gameengine.remotecontroller

import android.graphics.Bitmap
import android.view.MotionEvent
import com.example.try_gameengine.framework.Sprite

/**
 * Left key for remote controller.
 * @author irons
 // */
class LeftKey
/**
 * constructor.
 * @param bitmap
 * bitmap.
 * @param x
 * position x.
 * @param y
 * position y.
 * @param scale
 * scale.
 * @param autoAdd
 * is autoAdd all not,
 // */
    (bitmap: Bitmap?, x: Float, y: Float, scale: Int, autoAdd: Boolean) :
    Sprite(bitmap, x, y, scale, autoAdd) {
    /**
     * do preesDown, if MotionEvent do.
     * @param x
     * @param y
     * @param event
     * @return
     // */
    fun pressDown(x: Float, y: Float, event: MotionEvent?): Boolean {
        return onTouchEvent(event)
    }

    /**
     * do preess up, if MotionEvent can do.
     * @param x
     * @param y
     * @param event
     * @return
     // */
    fun pressUp(x: Float, y: Float, event: MotionEvent?): Boolean {
        return onTouchEvent(event)
    }
}
