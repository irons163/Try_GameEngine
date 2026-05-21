package com.example.try_gameengine.remotecontroller

import android.view.MotionEvent

class DownKeyCommandPressDown(downKey: DownKey) : Command {
    var downKey: DownKey
    private var pointerId = -1

    init {
        this.downKey = downKey
    }

    override fun execute(): RemoteController.CommandType {
        // TODO Auto-generated method stub
        return RemoteController.CommandType.DownKeyDownCommand
    }

    override fun checkExecute(x: Float, y: Float, event: MotionEvent?): Boolean {
        // TODO Auto-generated method stub
        return downKey.pressDown(x, y, event)
    }

    override fun setMotionEventPointerId(pointerId: Int) {
        // TODO Auto-generated method stub
        this.pointerId = pointerId
    }

    override fun getMotionEventPointerId(): Int {
        // TODO Auto-generated method stub
        return pointerId
    }
}
