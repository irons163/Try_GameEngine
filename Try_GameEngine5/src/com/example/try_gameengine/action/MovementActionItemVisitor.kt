package com.example.try_gameengine.action

import com.example.try_gameengine.action.visitor.IMovementActionVisitor

/**
 * @author irons
 // */
class MovementActionItemVisitor(movementDecorator: MovementDecorator) : IMovementActionVisitor {
    private val movementDecorator: MovementDecorator

    /**
     * MovementActionItemVisitor
     * @param movementDecorator
     // */
    init {
        this.movementDecorator = movementDecorator
    }

    override fun visitComposite(movementAction: MovementAction) {
//		if(movementAction.getInfo()!=null)
//			movementDecorator.coreCalculationMovementActionInfo(movementAction);	
    }

    override fun visitLeaf(movementAction: MovementAction) {
        if (movementAction.getInfo() != null) {
            movementDecorator.coreCalculationMovementActionInfo(movementAction)
        }
    }
}
