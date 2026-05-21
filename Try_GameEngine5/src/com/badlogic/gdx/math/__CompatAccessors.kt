@file:Suppress("unused", "FunctionName")
package com.badlogic.gdx.math

import com.example.try_gameengine.map.Field2D
import java.io.Serializable
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

internal fun Matrix2f.getCol1() = this.col1
internal fun Matrix2f.getCol2() = this.col2
internal fun Matrix2f.setCol1(value: Vector2f) { this.col1 = value }
internal fun Matrix2f.setCol2(value: Vector2f) { this.col2 = value }
internal fun Vector2.getX() = this.x
internal fun Vector2.getY() = this.y
internal fun Vector2.isValid() = this.isValid
internal fun Vector2.setX(value: Float) { this.x = value }
internal fun Vector2.setY(value: Float) { this.y = value }
internal fun Vector2f.getX() = this.x
internal fun Vector2f.getY() = this.y
internal fun Vector2f.setX(value: Float) { this.x = value }
internal fun Vector2f.setY(value: Float) { this.y = value }
