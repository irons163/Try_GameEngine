package com.example.try_gameengine.remotecontroller

import android.view.MotionEvent

/**
 * `Command` is a default command handler for 4D(4 Direction keys
 * , total 4 keys.
 * 
 * @author irons
 // */
interface Command {
    /**
     * @param x
     * @param y
     * @param event
     * @return
     // */
    fun checkExecute(x: Float, y: Float, event: MotionEvent?): Boolean

    /**
     * @return
     // */
    fun execute(): RemoteController.CommandType?

    /**
     * @param pointerId
     // */
    fun setMotionEventPointerId(pointerId: Int)

    /**
     * @return
     // */
    fun getMotionEventPointerId(): Int
}
