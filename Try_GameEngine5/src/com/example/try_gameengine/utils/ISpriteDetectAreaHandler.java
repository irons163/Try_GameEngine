package com.example.try_gameengine.utils;

/**
 * {@code ISpriteDetectAreaHandler} is use for deal with detect area controller.
 * @author irons
 *
 */
public interface ISpriteDetectAreaHandler {
	public void setSpriteDetectAreaBehavior(ISpriteDetectAreaBehavior spriteDetectAreaBehavior);
	public ISpriteDetectAreaBehavior getSpriteDetectAreaBehavior();
}

