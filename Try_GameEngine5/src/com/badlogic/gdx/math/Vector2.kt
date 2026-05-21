/*******************************************************************************
 * Copyright 2010 Mario Zechner (contact@badlogicgames.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 // */
package com.badlogic.gdx.math

import kotlin.math.sqrt

/**
 * Encapsulates a 2D vector. Allows chaining methods by returning a reference to
 * itself
 * 
 * @author badlogicgames@gmail.com
 // */
class Vector2 {
    /** the x-component of this vector *  */
	@JvmField
	var x: Float = 0f

    /** the y-component of this vector *  */
	@JvmField
	var y: Float = 0f

    /**
     * Constructs a new vector at (0,0)
     // */
    constructor()

    /**
     * Constructs a vector with the given components
     * 
     * @param x
     * The x-component
     * @param y
     * The y-component
     // */
    constructor(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    /**
     * Constructs a vector from the given vector
     * 
     * @param v
     * The vector
     // */
    constructor(v: Vector2) {
        set(v)
    }

    fun length(): Float {
        return sqrt((x * x + y * y).toDouble()).toFloat()
    }

    /** Return the squared length of this vector.  */
    fun lengthSquared(): Float {
        return (x * x + y * y)
    }

    /**
     * @return a copy of this vector
     // */
    fun cpy(): Vector2 {
        return Vector2(this)
    }

    /**
     * @return The euclidian length
     // */
    fun len(): Float {
        return sqrt((x * x + y * y).toDouble()).toFloat()
    }

    /**
     * @return The squared euclidian length
     // */
    fun len2(): Float {
        return x * x + y * y
    }

    /**
     * Sets this vector from the given vector
     * 
     * @param v
     * The vector
     * @return This vector for chaining
     // */
    fun set(v: Vector2): Vector2 {
        x = v.x
        y = v.y
        return this
    }

    /**
     * Sets the components of this vector
     * 
     * @param x
     * The x-component
     * @param y
     * The y-component
     * @return This vector for chaining
     // */
    fun set(x: Float, y: Float): Vector2 {
        this.x = x
        this.y = y
        return this
    }

    /**
     * Substracts the given vector from this vector.
     * 
     * @param v
     * The vector
     * @return This vector for chaining
     // */
    fun sub(v: Vector2): Vector2 {
        x -= v.x
        y -= v.y
        return this
    }

    /**
     * Normalizes this vector
     * 
     * @return This vector for chaining
     // */
    fun nor(): Vector2 {
        val len = len()
        if (len != 0f) {
            x /= len
            y /= len
        }
        return this
    }

    /**
     * Adds the given vector to this vector
     * 
     * @param v
     * The vector
     * @return This vector for chaining
     // */
    fun add(v: Vector2): Vector2 {
        x += v.x
        y += v.y
        return this
    }

    /**
     * Adds the given components to this vector
     * 
     * @param x
     * The x-component
     * @param y
     * The y-component
     * @return This vector for chaining
     // */
    fun add(x: Float, y: Float): Vector2 {
        this.x += x
        this.y += y
        return this
    }

    /**
     * @param v
     * The other vector
     * @return The dot product between this and the other vector
     // */
    fun dot(v: Vector2): Float {
        return x * v.x + y * v.y
    }

    /**
     * Multiplies this vector by a scalar
     * 
     * @param scalar
     * The scalar
     * @return This vector for chaining
     // */
    fun mul(scalar: Float): Vector2 {
        x *= scalar
        y *= scalar
        return this
    }

    /**
     * @param v
     * The other vector
     * @return the distance between this and the other vector
     // */
    fun dst(v: Vector2): Float {
        val x_d = v.x - x
        val y_d = v.y - y
        return sqrt((x_d * x_d + y_d * y_d).toDouble()).toFloat()
    }

    /**
     * @param x
     * The x-component of the other vector
     * @param y
     * The y-component of the other vector
     * @return the distance between this and the other vector
     // */
    fun dst(x: Float, y: Float): Float {
        val x_d = x - this.x
        val y_d = y - this.y
        return sqrt((x_d * x_d + y_d * y_d).toDouble()).toFloat()
    }

    /**
     * @param v
     * The other vector
     * @return the squared distance between this and the other vector
     // */
    fun dst2(v: Vector2): Float {
        val x_d = v.x - x
        val y_d = v.y - y
        return x_d * x_d + y_d * y_d
    }

    override fun toString(): String {
        return "[" + x + ":" + y + "]"
    }

    /**
     * Substracts the other vector from this vector.
     * 
     * @param x
     * The x-component of the other vector
     * @param y
     * The y-component of the other vector
     * @return This vector for chaining
     // */
    fun sub(x: Float, y: Float): Vector2 {
        this.x -= x
        this.y -= y
        return this
    }

    /**
     * @return a temporary copy of this vector. Use with care as this is backed
     * by a single static Vector2 instance. v1.tmp().add( v2.tmp() )
     * will not work!
     // */
    fun tmp(): Vector2 {
        return tmp.set(this)
    }

    val isValid: Boolean
        get() = x != Float.Companion.NaN && x != Float.Companion.NEGATIVE_INFINITY && x != Float.Companion.POSITIVE_INFINITY && y != Float.Companion.NaN && y != Float.Companion.NEGATIVE_INFINITY && y != Float.Companion.POSITIVE_INFINITY

    public fun clone(): Vector2 {
        return Vector2(x, y)
    }

    companion object {
        /** static temporary vector *  */
        private val tmp = Vector2()

        /*
	 * Static
	 // */
        fun abs(a: Vector2): Vector2 {
            return Vector2(kotlin.math.abs(a.x), kotlin.math.abs(a.y))
        }

        fun dot(a: Vector2, b: Vector2): Float {
            return a.x * b.x + a.y * b.y
        }

        fun cross(a: Vector2, b: Vector2): Float {
            return a.x * b.y - a.y * b.x
        }

        fun cross(a: Vector2, s: Float): Vector2 {
            return Vector2(s * a.y, -s * a.x)
        }

        fun cross(s: Float, a: Vector2): Vector2 {
            return Vector2(-s * a.y, s * a.x)
        }

        @JvmStatic
		fun min(a: Vector2, b: Vector2): Vector2 {
            return Vector2(if (a.x < b.x) a.x else b.x, if (a.y < b.y) a.y else b.y)
        }

        @JvmStatic
		fun max(a: Vector2, b: Vector2): Vector2 {
            return Vector2(if (a.x > b.x) a.x else b.x, if (a.y > b.y) a.y else b.y)
        }
    }
}
