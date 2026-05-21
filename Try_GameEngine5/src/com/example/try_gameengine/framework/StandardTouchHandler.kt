package com.example.try_gameengine.framework

import android.view.MotionEvent

class StandardTouchHandler : TouchHandler {
    constructor(touch: ITouchable?, priority: Int) : super(touch, priority)

    constructor(touch: ITouchable?, priority: Int, consumeTouch: Boolean) : super(
        touch,
        priority,
        consumeTouch
    )

    fun onTouchEvent(event: MotionEvent?): Boolean {
        return delegate?.onTouchEvent(event) ?: false
    }
}
