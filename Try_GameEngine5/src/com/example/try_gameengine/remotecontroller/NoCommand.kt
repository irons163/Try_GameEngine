package com.example.try_gameengine.remotecontroller

import android.view.MotionEvent

/**
 * This no command is a default for remote controller.
 * @author irons
 // */
class NoCommand : Command {
    private var pointerId = -1

    override fun execute(): RemoteController.CommandType {
        return RemoteController.CommandType.None
    }

    override fun checkExecute(x: Float, y: Float, event: MotionEvent?): Boolean {
        return false
    }

    override fun setMotionEventPointerId(pointerId: Int) {
        this.pointerId = pointerId
    }

    override fun getMotionEventPointerId(): Int {
        return pointerId
    }
}
