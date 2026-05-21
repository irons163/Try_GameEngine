package com.example.try_gameengine.framework

import android.graphics.Point
import android.os.Handler
import android.os.Message
import android.view.MotionEvent

class PlayerManager(
    jumpChessBoard: IChessBoard,
    chessPointManager: IChessPointManager?
) : IPlayerManager {
    private val clickPoint: Point? = null
    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            detectSomeOneWinAndToNextPlayerTurn()
        }
    }
    private val isPlaying = false
    private val isSomeOneSuccessArrival = false
    val isSomeOneWin: Boolean = false
    private val jumpChessBoard: IChessBoard
    private var logic: Logic? = null
    private var playersBySquential: MutableList<IPlayer?>
    private var whoPlay: IPlayer? = null
    private var whoRun = 1

    private val chessPointManager: IChessPointManager?
    private val playerFactory: IPlayerFactory?
    private val winLoseLogic: IWinLoseLogic?

    private fun AiPlayerProcess(player: IPlayer?, paramList: MutableList<Point?>?) {
        Thread(object : Runnable {
            override fun run() {
            }
        }).start()
    }

    private fun decideNextPlayer() {
        this.whoRun = (1 + this.whoRun)
        if (this.whoRun != this.playersBySquential.size) return
        this.whoRun = 0
    }

    private fun detectSomeOneWinAndToNextPlayerTurn() {
        decideNextPlayer()
    }

    private fun isAiPlayerRun(paramIPlayer: IPlayer?): Boolean {
        if (paramIPlayer is AiPlayer) return true
        else return false
    }

    private fun isClickonPointGroup(
        paramMotionEvent: MotionEvent,
        player: IPlayer?
    ): Boolean {
        var a = false

        val newPoint = jumpChessBoard.newPoint(
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

    init {
        this.jumpChessBoard = jumpChessBoard
        this.chessPointManager = chessPointManager

        winLoseLogic = NormalWinLoseLogic(
            jumpChessBoard.getAllExistPoints()
        )

        playerFactory = PlayerFactory(chessPointManager)

        playersBySquential = ArrayList<IPlayer?>()
    }

    override fun onTouchEvent(paramMotionEvent: MotionEvent?) {
        if (paramMotionEvent?.getAction() == MotionEvent.ACTION_DOWN) {
            if (isPlayerCanRun()) {
                this.whoPlay = getCurrentPlayer()
                isClickonPointGroup(paramMotionEvent, this.whoPlay)
                // if(winLoseLogic.isWin(clickPoint)){
            } else if (isPlayerProcessing()) {
                if (isClickonPointGroup(paramMotionEvent, this.whoPlay)) {
                    whoRun = -1
                } else playerRun(paramMotionEvent, this.whoPlay, this.clickPoint)
            }
        }
    }

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
