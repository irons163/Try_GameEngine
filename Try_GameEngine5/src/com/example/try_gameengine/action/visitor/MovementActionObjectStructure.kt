package com.example.try_gameengine.action.visitor

import com.example.try_gameengine.action.MovementAction

/**
 * `MovementActionObjectStructure` is use for set strucuar
 * @author irons
 // */
class MovementActionObjectStructure {
    private var root: MovementAction? = null

    fun handleRequest(movementActionVisitor: IMovementActionVisitor?) {
        if (root != null) {
            root!!.accept(movementActionVisitor!!)
        }
    }

    fun setRoot(movementAction: MovementAction?) {
        this.root = movementAction
    }
}
