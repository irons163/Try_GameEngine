package com.example.try_gameengine.remotecontroller.custome

import android.view.MotionEvent

/**
 * `Custom4D2FCommand` is a custom command handler for 4D2F(4 Direction keys
 * and 2 Function keys), total 6 keys.
 * 
 * @author irons
 // */
interface Custom4D2FCommand {
    /**
     * checkExecute
     * @param x
     * not
     * @param y
     * @param event
     * @return
     // */
    fun checkExecute(x: Float, y: Float, event: MotionEvent?): Boolean

    /**
     * execute.
     * @return
     // */
    fun execute(): Custom4D2FCommandType?

    /**
     * @param pointerId
     // */
    fun setMotionEventPointerId(pointerId: Int)

    /**
     * @return
     // */
    fun getMotionEventPointerId(): Int
}