package com.example.try_gameengine.remotecontroller

import android.view.MotionEvent

class UpKeyCommandPressDown(upKey: UpKey) : Command {
    var upKey: UpKey
    private var pointerId = -1

    init {
        this.upKey = upKey
    }

    override fun execute(): RemoteController.CommandType {
        return RemoteController.CommandType.UPKeyDownCommand
    }

    override fun checkExecute(x: Float, y: Float, event: MotionEvent?): Boolean {
        return upKey.pressUp(x, y, event)
    }

    override fun setMotionEventPointerId(pointerId: Int) {
        this.pointerId = pointerId
    }

    override fun getMotionEventPointerId(): Int {
        return pointerId
    }
}
