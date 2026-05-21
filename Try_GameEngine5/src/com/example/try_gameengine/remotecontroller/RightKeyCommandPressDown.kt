package com.example.try_gameengine.remotecontroller

import android.view.MotionEvent

class RightKeyCommandPressDown(rightKey: RightKey) : Command {
    var rightKey: RightKey
    private var pointerId = -1

    init {
        this.rightKey = rightKey
    }

    override fun execute(): RemoteController.CommandType {
        return RemoteController.CommandType.RightKeyDownCommand
    }

    override fun checkExecute(x: Float, y: Float, event: MotionEvent?): Boolean {
        return rightKey.pressDown(x, y, event)
    }

    override fun setMotionEventPointerId(pointerId: Int) {
        this.pointerId = pointerId
    }

    override fun getMotionEventPointerId(): Int {
        return pointerId
    }
}
