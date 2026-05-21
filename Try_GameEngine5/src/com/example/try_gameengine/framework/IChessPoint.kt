package com.example.try_gameengine.framework

import android.graphics.Bitmap
import android.graphics.Point

abstract class IChessPoint : Point() {
    abstract fun getChessPointBitmap(): Bitmap?

    abstract fun setChessPointBitmap(chessPointBimap: Bitmap?)
}
