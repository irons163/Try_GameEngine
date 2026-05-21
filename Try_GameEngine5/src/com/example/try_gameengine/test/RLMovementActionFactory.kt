package com.example.try_gameengine.test

import android.util.Log
import com.example.try_gameengine.action.DoubleDecorator
import com.example.try_gameengine.action.MovementAction
import com.example.try_gameengine.action.MovementActionFactory
import com.example.try_gameengine.action.MovementActionItemCountDownTimer
import com.example.try_gameengine.action.MovementActionSetWithThread

class RLMovementActionFactory : MovementActionFactory() {
    override fun createMovementAction(): MovementAction {
        // TODO Auto-generated method stub
        var newAction: MovementAction

        if (action == null) {
            newAction = DoubleDecorator(MovementActionSetWithThread())
        } else newAction = DoubleDecorator(MovementActionSetWithThread())
        newAction.addMovementAction(
            DoubleDecorator(
                MovementActionItemCountDownTimer(
                    1000,
                    200,
                    10,
                    0,
                    "R"
                )
            )
        )
        newAction.addMovementAction(
            DoubleDecorator(
                MovementActionItemCountDownTimer(
                    1000,
                    200,
                    -10,
                    0,
                    "L"
                )
            )
        )

        if (action != null) {
            action.addMovementAction(newAction)
            newAction = action
        }

        Log.i("MovementDescription", newAction.getDescription())

        return newAction
    }
}
