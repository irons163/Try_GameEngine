package com.example.try_gameengine.framework

import android.view.MotionEvent
import android.view.SurfaceHolder

interface IGameController {
    fun start()
    fun stop()
    fun showWin()
    fun showLose()
    fun onTouchEvent(event: MotionEvent?)
    fun setSurfaceHolder(surfaceHolder: SurfaceHolder?)
    fun runStart()
    fun surfaceChanged(
        holder: SurfaceHolder?, format: Int, width: Int,
        height: Int
    )

    fun setFlag(flag: Int)
}
