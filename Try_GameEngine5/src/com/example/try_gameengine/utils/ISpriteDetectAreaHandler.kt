package com.example.try_gameengine.utils

/**
 * `ISpriteDetectAreaHandler` is use for deal with detect area controller.
 * @author irons
 // */
interface ISpriteDetectAreaHandler {
    fun getSpriteDetectAreaBehavior(): ISpriteDetectAreaBehavior?
    fun setSpriteDetectAreaBehavior(spriteDetectAreaBehavior: ISpriteDetectAreaBehavior?)
}

