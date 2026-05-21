package com.example.try_gameengine.remotecontroller.custom

import android.view.MotionEvent
import com.example.try_gameengine.framework.ALayer

class CustomTouch(touch: ALayer?, event: MotionEvent?) {
    private var touch: ALayer?
    private var event: MotionEvent?

    init {
        this.touch = touch
        this.event = event
    }

    fun getTouch(): ALayer? {
        return touch
    }

    fun setTouch(touch: ALayer?) {
        this.touch = touch
    }

    fun getEvent(): MotionEvent? {
        return event
    }

    fun setEvent(event: MotionEvent?) {
        this.event = event
    }
}
