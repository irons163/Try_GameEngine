package com.example.try_gameengine.test

import com.example.try_gameengine.action.MovementAction
import com.example.try_gameengine.action.MovementActionFactory
import com.example.try_gameengine.action.MovementActionItemCountDownTimer
import com.example.try_gameengine.action.MovementActionSetWithThread

class DUMovementActionFactory : MovementActionFactory() {
    override fun createMovementAction(): MovementAction? {
        // TODO Auto-generated method stub
        if (action == null) action = MovementActionSetWithThread()
        action.addMovementAction(MovementActionItemCountDownTimer(30000, 1000, 0, 10))
        action.addMovementAction(MovementActionItemCountDownTimer(30000, 1000, 0, -10))
        return action
    }
}
