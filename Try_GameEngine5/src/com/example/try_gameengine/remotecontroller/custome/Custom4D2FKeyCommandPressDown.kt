package com.example.try_gameengine.remotecontroller.custome

import android.view.MotionEvent

/**
 * `Custom4D2FKeyCommandPressDown`, this is press down command.
 * @author irons
 // */
class Custom4D2FKeyCommandPressDown(key: Key, custome4D2FCommandType: Custom4D2FCommandType?) :
    Custom4D2FCommand {
    var key: Key
    var custome4D2FCommandType: Custom4D2FCommandType?
    private var pointerId = -1

    /**
     * constructor.
     * @param key key.
     * @param custome4D2FCommandType command type.
     // */
    init {
        // TODO Auto-generated constructor stub
        this.key = key
        this.custome4D2FCommandType = custome4D2FCommandType
    }

    override fun checkExecute(x: Float, y: Float, event: MotionEvent?): Boolean {
        // TODO Auto-generated method stub
        return key.pressDown(x, y, event)
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
