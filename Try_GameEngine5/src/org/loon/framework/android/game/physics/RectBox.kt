package org.loon.framework.android.game.physics

import android.graphics.Point
import android.graphics.RectF


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
 * @email嚗eponline@yahoo.com.cn
 * @version 0.1.2
 // */
class RectBox {
    var x: Int = 0

    var y: Int = 0

    var width: Int = 0

    var height: Int = 0

    constructor() {
        setBounds(0, 0, 0, 0)
    }

    constructor(x: Double, y: Double, width: Double, height: Double) {
        setBounds(x, y, width, height)
    }

    constructor(x: Int, y: Int, width: Int, height: Int) {
        setBounds(x, y, width, height)
    }

    constructor(rect: RectBox) {
        setBounds(rect.x, rect.y, rect.width, rect.height)
    }

    fun setBounds(rect: RectBox) {
        setBounds(rect.x, rect.y, rect.width, rect.height)
    }

    fun setBounds(x: Int, y: Int, width: Int, height: Int) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    fun setBounds(x: Double, y: Double, width: Double, height: Double) {
        this.x = x.toInt()
        this.y = y.toInt()
        this.width = width.toInt()
        this.height = height.toInt()
    }

    fun setLocation(r: RectBox) {
        this.x = r.x
        this.y = r.y
    }

    fun setLocation(r: Point) {
        this.x = r.x
        this.y = r.y
    }

