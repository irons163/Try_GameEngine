package com.example.try_gameengine.framework

import android.graphics.Point

interface IWinLoseLogic {
    fun isWin(p: Point): Boolean
    fun rank()
    fun countScore()
}
