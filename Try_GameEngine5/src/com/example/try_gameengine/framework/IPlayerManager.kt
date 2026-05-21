package com.example.try_gameengine.framework

import android.view.MotionEvent

interface IPlayerManager {
    //	void setBoard(IChessBoard jumpChessBoard);
    fun getNextPlayer(): IPlayer?

    fun getBefforePlayer(): IPlayer?
    fun getCurrentPlayer(): IPlayer?
    fun toNextPlayer()
    fun toBefforePlayer()
    fun isAllPlayersDone(): Boolean
    fun isPlayerCanRun(): Boolean
    fun isPlayerProcessing(): Boolean
    fun setOnProcessing()
    fun onTouchEvent(event: MotionEvent?)

    //	void setLogic(Logic logic);
    fun getPlayersBySquential(): MutableList<IPlayer?>?
    fun setPlayersBySquential(playersBySquential: MutableList<IPlayer?>?)
}
