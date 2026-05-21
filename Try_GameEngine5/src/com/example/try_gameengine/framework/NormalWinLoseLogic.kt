package com.example.try_gameengine.framework

import android.graphics.Point

class NormalWinLoseLogic(allExistPoints: Array<IntArray?>) : IWinLoseLogic {
    private val allExistPoints: Array<IntArray?>
    var who: Int = 0

    init {
        this.allExistPoints = allExistPoints
    }

    override fun isWin(p: Point): Boolean {
        var isWin = false
        val x = p.x
        val y = p.y
        who = allExistPoints[x]!![y]

        if (detectLeftAndRight(x, y) ||
            detectLeftTopAndRightBottom(x, y) ||
            detectTopAndBottom(x, y) ||
            detectLeftBottomAndRightTop(x, y)
        ) {
            isWin = true
        }

        return isWin
    }

    private fun detect(x: Int, y: Int): Boolean {
        var isDeteced = false
        if (x >= 0 && x < allExistPoints.size && y >= 0 && y < allExistPoints[x]!!.size) {
            if (allExistPoints[x]!![y] == who) {
                isDeteced = true
            }
        }

        return isDeteced
    }

    private fun detect(x: Int, y: Int, offsetX: Int, offsetY: Int): Int {
        var num = 0
        if (detect(x + offsetX, y + offsetY)) {
            num = detect(x + offsetX, y + offsetY, offsetX, offsetY)
            return ++num
        } else {
            return num
        }
    }

    private fun detectLeftAndRight(x: Int, y: Int): Boolean {
        return isWin(detectLeft(x, y), detectRight(x, y))
    }

    private fun detectLeft(x: Int, y: Int): Int {
        return detect(x, y, -1, 0)
    }

    private fun detectRight(x: Int, y: Int): Int {
        return detect(x, y, 1, 0)
    }

    private fun detectLeftTopAndRightBottom(x: Int, y: Int): Boolean {
        return isWin(detectLeftTop(x, y), detectRightBottom(x, y))
    }

    private fun detectLeftTop(x: Int, y: Int): Int {
        return detect(x, y, -1, -1)
    }

    private fun detectRightBottom(x: Int, y: Int): Int {
        return detect(x, y, 1, 1)
    }

    private fun detectTopAndBottom(x: Int, y: Int): Boolean {
        return isWin(detectTop(x, y), detectBottom(x, y))
    }

    private fun detectTop(x: Int, y: Int): Int {
        return detect(x, y, 0, -1)
    }

    private fun detectBottom(x: Int, y: Int): Int {
        return detect(x, y, 0, 1)
    }

    private fun detectLeftBottomAndRightTop(x: Int, y: Int): Boolean {
        return isWin(detectLeftBottom(x, y), detectRightTop(x, y))
    }

    private fun detectLeftBottom(x: Int, y: Int): Int {
        return detect(x, y, -1, 1)
    }

    private fun detectRightTop(x: Int, y: Int): Int {
        return detect(x, y, 1, -1)
    }

    private fun isWin(oneSideNum: Int, theOtherSideNum: Int): Boolean {
        val totalNum = oneSideNum + theOtherSideNum
        var isWin = false
        if (totalNum >= 3) isWin = true
        return isWin
    }

    override fun rank() {
        // TODO Auto-generated method stub
    }

    override fun countScore() {
        // TODO Auto-generated method stub
    }
}