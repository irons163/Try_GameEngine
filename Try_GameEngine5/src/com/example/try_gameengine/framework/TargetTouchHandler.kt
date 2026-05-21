package com.example.try_gameengine.framework

import android.view.MotionEvent

class TargetTouchHandler : TouchHandler {
    constructor(touch: ITouchable?, priority: Int) : super(touch, priority, true)

    constructor(touch: ITouchable?, priority: Int, consumeTouch: Boolean) : super(
        touch,
        priority,
        consumeTouch
    )

    fun onTouchBegan(event: MotionEvent?): Boolean {
        // TODO Auto-generated method stub
        return delegate?.onTouchBegan(event) ?: false
    }

    fun onTouchEnded(event: MotionEvent?) {
        // TODO Auto-generated method stub
        delegate?.onTouchEnded(event)
    }

    fun onTouchMoved(event: MotionEvent?) {
        // TODO Auto-generated method stub
        delegate?.onTouchMoved(event)
    }

    fun onTouchCancelled(event: MotionEvent?) {
        // TODO Auto-generated method stub
        delegate?.onTouchCancelled(event)
    }
}
