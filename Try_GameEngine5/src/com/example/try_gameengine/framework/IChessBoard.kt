package com.example.try_gameengine.framework

import android.graphics.Point
import android.graphics.PointF

interface IChessBoard : IDrawEvent {
    fun createLines()
    fun createPoints()
    fun newPoint(x: Float, y: Float): Point
    fun getAllExistPoints(): Array<IntArray?>
    fun setAllExistPoints(allExistPoints: Array<IntArray?>)
    fun getLineDistance(): Int
    fun setPlayersBySquential(playersBySquential: MutableList<IChessPlayer?>?)
    fun getScreenXYByChessPoint(p: Point): PointF
}
