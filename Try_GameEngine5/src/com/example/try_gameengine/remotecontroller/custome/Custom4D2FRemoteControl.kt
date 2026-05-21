package com.example.try_gameengine.remotecontroller.custome

import android.view.MotionEvent

class Custom4D2FRemoteControl {
    var slot: Custom4D2FCommand? = null

    var onCommands: Array<Custom4D2FCommand>
    var offCommands: Array<Custom4D2FCommand>

    init {
        val noCommand: Custom4D2FCommand = Custom4D2FNoCommand()
        onCommands = Array(7) { noCommand }
        offCommands = Array(7) { noCommand }
    }

    fun setCommand(command: Custom4D2FCommand?) {
        slot = command
    }

    fun setCommand(slot: Int, onCommand: Custom4D2FCommand?, offCommand: Custom4D2FCommand?) {
        onCommands[slot] = onCommand!!
        offCommands[slot] = offCommand!!
    }

    fun executePressDown(
        x: Float,
        y: Float,
        motionEventPointerId: Int,
        event: MotionEvent?
    ): Custom4D2FCommandType? {
        var commandType: Custom4D2FCommandType? = Custom4D2FCommandType.None
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
        x: Float,
        y: Float,
        motionEventPointerId: Int,
        event: MotionEvent?
    ): Custom4D2FCommandType? {
        var commandType: Custom4D2FCommandType? = Custom4D2FCommandType.None

        for (command in offCommands) {
            if (command.checkExecute(x, y, event)) {
                commandType = command.execute()
                break
            }
        }

        return commandType
    }
}
