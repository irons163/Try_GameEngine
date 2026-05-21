package com.example.try_gameengine.remotecontroller.custom

import android.view.MotionEvent
import com.example.try_gameengine.framework.ALayer

class CustomRemoteControl {
    var onCommands: MutableList<CustomCommand>
    var offCommands: MutableList<CustomCommand>

    init {
        onCommands = ArrayList<CustomCommand>()
        offCommands = ArrayList<CustomCommand>()
        //		CustomCommand noCommand = null;
//		for(int i=0; i<7; i++){
//			onCommands[i] = noCommand;
//			offCommands[i] = noCommand;
//		}
    }

    fun addCommand(onCommand: CustomCommand?, offCommand: CustomCommand?) {
        onCommands.add(onCommand!!)
        offCommands.add(onCommand)
    }

    fun executePressDown(
        x: Float,
        y: Float,
        motionEventPointerId: Int,
        event: MotionEvent?
    ): CustomTouch? {
        var commandType: ALayer? = null
        for (command in onCommands) {
            if (command.checkExecute(x, y, event)) {
                commandType = command.execute()
                command.setMotionEventPointerId(motionEventPointerId)
                //				break;
                return CustomTouch(commandType, event)
            }
        }
        return null
    }

    fun executePressUp(
        x: Float,
        y: Float,
        motionEventPointerId: Int,
        event: MotionEvent?
    ): CustomTouch? {
        var commandType: ALayer? = null

        for (command in offCommands) {
            if (command.checkExecute(x, y, event)) {
                commandType = command.execute()
                return CustomTouch(commandType, event)
            }
        }

        return null
    }
}
