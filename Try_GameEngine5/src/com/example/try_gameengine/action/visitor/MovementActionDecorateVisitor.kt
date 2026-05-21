package com.example.try_gameengine.action.visitor

import com.example.try_gameengine.action.MovementAction

class MovementActionDecorateVisitor : IMovementActionVisitor {
    override fun visitComposite(movementAction: MovementAction) {
        // TODO Auto-generated method stub
//		movementAction.createMovementActionMemento();
    }

    override fun visitLeaf(movementAction: MovementAction) {
        // TODO Auto-generated method stub
//		movementAction.doIn();
    }
}
