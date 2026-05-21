package com.example.try_gameengine.map

import android.graphics.Bitmap
import com.badlogic.gdx.math.Vector2f

/**
 * Copyright 2008 - 2009
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
class TileMap @JvmOverloads constructor(
    var width: Int,
    var height: Int,
    private val tileWidth: Int = 32,
    private val tileHeight: Int = 32
) {
    private val tiles: Array<Bitmap?>

    private val offset: Vector2f

    /**
     * 获得滚动图
     * 
     * @return
     // */
    /**
     * 设置滚动图
     * 
     * @param velocity
     // */
    var scrollingVelocity: Vector2f? = null

    /**
     * 构造一个瓦片地图，并指定宽x高
     * 
     * @param width
     * @param height
     * @param tileWidth
     * @param tileHeight
     // */
    /**
     * 构造一个瓦片地图（默认大小32*32）
     * 
     * @param width
     * @param height
     // */
    init {
        tiles = arrayOfNulls<Bitmap>(width * height)
        offset = Vector2f(0f, 0f)
    }

    fun getTile(x: Int, y: Int): Bitmap? {
        return tiles[x + width * y]
    }

    fun setTile(x: Int, y: Int, img: Bitmap?) {
        tiles[x + width * y] = img
    }

    fun getTileFromPixels(x: Float, y: Float): Bitmap? {
        return getTileFromPixels(Vector2f(x, y))
    }

    fun getTileFromPixels(p: Vector2f): Bitmap? {
        val x = (p.getX() + offset.getX())
        val y = (p.getY() + offset.getY())
        val tileCoordinates = pixelsToTiles(x, y)
        return getTile(
            Math.round(tileCoordinates.getX()), Math
                .round(tileCoordinates.getY())
        )
    }

    fun pixelsToTiles(x: Float, y: Float): Vector2f {
        val xprime = x / tileWidth - 1
        val yprime = y / tileHeight - 1
        return Vector2f(xprime, yprime)
    }

    /**
     * 转换坐标为像素坐标
     * 
     * @param x
     * @param y
     * @return
     // */
    fun tilesToPixels(x: Float, y: Float): Vector2f {
        val xprime = x * tileWidth - offset.getX()
        val yprime = y * tileHeight - offset.getY()
        return Vector2f(xprime, yprime)
    }

    /**
     * 获得矫正后的碰撞位置
     * 
     * @param p
     * @param width
     * @param height
     * @return
     // */
    fun getCollision(p: Vector2f, width: Float, height: Float): Vector2f {
        val tile1 = getTileFromPixels(p.getX(), p.getY())
        val tile2 = getTileFromPixels(p.getX(), p.getY() + height)
        val tile3 = getTileFromPixels(p.getX() + width, p.getY())
        var x: Float
        var y: Float
        y = 0f
        x = y
        if (tile1 != null) {
            x = -1f
        } else if (tile3 != null) {
            x = 1f
        }
        if (tile2 != null) {
            y = 1f
        } else if (tile1 != null) {
            y = -1f
        }
        return Vector2f(x, y)
    }

    /**
     * 设置瓦片位置
     * 
     * @param x
     * @param y
     // */
    fun setOffset(x: Float, y: Float) {
        this.offset.setX(x)
        this.offset.setY(y)
    }

    /**
     * 设定偏移量
     * 
     * @param offset
     // */
    fun setOffset(offset: Vector2f) {
        this.offset.setX(offset.getX())
        this.offset.setY(offset.getY())
    }

    /**
     * 获得瓦片位置
     * 
     * @return
     // */
    fun getOffset(): Vector2f {
        return offset
    }
}
