package com.example.try_gameengine.remotecontroller

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent

/**
 * @author irons
 // */
interface IRemoteController {
    fun onTouchEvent(event: MotionEvent?): Boolean

    fun drawRemoteController(canvas: Canvas?, paint: Paint?)
}
