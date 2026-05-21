package com.example.try_gameengine.framework

import android.graphics.Bitmap

class ChessPoint : IChessPoint() {
    var chessPointBimap: Bitmap? = null

    override fun getChessPointBitmap(): Bitmap? {
        // TODO Auto-generated method stub
        return chessPointBimap
    }

    override fun setChessPointBitmap(chessPointBimap: Bitmap?) {
        // TODO Auto-generated method stub
        this.chessPointBimap = chessPointBimap
    }
}
