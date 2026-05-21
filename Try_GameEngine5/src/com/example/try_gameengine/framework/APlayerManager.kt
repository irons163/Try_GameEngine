package com.example.try_gameengine.framework

import android.graphics.Point
import android.os.Handler
import android.os.Message
import android.view.MotionEvent

/**
 * APLayerManager.getInstance()
 * @author irons
 // */
abstract class APlayerManager : IPlayerManager {
    private val clickPoint: Point? = null
    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            detectSomeOneWinAndToNextPlayerTurn()
        }
    }

    val isSomeOneWin: Boolean = false
    private val jumpChessBoard: IChessBoard? = null
    private var logic: Logic? = null
    @JvmField
    protected var playersBySquential: MutableList<IPlayer?>
    protected var whoPlay: IPlayer? = null
    protected var whoRun: Int = 1
    private val chessPointManager: IChessPointManager? = null
    protected var playerFactory: IPlayerFactory? = null

    /**
     * 
     // */
    protected fun initPlayerFactory() {
        playerFactory = PlayerFactory(chessPointManager)
    }

    /**
     * @param player
     * @param paramList
     // */
    private fun AiPlayerProcess(player: IPlayer?, paramList: MutableList<Point?>?) {
        Thread(object : Runnable {
            override fun run() {
            }
        }).start()
    }

    /**
     * 
     // */
    private fun decideNextPlayer() {
        this.whoRun = (1 + this.whoRun)
        if (this.whoRun != this.playersBySquential.size) return
        this.whoRun = 0
    }

    /**
     * 
     // */
    private fun detectSomeOneWinAndToNextPlayerTurn() {
        decideNextPlayer()
    }

    /**
     * @param paramIPlayer
     * @return
     // */
    private fun isAiPlayerRun(paramIPlayer: IPlayer?): Boolean {
        if (paramIPlayer is AiPlayer) return true
        else return false
    }

    /**
     * 
     * @param paramMotionEvent
     * @param player
     * @return
     // */
    private fun isClickonPointGroup(
        paramMotionEvent: MotionEvent,
        player: IPlayer?
    ): Boolean {
        var a = false

        val newPoint = jumpChessBoard!!.newPoint(
            paramMotionEvent.getX(),
            paramMotionEvent.getY()
        )

        if ((newPoint.x >= 0 && newPoint.x < jumpChessBoard.getAllExistPoints().size)
            && (newPoint.y >= 0 && newPoint.y < jumpChessBoard
                .getAllExistPoints()!![0]!!.size)
        ) {
            val playerIndex = playersBySquential.indexOf(player) + 1

            if (jumpChessBoard.getAllExistPoints()!![newPoint.x]!![newPoint.y] == playerIndex) {
                a = true
                Logic.Companion.jumps.clear()
                if (playerIndex == 1) {
                    logic!!.startToDetectedTopToDown(newPoint.x, newPoint.y, playerIndex)
                } else {
                    logic!!.startToDetectedDownToTop(newPoint.x, newPoint.y, playerIndex)
                }
            }
        }
        return a
    }

    private fun playerRun(
        paramMotionEvent: MotionEvent?, paramIPlayer: IPlayer?,
        paramPoint: Point?
    ) {
        this.whoRun = this.playersBySquential.indexOf(paramIPlayer)
        detectSomeOneWinAndToNextPlayerTurn()
    }

    private fun setFirstPlayer() {
        this.whoRun = 0
    }

    override fun getBefforePlayer(): IPlayer? {
        return null
    }

    override fun getCurrentPlayer(): IPlayer? {
        return this.playersBySquential.get(this.whoRun)
    }

    override fun getNextPlayer(): IPlayer? {
        return null
    }

    val winner: MutableList<IPlayer?>
        get() {
            val winnerArrayList: MutableList<IPlayer?> =
                ArrayList<IPlayer?>()
            for (player in playersBySquential) {
                winnerArrayList.add(player)
            }
            return winnerArrayList
        }

    override fun isAllPlayersDone(): Boolean {
        return false
    }

    override fun isPlayerCanRun(): Boolean {
        if (this.whoRun >= 0) return true
        else return false
    }

    override fun isPlayerProcessing(): Boolean {
        if (this.whoRun == -1) return true
        else return false
    }

    var isCanPutChessPoint: Boolean = true

    /**
     * 
     // */
    init {
        playersBySquential = ArrayList<IPlayer?>()
        initPlayerFactory()
    }

    override fun onTouchEvent(paramMotionEvent: MotionEvent?) {
        if (paramMotionEvent?.getAction() == MotionEvent.ACTION_DOWN) {
            touchPerform(paramMotionEvent)
        }
    }

    protected fun touchPerform(paramMotionEvent: MotionEvent?) {
        if (isPlayerCanRun()) {
            this.whoPlay = getCurrentPlayer()
            //			isClickonPointGroup(paramMotionEvent, this.whoPlay);
            isClick(paramMotionEvent, this.whoPlay)
            // if(winLoseLogic.isWin(clickPoint)){
        } else if (isPlayerProcessing()) {
//			if (isClickonPointGroup(paramMotionEvent, this.whoPlay)) {
            if (isClick(paramMotionEvent, this.whoPlay)) {
//				isClick(paramMotionEvent, this.whoPlay);
                whoRun = -1
            } else playerRun(paramMotionEvent, this.whoPlay, this.clickPoint)
        }
    }

    protected abstract fun isClick(
        paramMotionEvent: MotionEvent?,
        player: IPlayer?
    ): Boolean

    //	public void setBoard(IChessBoard paramJumpChessBoard) {
    //		this.jumpChessBoard = paramJumpChessBoard;
    //	}
    fun setLogic(paramLogic: Logic) {
        this.logic = paramLogic
    }

    override fun setOnProcessing() {
        this.whoRun = -1
    }

    override fun setPlayersBySquential(paramList: MutableList<IPlayer?>?) {
        paramList ?: return
        this.playersBySquential = paramList
        setFirstPlayer()
    }

    override fun getPlayersBySquential(): MutableList<IPlayer?> {
        return playersBySquential
    }

    fun startPlayByFirstPlayer() {
    }

    override fun toBefforePlayer() {
    }

    override fun toNextPlayer() {
    }
}
