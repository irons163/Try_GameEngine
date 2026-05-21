package com.example.try_gameengine.viewport

import android.graphics.Point
import android.graphics.PointF

object MyMap {
    private var totalX = 1f
    private var totalY = 1f
    private var displayX = 1f
    private var displayY = 1f

    fun setTotalXY(totalX: Float, totalY: Float) {
        MyMap.totalX = totalX
        MyMap.totalY = totalY
    }

    fun setDisplayXY(displayX: Float, displayY: Float) {
        MyMap.displayX = displayX
        MyMap.displayY = displayY
    }

    fun setImageXYByOldXY(oldX: Int, oldY: Int): PointF {
        val newX = (oldX / totalX) * displayX
        val newY = (oldY / totalY) * displayY

        return PointF(newX, newY)
    }

    fun getWH(oldW: Int, oldH: Int): Point {
        val point =
            Point(((oldW / totalX) * displayX).toInt(), ((oldH / totalY) * displayY).toInt())
        return point
    }

    fun setInfo(orangeW: Int, orangeH: Int, displayW: Int, displayH: Int) {
        totalX = orangeW.toFloat()
        totalY = orangeH.toFloat()
        displayX = displayW.toFloat()
        displayY = displayH.toFloat()
    }
}
