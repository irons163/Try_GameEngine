@file:Suppress("unused", "FunctionName")
package com.example.try_gameengine.Camera

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.RectF
import com.example.try_gameengine.framework.ILayer
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

internal fun Camera.getClearColor() = this.clearColor
internal fun Camera.getLayer() = this.layer
internal fun Camera.getMatrix() = this.matrix
internal fun Camera.getOffsetX() = this.offsetX
internal fun Camera.getOffsetY() = this.offsetY
internal fun Camera.getRotation() = this.rotation
internal fun Camera.getViewPort() = this.viewPort
internal fun Camera.getViewPortRectF() = this.viewPortRectF
internal fun Camera.setClearColor(value: Int) { this.clearColor = value }
internal fun Camera.setLayer(value: ILayer?) { this.layer = value }
internal fun ViewPort.getHeight() = this.height
internal fun ViewPort.getMatrix() = this.matrix
internal fun ViewPort.getRotation() = this.rotation
internal fun ViewPort.getViewPortRectF() = this.viewPortRectF
internal fun ViewPort.getWidth() = this.width
internal fun ViewPort.getX() = this.x
internal fun ViewPort.getY() = this.y
internal fun ViewPort.setHeight(value: Float) { this.height = value }
internal fun ViewPort.setRotation(value: Float) { this.rotation = value }
internal fun ViewPort.setViewPortRectF(value: RectF) { this.viewPortRectF = value }
internal fun ViewPort.setWidth(value: Float) { this.width = value }
internal fun ViewPort.setX(value: Float) { this.x = value }
internal fun ViewPort.setY(value: Float) { this.y = value }
