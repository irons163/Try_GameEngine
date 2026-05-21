package com.example.try_gameengine.remotecontroller.custome

import android.view.MotionEvent

class Custom4D2FKeyCommandPressUp(key: Key, custome4D2FCommandType: Custom4D2FCommandType?) :
    Custom4D2FCommand {
    var key: Key
    var custome4D2FCommandType: Custom4D2FCommandType?
    private var pointerId = -1

    init {
        // TODO Auto-generated constructor stub
        this.key = key
        this.custome4D2FCommandType = custome4D2FCommandType
    }

    override fun checkExecute(x: Float, y: Float, event: MotionEvent?): Boolean {
        // TODO Auto-generated method stub
        return key.pressUp(x, y, event)
    }

    override fun execute(): Custom4D2FCommandType? {
        // TODO Auto-generated method stub
        return this.custome4D2FCommandType
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
