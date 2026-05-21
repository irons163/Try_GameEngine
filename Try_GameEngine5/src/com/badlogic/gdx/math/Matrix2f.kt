package com.badlogic.gdx.math

import kotlin.math.cos
import kotlin.math.sin


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
 * @version 0.1
 // */
class Matrix2f {
    var col1: Vector2f = Vector2f()

    var col2: Vector2f = Vector2f()

    constructor()

    constructor(angle: Float) {
        val c = cos(angle.toDouble()).toFloat()
        val s = sin(angle.toDouble()).toFloat()
        col1.x = c
        col2.x = -s
        col1.y = s
        col2.y = c
    }

    constructor(col1: Vector2f, col2: Vector2f) {
        this.col1.set(col1)
        this.col2.set(col2)
    }

    fun multiply(vec: Vector2f): Vector2f {
        val x = col1.x * vec.x + col2.x * vec.y
        val y = col1.y * vec.x + col2.y * vec.y
        return Vector2f(x, y)
    }

    fun transpose(): Matrix2f {
        return Matrix2f(
            Vector2f(col1.x, col2.x), Vector2f(
                col1.y,
                col2.y
            )
        )
    }

    fun invert(): Matrix2f {
        val a = col1.x
        val b = col2.x
        val c = col1.y
        val d = col2.y
        val m2d = Matrix2f()
        var det = a * d - b * c
        if (det == 0.0f) {
            throw RuntimeException("det == 0.0")
        }

        det = 1.0f / det
        m2d.col1.x = det * d
        m2d.col2.x = -det * b
        m2d.col1.y = -det * c
        m2d.col2.y = det * a
        return m2d
    }
}
