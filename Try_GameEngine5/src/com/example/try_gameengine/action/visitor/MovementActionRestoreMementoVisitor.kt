package com.example.try_gameengine.action.visitor

import com.example.try_gameengine.action.MovementAction

/**
 * @author irons
 // */
class MovementActionRestoreMementoVisitor : IMovementActionVisitor {
    override fun visitComposite(movementAction: MovementAction) {
        // TODO Auto-generated method stub
        movementAction.restoreMovementActionMemento(null)
    }

    override fun visitLeaf(movementAction: MovementAction) {
        // TODO Auto-generated method stub
        movementAction.restoreMovementActionMemento(null)
    }
}
