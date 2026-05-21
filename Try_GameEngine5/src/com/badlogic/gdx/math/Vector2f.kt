package com.badlogic.gdx.math

import com.example.try_gameengine.map.Field2D
import java.io.Serializable
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Copyright 2008 - 2010
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loonframework
 * @author chenpeng
 * @email：ceponline@yahoo.com.cn
 * @version 0.1.2
 // */
class Vector2f : Serializable, Cloneable {
    @JvmField
    var x: Float
    @JvmField
    var y: Float

    constructor(value: Float) : this(value, value)

    constructor(coords: FloatArray) {
        x = coords[0]
        y = coords[1]
    }

    constructor(x: Float = 0f, y: Float = 0f) {
        this.x = x
        this.y = y
    }

    constructor(vector2D: Vector2f) {
        this.x = vector2D.x
        this.y = vector2D.y
    }

    fun move(vector2D: Vector2f) {
        this.x += vector2D.x
        this.y += vector2D.y
    }

    fun move_multiples(direction: Int, multiples: Int) {
        var multiples = multiples
        if (multiples <= 0) {
            multiples = 1
        }
        val v: Vector2f = Field2D.Companion.getDirection(direction)!!
        move((v.x() * multiples).toFloat(), (v.y() * multiples).toFloat())
    }

    fun moveX(x: Int) {
        this.x += x.toFloat()
    }

    fun moveY(y: Int) {
        this.y += y.toFloat()
    }

    fun moveByAngle(degAngle: Int, distance: Float) {
        if (distance == 0f) {
            return
        }
        val Angle = Math.toRadians(degAngle.toDouble()).toFloat()
        val dX = (cos(Angle.toDouble()) * distance).toFloat()
        val dY = (-sin(Angle.toDouble()) * distance).toFloat()
        val idX = Math.round(dX)
        val idY = Math.round(dY)
        move(idX.toFloat(), idY.toFloat())
    }

    fun move(x: Float, y: Float) {
        this.x += x
        this.y += y
    }

    fun nearlyCompare(v: Vector2f, range: Int): Boolean {
        val dX = abs(x() - v.x())
        val dY = abs(y() - v.y())
        return (dX <= range) && (dY <= range)
    }

    fun angle(v: Vector2f): Int {
        val dx = v.x() - x()
        val dy = v.y() - y()
        val adx = abs(dx)
        val ady = abs(dy)
        if ((dy == 0) && (dx == 0)) {
            return 0
        }
        if ((dy == 0) && (dx > 0)) {
            return 0
        }
        if ((dy == 0) && (dx < 0)) {
            return 180
        }
        if ((dy > 0) && (dx == 0)) {
            return 90
        }
        if ((dy < 0) && (dx == 0)) {
            return 270
        }
        val rwinkel = atan((ady / adx).toDouble()).toFloat()
        var dwinkel = 0.0f
        if ((dx > 0) && (dy > 0)) {
            dwinkel = Math.toDegrees(rwinkel.toDouble()).toFloat()
        } else if ((dx < 0) && (dy > 0)) {
            dwinkel = (180.0f - Math.toDegrees(rwinkel.toDouble())).toFloat()
        } else if ((dx > 0) && (dy < 0)) {
            dwinkel = (360.0f - Math.toDegrees(rwinkel.toDouble())).toFloat()
        } else if ((dx < 0) && (dy < 0)) {
            dwinkel = (180.0f + Math.toDegrees(rwinkel.toDouble())).toFloat()
        }
        var iwinkel = dwinkel.toInt()
        if (iwinkel == 360) {
            iwinkel = 0
        }
        return iwinkel
    }

    fun setAngle(angle: Float) {
        var angle = angle
        if ((angle < -360) || (angle > 360)) {
            angle = angle % 360
        }
        if (angle < 0) {
            angle = 360 + angle
        }
        var oldAngle = getAngle().toDouble()
        if ((angle < -360) || (angle > 360)) {
            oldAngle = oldAngle % 360
        }
        if (oldAngle < 0) {
            oldAngle = 360 + oldAngle
        }
        val len = length()
        x = len * cos(StrictMath.toRadians(angle.toDouble())).toFloat()
        y = len * sin(StrictMath.toRadians(angle.toDouble())).toFloat()
    }

    fun getAngle(): Float {
        var theta = StrictMath.toDegrees(StrictMath.atan2(y.toDouble(), x.toDouble())).toFloat()
        if ((theta < -360) || (theta > 360)) {
            theta = theta % 360
        }
        if (theta < 0) {
            theta = 360 + theta
        }
        return theta
    }

    fun getCoords(): FloatArray? {
        return (floatArrayOf(x, y))
    }

