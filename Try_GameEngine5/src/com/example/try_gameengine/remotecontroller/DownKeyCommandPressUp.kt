package com.example.try_gameengine.remotecontroller

import android.view.MotionEvent

class DownKeyCommandPressUp(downKey: DownKey) : Command {
    var downKey: DownKey
    private var pointerId = -1

    init {
        this.downKey = downKey
    }

    override fun execute(): RemoteController.CommandType {
        return RemoteController.CommandType.DownKeyUpCommand
    }

    override fun checkExecute(x: Float, y: Float, event: MotionEvent?): Boolean {
        return downKey.pressUp(x, y, event)
    }

    override fun setMotionEventPointerId(pointerId: Int) {
        this.pointerId = pointerId
    }

    override fun getMotionEventPointerId(): Int {
        return pointerId
    }
}
