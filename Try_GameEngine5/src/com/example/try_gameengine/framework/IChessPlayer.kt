package com.example.try_gameengine.framework

import android.graphics.Point

interface IChessPlayer : IPlayer {
    fun getChessPoint(): IChessPoint?
    fun getPocessableMvoeChessPoint(): IChessPoint?
    fun run(point: Point?, clickPoint: Point?, allFreePoints: MutableList<Point?>?): Boolean
}
