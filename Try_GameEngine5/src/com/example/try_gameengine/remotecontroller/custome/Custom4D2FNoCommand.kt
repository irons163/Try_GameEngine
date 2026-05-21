package com.example.try_gameengine.remotecontroller.custome

import android.view.MotionEvent

class Custom4D2FNoCommand : Custom4D2FCommand {
    private val pointerId = -1

    override fun execute(): Custom4D2FCommandType {
        // TODO Auto-generated method stub
        return Custom4D2FCommandType.None
    }

    override fun checkExecute(x: Float, y: Float, event: MotionEvent?): Boolean {
        // TODO Auto-generated method stub
        return false
    }

    override fun setMotionEventPointerId(pointerId: Int) {
        // TODO Auto-generated method stub
    }

    override fun getMotionEventPointerId(): Int {
        // TODO Auto-generated method stub
        return pointerId
    }
}
