package com.example.try_gameengine.framework

import android.graphics.Canvas
import android.graphics.Paint

interface IDrawEvent {
    fun drawChessboardLines(canvas: Canvas, paint: Paint)
    fun drawAllExistPoints(canvas: Canvas)
    fun drawPlayerPocessableMovePoints(canvas: Canvas)
}
