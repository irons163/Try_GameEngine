package com.example.try_gameengine.action


/**
 * MovementAtionController can control some work.
 * @author irons
 // */
class MovementAtionController {
    var action: MovementAction? = null

    /**
     * @param action
     // */
    fun setMovementAction(action: MovementAction) {
        this.action = action
    }

    /**
     * 
     // */
    fun cancelCurrentMove() {
        action!!.cancelMove()
    }

    /**
     * 
     // */
    fun cancelAllMove() {
        action!!.cancelAllMove()
    }

    /**
     * 
     // */
    fun pause() {
        action!!.pause()
    }

    fun pause(milliseconds: Long) {
        action!!.pause()
    }

    /**
     * 
     // */
    fun resume() {
        action!!.pause()
    }

    /**
     * 
     // */
    fun restart() {
        action!!.cancelMove()
        action!!.start()
    }

    /**
     * @param loop
     // */
    fun looper(loop: Boolean) {
        if (action!!.isFinish()) {
        }
    }
}
