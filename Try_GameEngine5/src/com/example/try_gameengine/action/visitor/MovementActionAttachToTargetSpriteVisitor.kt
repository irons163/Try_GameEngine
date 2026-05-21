package com.example.try_gameengine.action.visitor

import com.example.try_gameengine.action.MovementAction
import com.example.try_gameengine.framework.Sprite

/**
 * @author irons
 // */
class MovementActionAttachToTargetSpriteVisitor(sprite: Sprite?) : IMovementActionVisitor {
    private val sprite: Sprite?

    /**
     * MovementActionAttachToTargetSpriteVisitor
     * @param sprite
     // */
    init {
        this.sprite = sprite
    }

    override fun visitComposite(movementAction: MovementAction) {
        if (movementAction.getInfo() != null) {
            movementAction.getInfo().setSprite(sprite)
        }
    }

    override fun visitLeaf(movementAction: MovementAction) {
        if (movementAction.getInfo() != null) {
            movementAction.getInfo().setSprite(sprite)
        }
    }
}
