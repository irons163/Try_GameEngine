package com.example.try_gameengine.action.visitor

import com.example.try_gameengine.action.MovementAction

/**
 * `IMovementActionVisitor` is a interface for the movementAction visitor. Can easy access one of movements composites.
 * @author irons
 // */
interface IMovementActionVisitor {
    /**
     * `visitComposite` visit all the composite part of target movementAction.
     * @param movementAction
     * target for visit.
     // */
    fun visitComposite(movementAction: MovementAction)

    /**
     * `visitLeaf` visit all the leaf part of target movementAction.
     * @param movementAction
     * target for visit.
     // */
    fun visitLeaf(movementAction: MovementAction)
}
