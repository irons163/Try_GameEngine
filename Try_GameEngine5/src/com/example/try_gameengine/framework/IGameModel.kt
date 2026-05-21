package com.example.try_gameengine.framework

import android.view.MotionEvent
import android.view.SurfaceHolder
import com.example.try_gameengine.Camera.Camera

interface IGameModel {
    fun getData(): Data?
    fun setData(data: Data?)
    fun registerObserver(moveObserver: IMoveObserver?)
    fun removeObserver(moveObserver: IMoveObserver?)
    fun onTouchEvent(event: MotionEvent?)
    fun start()
    fun setSurfaceHolder(surfaceHolder: SurfaceHolder?)
    fun restart()
    fun stop()
    fun getBackgroundColor(): Int
    fun setBackgroundColor(backgroundColor: Int)
    fun getCamera(): Camera?
    fun setCamera(camera: Camera?)
    fun addPreProcessBlock(processBlock: ProcessBlock?) //	public void setTime(Time time);
    //	public Time getTime();
}
