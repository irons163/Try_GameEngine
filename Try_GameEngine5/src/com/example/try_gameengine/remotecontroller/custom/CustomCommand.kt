package com.example.try_gameengine.remotecontroller.custom

import android.view.MotionEvent
import com.example.try_gameengine.framework.ALayer

/**
 * `Custom4D2FCommand` is a custom command handler for 4D2F(4 Direction keys
 * and 2 Function keys), total 6 keys.
 * 
 * @author irons
 // */
interface CustomCommand {
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
    fun execute(): ALayer?

    /**
     * @param pointerId
     // */
    fun setMotionEventPointerId(pointerId: Int)

    /**
     * @return
     // */
    fun getMotionEventPointerId(): Int
}