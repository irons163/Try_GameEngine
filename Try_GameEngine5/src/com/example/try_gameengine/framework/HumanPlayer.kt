package com.example.try_gameengine.framework

import android.graphics.Point

/**
 * HumanPlayer fot UI.
 * @author irons
 // */
class HumanPlayer(chessPoint: IChessPoint?, pocessableMvoeChessPoint: IChessPoint?) : IPlayer,
    IChessPlayer {
    private val chessPoint: IChessPoint?
    private val pocessableMvoeChessPoint: IChessPoint?

    init {
        this.chessPoint = chessPoint
        this.pocessableMvoeChessPoint = pocessableMvoeChessPoint
    }

    override fun run(
        point: Point?,
        clickPoint: Point?,
        allFreePoints: MutableList<Point?>?
    ): Boolean {
        // TODO Auto-generated method stub
        return player1Run(point, clickPoint, allFreePoints)
    }

    private fun player1Run(
        point: Point?,
        clickPoint: Point?,
        allFreePoints: MutableList<Point?>?
    ): Boolean {
        var isFinishMove = false
        isFinishMove = true
        return isFinishMove
    }

    override fun setThinkingTime() {
        // TODO Auto-generated method stub
    }

    override fun getThinkingTime(): Int {
        // TODO Auto-generated method stub
        return 0
    }

    override fun setCurrentThinkingTime() {
        // TODO Auto-generated method stub
    }

    override fun getCurrentThinkingTime(): Int {
        // TODO Auto-generated method stub
        return 0
    }

    override fun setCurrentMove() {
        // TODO Auto-generated method stub
    }

    override fun getChessPoint(): IChessPoint? {
        // TODO Auto-generated method stub
        return chessPoint
    }

    override fun getPocessableMvoeChessPoint(): IChessPoint? {
        // TODO Auto-generated method stub
        return pocessableMvoeChessPoint
    }
}
