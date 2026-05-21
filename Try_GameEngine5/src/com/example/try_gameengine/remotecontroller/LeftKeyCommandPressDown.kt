package com.example.try_gameengine.remotecontroller

import android.view.MotionEvent

/**
 * left key press down.
 * @author irons
 // */
class LeftKeyCommandPressDown(leftKey: LeftKey) : Command {
    var leftKey: LeftKey
    private var pointerId = -1

    init {
        this.leftKey = leftKey
    }

    override fun execute(): RemoteController.CommandType {
        return RemoteController.CommandType.LeftKeyDownCommand
    }

    override fun checkExecute(x: Float, y: Float, event: MotionEvent?): Boolean {
        return leftKey.pressDown(x, y, event)
    }

    override fun setMotionEventPointerId(pointerId: Int) {
        this.pointerId = pointerId
    }

    override fun getMotionEventPointerId(): Int {
        return pointerId
    }
}
