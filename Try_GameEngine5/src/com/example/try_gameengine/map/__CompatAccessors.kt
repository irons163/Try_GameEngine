@file:Suppress("unused", "FunctionName")
package com.example.try_gameengine.map

import android.graphics.Bitmap
import com.badlogic.gdx.math.Vector2f
import com.example.try_gameengine.avg.Resources
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.LinkedList
import kotlin.math.abs

internal fun AStarFinder.getEndX() = this.endX
internal fun AStarFinder.getEndY() = this.endY
internal fun AStarFinder.getStartX() = this.startX
internal fun AStarFinder.getStartY() = this.startY
internal fun AStarFinder.isFlying() = this.isFlying
internal fun Field2D.getHeight() = this.height
internal fun Field2D.getMap() = this.map
internal fun Field2D.getTileHeight() = this.tileHeight
internal fun Field2D.getTileWidth() = this.tileWidth
internal fun Field2D.getWidth() = this.width
internal fun Field2D.setMap(value: Array<IntArray?>) { this.map = value }
internal fun TileMapConfig.getBackMap() = this.backMap
internal fun TileMapConfig.setBackMap(value: Array<IntArray?>?) { this.backMap = value }
