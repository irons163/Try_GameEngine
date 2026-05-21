package com.example.try_gameengine.framework

import android.view.MotionEvent

interface ILayerDelegate {
    fun onTouched(event: MotionEvent?)
}
