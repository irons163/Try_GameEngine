package com.example.try_gameengine.test

import com.example.try_gameengine.action.MovementAction
import com.example.try_gameengine.action.MovementActionFactory
import com.example.try_gameengine.action.MovementActionItemCountDownTimer
import com.example.try_gameengine.action.MovementActionSetWithThread

class LRMovementActionFactory : MovementActionFactory() {
    override fun createMovementAction(): MovementAction? {
        // TODO Auto-generated method stub
        if (action == null) action = MovementActionSetWithThread()

        action.addMovementAction(MovementActionItemCountDownTimer(5000, 1000, -10, 0))
        action.addMovementAction(MovementActionItemCountDownTimer(5000, 1000, 10, 0))
        return action
    }
}