    fun setLocation(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    fun copy(other: RectBox) {
        this.x = other.x
        this.y = other.y
        this.width = other.width
        this.height = other.height
    }

    val minX: Int
        get() = this.x

    val minY: Int
        get() = this.y

    val maxX: Int
        get() = this.x + this.width

    val maxY: Int
        get() = this.y + this.height

    val right: Int
        get() = this.maxX

    val top: Int
        get() = this.maxY

    val middleX: Int
        get() = this.x + this.width / 2

    val middleY: Int
        get() = this.y + this.height / 2

    val centerX: Double
        get() = this.x + this.width / 2.0

    val centerY: Double
        get() = this.y + this.height / 2.0

    val rectangle2D: RectF
        get() = RectF(
            this.x.toFloat(),
            this.y.toFloat(),
            this.width.toFloat(),
            this.height.toFloat()
        )

    val rect: RectBox
        get() = this

    override fun equals(obj: Any?): Boolean {
        if (obj is RectBox) {
            val rect = obj
            return equals(rect.x, rect.y, rect.width, rect.height)
        } else {
            return false
        }
    }

    fun equals(x: Int, y: Int, width: Int, height: Int): Boolean {
        return (this.x == x && this.y == y && this.width == width && this.height == height)
    }

    val area: Int
        get() = width * height

    /**
     * 水平移动X坐标执行长度
     * 
     * @param xMod
     // */
    fun modX(xMod: Int) {
        x += xMod
    }

    /**
     * 水平移动Y坐标指定长度
     * 
     * @param yMod
     // */
    fun modY(yMod: Int) {
        y += yMod
    }

    /**
     * 水平移动Width指定长度
     * 
     * @param w
     // */
    fun modWidth(w: Int) {
        this.width += w
    }

    /**
     * 水平移动Height指定长度
     * 
     * @param h
     // */
    fun modHeight(h: Int) {
        this.height += h
    }

    /**
     * 检查是否包含指定坐标
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     // */
    /**
     * 检查是否包含指定坐标
     * 
     * @param x
     * @param y
     * @return
     // */
    @JvmOverloads
    fun contains(x: Int, y: Int, width: Int = 0, height: Int = 0): Boolean {
        return (x >= this.x && y >= this.y && ((x + width) <= (this.x + this.width)) && ((y + height) <= (this.y + this.height)))
    }

    /**
     * 检查是否包含指定坐标
     * 
     * @param rect
     * @return
     // */
    fun contains(rect: RectBox): Boolean {
        return contains(rect.x, rect.y, rect.width, rect.height)
    }

    /**
     * 设定矩形选框交集
     * 
     * @param rect
     * @return
     // */
    fun intersects(rect: RectBox): Boolean {
        return intersects(rect.x, rect.y, rect.width, rect.height)
    }

    fun intersects(x: Int, y: Int): Boolean {
        return intersects(0, 0, width, height)
    }

    /**
     * 设定矩形选框交集
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     // */
    fun intersects(x: Int, y: Int, width: Int, height: Int): Boolean {
        return x + width > this.x && x < this.x + this.width && y + height > this.y && y < this.y + this.height
    }

    /**
     * 设定矩形选框交集
     * 
     * @param rect
     // */
    fun intersection(rect: RectBox) {
        intersection(rect.x, rect.y, rect.width, rect.height)
    }

    /**
     * 设定矩形选框交集
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     // */
    fun intersection(x: Int, y: Int, width: Int, height: Int) {
        val x1 = kotlin.math.max(this.x, x)
        val y1 = kotlin.math.max(this.y, y)
        val x2 = kotlin.math.min(this.x + this.width - 1, x + width - 1)
        val y2 = kotlin.math.min(this.y + this.height - 1, y + height - 1)
        setBounds(x1, y1, kotlin.math.max(0, x2 - x1 + 1), kotlin.math.max(0, y2 - y1 + 1))
    }

    /**
     * 判断指定坐标是否在一条直线上
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     // */
    fun intersectsLine(
        x1: Int, y1: Int,
        x2: Int, y2: Int
    ): Boolean {
        return contains(x1, y1) || contains(x2, y2)
    }

    /**
     * 判定指定坐标是否位于当前RectBox内部
     * 
     * @param x
     * @param y
     * @return
     // */
    fun inside(x: Int, y: Int): Boolean {
        return (x >= this.x) && ((x - this.x) < this.width) && (y >= this.y)
                && ((y - this.y) < this.height)
    }

    /**
     * 返回当前的矩形选框交集
     * 
     * @param rect
     * @return
     // */
    fun getIntersection(rect: RectBox): RectBox {
        val x1 = kotlin.math.max(x, rect.x)
        val x2 = kotlin.math.min(x + width, rect.x + rect.width)
        val y1 = kotlin.math.max(y, rect.y)
        val y2 = kotlin.math.min(y + height, rect.y + rect.height)
        return RectBox(x1, y1, x2 - x1, y2 - y1)
    }

    /**
     * 合并矩形选框
     * 
     * @param rect
     // */
    fun union(rect: RectBox) {
        union(rect.x, rect.y, rect.width, rect.height)
    }

    /**
     * 合并矩形选框
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     // */
    fun union(x: Int, y: Int, width: Int, height: Int) {
        val x1 = kotlin.math.min(this.x, x)
        val y1 = kotlin.math.min(this.y, y)
        val x2 = kotlin.math.max(this.x + this.width - 1, x + width - 1)
        val y2 = kotlin.math.max(this.y + this.height - 1, y + height - 1)
        setBounds(x1, y1, x2 - x1 + 1, y2 - y1 + 1)
    }

    companion object {
        fun getIntersection(a: RectBox, b: RectBox): RectBox? {
            val a_x = a.x
            val a_r = a.right
            val a_y = a.y
            val a_t = a.top
            val b_x = b.x
            val b_r = b.right
            val b_y = b.y
            val b_t = b.top
            val i_x = kotlin.math.max(a_x, b_x)
            val i_r = kotlin.math.min(a_r, b_r)
            val i_y = kotlin.math.max(a_y, b_y)
            val i_t = kotlin.math.min(a_t, b_t)
            return if (i_x < i_r && i_y < i_t) RectBox(
                i_x, i_y, i_r - i_x, i_t
                        - i_y
            ) else null
        }
    }
}
