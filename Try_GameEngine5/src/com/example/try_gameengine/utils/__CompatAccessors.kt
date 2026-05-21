@file:Suppress("unused", "FunctionName")
package com.example.try_gameengine.utils

import android.graphics.PointF
import android.graphics.RectF
import android.util.Log
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

internal fun DetectArea.getDetectAreaType() = this.detectAreaType
internal fun DetectArea.getObjectTag() = this.objectTag
internal fun DetectArea.getSpriteDetectAreaListener() = this.spriteDetectAreaListener
internal fun DetectArea.getSuccessor() = this.successor
internal fun DetectArea.getTag() = this.tag
internal fun DetectArea.setDetectAreaType(value: DetectAreaType?) { this.detectAreaType = value }
internal fun DetectArea.setObjectTag(value: Any?) { this.objectTag = value }
internal fun DetectArea.setSpriteDetectAreaListener(value: ISpriteDetectAreaListener?) { this.spriteDetectAreaListener = value }
internal fun DetectArea.setSuccessor(value: DetectArea?) { this.successor = value }
internal fun DetectArea.setTag(value: String?) { this.tag = value }
internal fun DetectAreaPoint.getPoint() = this.point
internal fun DetectAreaRound.getRadius() = this.radius
internal fun DetectAreaRound.setRadius(value: Float) { this.radius = value }
internal fun GameTimeUtil.isArriveExecuteTime() = this.isArriveExecuteTime
internal fun GameTimeUtil.isArriveExecuteTimeIfOneDelayThenAllDelay() = this.isArriveExecuteTimeIfOneDelayThenAllDelay
internal fun SpriteDetectAreaBehavior.getSpriteDetectAreaListener() = this.spriteDetectAreaListener
internal fun SpriteDetectAreaBehavior.setSpriteDetectAreaListener(value: ISpriteDetectAreaListener?) { this.spriteDetectAreaListener = value }
