package com.example.try_gameengine.remotecontroller.custom

import android.view.MotionEvent
import com.example.try_gameengine.framework.ALayer
import com.example.try_gameengine.remotecontroller.custome.Key

/**
 * `CustomCommandPressDown`, this is press down command.
 * @author irons
 // */
class CustomCommandPressDown(key: Key) : CustomCommand {
    var key: Key
    var event: MotionEvent? = null
    private var pointerId = -1

    /**
     * constructor.
     * @param key key.
     * @param custome4D2FCommandType command type.
     // */
    init {
        // TODO Auto-generated constructor stub
        this.key = key
        //		this.event = event;
    }

    override fun checkExecute(x: Float, y: Float, event: MotionEvent?): Boolean {
        // TODO Auto-generated method stub
        return key.pressDown(x, y, event)
    }

    override fun execute(): ALayer {
        // TODO Auto-generated method stub
        return this.key
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