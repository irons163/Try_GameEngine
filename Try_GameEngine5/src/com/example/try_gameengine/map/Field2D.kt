package com.example.try_gameengine.map

import com.badlogic.gdx.math.Vector2f
import java.io.IOException
import kotlin.math.abs

/**
 * Copyright 2008 - 2011
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
 * @email ceponline@yahoo.com.cn
 * @version 0.1.1
 // */
class Field2D : Config {
    private var result: ArrayList<Vector2f>? = null

    var map: Array<IntArray?> = emptyArray()

    var tileWidth: Int = 0
    var tileHeight: Int = 0

    var width: Int = 0
        private set
    var height: Int = 0
        private set

    constructor(fileName: String?, w: Int, h: Int) {
        try {
            set(TileMapConfig.Companion.loadAthwartArray(fileName), w, h)
        } catch (e: IOException) {
            throw RuntimeException(e.message)
        }
    }

    @JvmOverloads
    constructor(data: Array<IntArray?>, w: Int = 0, h: Int = 0) {
        this.set(data, w, h)
    }

    fun set(data: Array<IntArray?>, w: Int, h: Int) {
        this.map = data
        this.tileWidth = w
        this.tileHeight = h
        this.width = data[0]!!.size
        this.height = data.size
    }

    fun pixelsToTilesWidth(x: Int): Int {
        return x / tileWidth
    }

    fun pixelsToTilesHeight(y: Int): Int {
        return y / tileHeight
    }

    fun tilesToWidthPixels(tiles: Int): Int {
        return tiles * tileWidth
    }

    fun tilesToHeightPixels(tiles: Int): Int {
        return tiles * tileHeight
    }

    fun getType(x: Int, y: Int): Int {
        try {
            return this.map[x]!![y]
        } catch (e: Exception) {
            return -1
        }
    }

    fun isHit(point: Vector2f): Boolean {
        if (get(this.map, point) != -1) {
            return true
        }
        return false
    }

    fun isHit(px: Int, py: Int): Boolean {
        if (get(this.map, px, py) != -1) {
            return true
        }
        return false
    }

    fun neighbors(px: Int, py: Int, flag: Boolean): Array<IntArray?> {
        val pos = Array<IntArray?>(8) { IntArray(2) }
        insertArrays(pos, 0, px, py - 1)
        insertArrays(pos, 0, px + 1, py)
        insertArrays(pos, 0, px, py + 1)
        insertArrays(pos, 0, px - 1, py)
        if (flag) {
            insertArrays(pos, 0, px - 1, py - 1)
            insertArrays(pos, 0, px + 1, py - 1)
            insertArrays(pos, 0, px + 1, py + 1)
            insertArrays(pos, 0, px - 1, py + 1)
        }
        return pos
    }

    fun neighbors(pos: Vector2f, flag: Boolean): ArrayList<Vector2f> {
        if (result == null) {
            result = ArrayList<Vector2f>(8)
        } else {
            result!!.clear()
        }
        val x = pos.x()
        val y = pos.y()
        result!!.add(Vector2f(x.toFloat(), (y - 1).toFloat()))
        result!!.add(Vector2f((x + 1).toFloat(), y.toFloat()))
        result!!.add(Vector2f(x.toFloat(), (y + 1).toFloat()))
        result!!.add(Vector2f((x - 1).toFloat(), y.toFloat()))
        if (flag) {
            result!!.add(Vector2f((x - 1).toFloat(), (y - 1).toFloat()))
            result!!.add(Vector2f((x + 1).toFloat(), (y - 1).toFloat()))
            result!!.add(Vector2f((x + 1).toFloat(), (y + 1).toFloat()))
            result!!.add(Vector2f((x - 1).toFloat(), (y + 1).toFloat()))
        }
        return result!!
    }

    fun score(goal: Vector2f, point: Vector2f): Int {
        return abs(point.x() - goal.x()) + abs(point.y() - goal.y())
    }

    fun score(x: Int, y: Int, px: Int, py: Int): Int {
        return abs(px - x) + abs(py - y)
    }

    private fun get(data: Array<IntArray?>, px: Int, py: Int): Int {
        try {
            if (px < width && py < height) {
                return data[py]!![px]
            } else {
                return -1
            }
        } catch (e: Exception) {
            return -1
        }
    }

    private fun get(data: Array<IntArray?>, point: Vector2f): Int {
        try {
            if (point.x() < width && point.y() < height) {
                return data[point.y()]!![point.x()]
            } else {
                return -1
            }
        } catch (e: Exception) {
            return -1
        }
    }

    companion object {
        private var vector2: Vector2f? = null

        private val directions: MutableMap<Vector2f?, Int?> = HashMap<Vector2f?, Int?>(
            9
        )

        private val directionValues: MutableMap<Int?, Vector2f?> = HashMap<Int?, Vector2f?>(
            9
        )

        init {
            directions.put(Vector2f(0f, 0f), Config.Companion.EMPTY)
            directions.put(Vector2f(1f, -1f), Config.Companion.UP)
            directions.put(Vector2f(-1f, -1f), Config.Companion.LEFT)
            directions.put(Vector2f(1f, 1f), Config.Companion.RIGHT)
            directions.put(Vector2f(-1f, 1f), Config.Companion.DOWN)
            directions.put(Vector2f(0f, -1f), Config.Companion.TUP)
            directions.put(Vector2f(-1f, 0f), Config.Companion.TLEFT)
            directions.put(Vector2f(1f, 0f), Config.Companion.TRIGHT)
            directions.put(Vector2f(0f, 1f), Config.Companion.TDOWN)

            directionValues.put(Config.Companion.EMPTY, Vector2f(0f, 0f))
            directionValues.put(Config.Companion.UP, Vector2f(1f, -1f))
            directionValues.put(Config.Companion.LEFT, Vector2f(-1f, -1f))
            directionValues.put(Config.Companion.RIGHT, Vector2f(1f, 1f))
            directionValues.put(Config.Companion.DOWN, Vector2f(-1f, 1f))
            directionValues.put(Config.Companion.TUP, Vector2f(0f, -1f))
            directionValues.put(Config.Companion.TLEFT, Vector2f(-1f, 0f))
            directionValues.put(Config.Companion.TRIGHT, Vector2f(1f, 0f))
            directionValues.put(Config.Companion.TDOWN, Vector2f(0f, 1f))
        }

        fun getDirection(x: Int, y: Int): Int {
            if (vector2 == null) {
                vector2 = Vector2f(x.toFloat(), y.toFloat())
            } else {
                vector2!!.set(x.toFloat(), y.toFloat())
            }
            return directions.get(vector2)!!
        }

        fun getDirection(type: Int): Vector2f? {
            return directionValues.get(type)
        }

        private fun insertArrays(arrays: Array<IntArray?>, index: Int, px: Int, py: Int) {
            arrays[index]!![0] = px
            arrays[index]!![1] = py
        }
    }
}
