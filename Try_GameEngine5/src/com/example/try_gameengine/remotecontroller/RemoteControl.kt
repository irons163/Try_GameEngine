package com.example.try_gameengine.remotecontroller

import android.view.MotionEvent

/**
 * @author irons
 // */
class RemoteControl {
    var slot: Command? = null
    var onCommands: Array<Command>
    var offCommands: Array<Command>

    init {
        val noCommand: Command = NoCommand()
        onCommands = Array(7) { noCommand }
        offCommands = Array(7) { noCommand }
    }

    fun setCommand(command: Command?) {
        slot = command
    }

    fun setCommand(slot: Int, onCommand: Command?, offCommand: Command?) {
        onCommands[slot] = onCommand!!
        offCommands[slot] = offCommand!!
    }

    fun executePressDown(
        x: Float, y: Float,
        motionEventPointerId: Int, event: MotionEvent?
    ): RemoteController.CommandType? {
        var commandType: RemoteController.CommandType? = RemoteController.CommandType.None
        for (command in onCommands) {
            if (command.checkExecute(x, y, event)) {
                commandType = command.execute()
                command.setMotionEventPointerId(motionEventPointerId)
                break
            }
        }
        return commandType
    }

    fun executePressUp(
        x: Float, y: Float,
        motionEventPointerId: Int, event: MotionEvent?
    ): RemoteController.CommandType? {
        var commandType: RemoteController.CommandType? = RemoteController.CommandType.None

        for (command in offCommands) {
            if (command.checkExecute(x, y, event)) {
                commandType = command.execute()
                break
            }
        }

        return commandType
    }
}