    fun setLocation(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    override fun equals(o: Any?): Boolean {
        if (o is Vector2f) {
            val p = o
            return p.x == x && p.y == y
        }
        return false
    }

    override fun hashCode(): Int {
        return (x + y).toInt()
    }

    fun setX(x: Float) {
        this.x = x
    }

    fun setY(y: Float) {
        this.y = y
    }

    fun getX(): Float {
        return x
    }

    fun getY(): Float {
        return y
    }

    fun x(): Int {
        return x.toInt()
    }

    fun y(): Int {
        return y.toInt()
    }

    public override fun clone(): Any {
        return Vector2f(x, y)
    }

    fun set(other: Vector2f) {
        set(other.getX(), other.getY())
    }

    fun set(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun reverse(): Vector2f {
        x = -x
        y = -y
        return this
    }

    fun length(): Float {
        return sqrt((x * x + y * y).toDouble()).toFloat()
    }

    fun lengthSquared(): Float {
        return (x * x) + (y * y)
    }

    fun add(other: Vector2f): Vector2f {
        val x = this.x + other.x
        val y = this.y + other.y
        return Vector2f(x, y)
    }

    fun addThis(other: Vector2f): Vector2f {
        this.x += other.x
        this.y += other.y
        return this
    }

    fun sub(v: Vector2f): Vector2f {
        x -= v.getX()
        y -= v.getY()
        return this
    }

    fun subtract(other: Vector2f): Vector2f {
        val x = this.x - other.x
        val y = this.y - other.y
        return Vector2f(x, y)
    }

    fun dot(vec: Vector2f): Float {
        return (x * vec.x) + (y * vec.y)
    }

    fun cross(vec: Vector2f): Float {
        return x * vec.y - y * vec.x
    }

    fun multiply(value: Float): Vector2f {
        return Vector2f(value * x, value * y)
    }

    fun dotProduct(other: Vector2f): Float {
        return other.x * x + other.y * y
    }

    fun scale(a: Float): Vector2f {
        x *= a
        y *= a
        return this
    }

    fun normalize(): Vector2f {
        val magnitude = sqrt(dotProduct(this).toDouble()).toFloat()
        return Vector2f(x / magnitude, y / magnitude)
    }

    fun level(): Float {
        return sqrt(dotProduct(this).toDouble()).toFloat()
    }

    fun distanceSquared(other: Vector2f): Float {
        val dx = other.getX() - getX()
        val dy = other.getY() - getY()

        return (dx * dx) + (dy * dy)
    }

    fun distance(other: Vector2f): Float {
        return sqrt(distanceSquared(other).toDouble()).toFloat()
    }

    fun modulate(other: Vector2f): Vector2f {
        val x = this.x * other.x
        val y = this.y * other.y
        return Vector2f(x, y)
    }

    fun equalsDelta(other: Vector2f, delta: Float): Boolean {
        return (other.getX() - delta < x && other.getX() + delta > x && other.getY() - delta < y && other.getY() + delta > y)
    }

    fun rotate90() {
        setLocation(y, -x)
    }

    fun copy(): Vector2f {
        return Vector2f(x, y)
    }

    override fun toString(): String {
        return (StringBuffer("[Vector2f x:")).append(x).append(" y:")
            .append(y).append("]").toString()
    }

    companion object {
        /**
         * 
         // */
        private val serialVersionUID = -1844534518528011982L

        fun sum(summands: MutableList<*>): Vector2f {
            val result = Vector2f(0f, 0f)
            val it: MutableIterator<*> = summands.iterator()
            while (it.hasNext()) {
                val v = it.next() as Vector2f
                result.addThis(v)
            }
            return result
        }

        fun sum(a: Vector2f, b: Vector2f): Vector2f {
            val answer = Vector2f(a)
            return answer.addThis(b)
        }

        fun mean(points: MutableList<*>): Vector2f? {
            val n = points.size
            if (n == 0) {
                return Vector2f(0f, 0f)
            }
            return sum(points).scale(1.0f / n)
        }

        fun cross(a: Vector2f, b: Vector2f): Float {
            return a.cross(b)
        }

        fun getRealCordinates(
            relativeCoordinate: Vector2f,
            centroid: Vector2f, angularPosition: Float
        ): Vector2f {
            val answer = (Matrix2f(angularPosition)).multiply(
                relativeCoordinate
            ).add(centroid)
            return answer
        }

        fun difference(first: Vector2f, second: Vector2f): Vector2f {
            val answer = Vector2f(first)
            return answer.sub(second)
        }

        fun rotate90(vec: Vector2f): Vector2f {
            return Vector2f(-vec.y, vec.x)
        }

        fun rotate90R(vec: Vector2f): Vector2f {
            return Vector2f(vec.y, -vec.x)
        }

        fun dot(a: Vector2f, b: Vector2f): Float {
            return a.dot(b)
        }

        fun crossZ(a: Vector2f, b: Vector2f): Float {
            return a.x * b.y - a.y * b.x
        }

        fun mult(vector: Vector2f, scalar: Float): Vector2f {
            val answer = Vector2f(vector)
            return answer.scale(scalar)
        }
    }
}
