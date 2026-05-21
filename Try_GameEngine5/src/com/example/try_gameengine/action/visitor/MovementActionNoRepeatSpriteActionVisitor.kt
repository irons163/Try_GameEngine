package com.example.try_gameengine.action.visitor

import com.example.try_gameengine.action.MovementAction

/**
 * @author irons
 // */
class MovementActionNoRepeatSpriteActionVisitor : IMovementActionVisitor {
    override fun visitComposite(movementAction: MovementAction) {
        // TODO Auto-generated method stub
        movementAction.isRepeatSpriteActionIfMovementActionRepeat = false
    }

    override fun visitLeaf(movementAction: MovementAction) {
        // TODO Auto-generated method stub
        movementAction.isRepeatSpriteActionIfMovementActionRepeat = false
    }
}
