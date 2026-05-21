package com.example.try_gameengine.action.visitor

import com.example.try_gameengine.action.MovementAction

class MovementActionCreateMementoVisitor : IMovementActionVisitor {
    override fun visitComposite(movementAction: MovementAction) {
        // TODO Auto-generated method stub
        movementAction.createMovementActionMemento()
    }

    override fun visitLeaf(movementAction: MovementAction) {
        // TODO Auto-generated method stub
        movementAction.createMovementActionMemento()
    }
}
