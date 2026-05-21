package com.example.try_gameengine.utils

import android.graphics.PointF

/**
 * `ISpriteDetectAreaBehavior` has a set of DetectArea methods. It can be detected the chain of member DetectArea.
 * @author irons
 // */
interface ISpriteDetectAreaBehavior {
    fun getSpriteDetectArea(): DetectArea?
    fun setSpriteDetectArea(spriteDetectArea: DetectArea?)
    fun updateSpriteDetectAreaCenter(center: PointF?)
    fun detect(requestDetectArea: DetectArea?): Boolean
    fun detect(requestDetectArea: IDetectAreaRequest?): Boolean
}
