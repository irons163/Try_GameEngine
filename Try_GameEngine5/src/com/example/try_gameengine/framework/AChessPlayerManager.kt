package com.example.try_gameengine.framework

import android.graphics.Point
import android.os.Handler
import android.os.Message
import android.view.MotionEvent

abstract class AChessPlayerManager(
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
    private val isSomeOneWin = false
    protected var jumpChessBoard: IChessBoard
    @JvmField
    protected var logic: Logic? = null
    @JvmField
    protected var playersBySquential: MutableList<IPlayer?>
    protected var whoPlay: IPlayer? = null
    @JvmField
    protected var whoRun: Int = 0

    //	private IChessPointManager chessPointManager;
    protected var playerFactory: IPlayerFactory? = null

    protected abstract fun initPlayerFactory(chessPointManager: IChessPointManager?)

    protected abstract fun initPlayerFactoryCreate(playersBySquential: MutableList<IPlayer?>?)

    protected abstract fun initChecssPointManagerCreate()

    //	protected void AiPlayerProcess(final IPlayer player, List<Point> paramList) {
    //		new Thread(new Runnable() {
    //
    //			@Override
    //			public void run() {
    //				doAiProcess();
    //			}
    //		}).start();
    //	}
    protected fun AiPlayerProcess(player: IPlayer?, board: Array<IntArray?>?) {
        Thread(object : Runnable {
            override fun run() {
                doAiProcess(player, board)
            }
        }).start()
    }

    protected abstract fun doAiProcess(player: IPlayer?, board: Array<IntArray?>?)

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

    fun getWinner(): MutableList<IPlayer?> {
        val winnerArrayList: MutableList<IPlayer?> = ArrayList<IPlayer?>()
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

    fun isSomeOneWin(): Boolean {
        return this.isSomeOneWin
    }

    var isCanPutChessPoint: Boolean = true

    init {
        this.jumpChessBoard = jumpChessBoard

        //		this.chessPointManager = chessPointManager;

//		winLoseLogic = new NormalWinLoseLogic(
//				jumpChessBoard.getAllExistPoints());
        playersBySquential = ArrayList<IPlayer?>()

        initPlayerFactory(chessPointManager)
        initPlayerFactoryCreate(playersBySquential)


//		playersBySquential.add(playerFactory.createHumanPlayerWithRed());
//		playersBySquential.add(playerFactory.createHumanPlayerWithYellow());
        initChecssPointManagerCreate()
        //		chessPointManager.createChessPointWhite();
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


//			if(isClick(paramMotionEvent, this.whoPlay)){
//				if(winLoseLogic.isWin(clickPoint)){
//					whoRun = -1;
//				}else
//					playerRun(paramMotionEvent, this.whoPlay, this.clickPoint);
//			}
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

    fun getWhoRun(): Int {
        return whoRun
    }
}
