package com.example.try_gameengine.action.visitor

import com.example.try_gameengine.action.MovementAction
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener
import com.example.try_gameengine.framework.Sprite

/**
 * @author irons
 // */
class MovementActionSetDefaultTimeOnTickListenerIfNotSetYetVisitor(sprite: Sprite) :
    IMovementActionVisitor {
    private val sprite: Sprite
    private val defaultTimerOnTickListener: TimerOnTickListener = object : TimerOnTickListener {
        override fun onTick(dx: Float, dy: Float) {
            // TODO Auto-generated method stub
            sprite.move(dx, dy)
        }
    }

    /**
     * @param sprite
     // */
    init {
        // TODO Auto-generated constructor stub
        this.sprite = sprite
    }

    override fun visitComposite(movementAction: MovementAction) {
        // TODO Auto-generated method stub
        if (movementAction.getTimerOnTickListener() == null) {
            movementAction.setTimerOnTickListener(defaultTimerOnTickListener)
        }
    }

    override fun visitLeaf(movementAction: MovementAction) {
        // TODO Auto-generated method stub
        if (movementAction.getTimerOnTickListener() == null) {
            movementAction.setTimerOnTickListener(defaultTimerOnTickListener)
        }
    }
}
