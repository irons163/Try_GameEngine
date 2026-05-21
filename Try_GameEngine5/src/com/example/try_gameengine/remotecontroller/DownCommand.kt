package com.example.try_gameengine.remotecontroller

import android.view.MotionEvent

/**
 * 
 * @author irons
 // */
class DownCommand(upKey: UpKey) : Command {
    var upKey: UpKey
    private var pointerId = -1

    init {
        this.upKey = upKey
    }

    override fun execute(): RemoteController.CommandType {
        // TODO Auto-generated method stub
        return RemoteController.CommandType.UPKeyUpCommand
    }

    override fun checkExecute(x: Float, y: Float, event: MotionEvent?): Boolean {
        // TODO Auto-generated method stub
        return upKey.pressDown(x, y, event)
    }

    override fun setMotionEventPointerId(pointerId: Int) {
        // TODO Auto-generated method stub
        this.pointerId = pointerId
    }

    override fun getMotionEventPointerId(): Int {
        // TODO Auto-generated method stub
//		return -1;
        return pointerId
    }
}
