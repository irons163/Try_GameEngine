package com.example.try_gameengine.framework

import android.graphics.Point

class ChessBasePlayer(chessPoint: IChessPoint?, pocessableMvoeChessPoint: IChessPoint?) : IPlayer,
    IChessPlayer {
    private val chessPoint: IChessPoint?
    private val pocessableMvoeChessPoint: IChessPoint?

    override fun run(
        point: Point?,
        clickPoint: Point?,
        allFreePoints: MutableList<Point?>?
    ): Boolean {
        // TODO Auto-generated method stub
        return player1Run(point, clickPoint, allFreePoints)
    }

    private fun player1Run(
        point: Point?, clickPoint: Point?,
        allFreePoints: MutableList<Point?>?
    ): Boolean {
        val isFinishMove = false

        //		if (Logic.jumps.contains(point)) {
//			movePoint(point, clickPoint, allFreePoints);
//			isFinishMove = true;
//		} else {
//			Logic.jumps.clear();
//		}
        return isFinishMove
    }

    private fun movePoint(
        point: Point?, clickPoint: Point?,
        allFreePoints: MutableList<Point?>?
    ) {
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


    //	@Override
    //	public boolean isSuccessArrival() {
    //		// TODO Auto-generated method stub
    //		winLoseLogic.isSuccessArrival(this);
    //		return false;
    //	}
    var count: Int = 0

    //	private IWinLoseLogic winLoseLogic;
    init {
        this.chessPoint = chessPoint
        this.pocessableMvoeChessPoint = pocessableMvoeChessPoint
        //		this.winLoseLogic = new NormalWinLoseLogic();
        // IStrategy strategy = new Strategy();
        // IBestMoveStrategy bestMoveStrategy = new BestOnceMoveStrategy(logic,
        // clickPointX, clickPointY);
    }

    //	public void doAutoPlay(PLayerManager.getInstance() pLayerManager.getInstance(), Logic logic, List<Point> allFreePoints) {
    //
    //
    //	}
    //	
    //	public void doAutoPlay2(PLayerManager.getInstance() pLayerManager.getInstance(), Logic logic, List<Point> allFreePoints) {
    //
    //
    //	}
    override fun getChessPoint(): IChessPoint? {
        // TODO Auto-generated method stub
        return chessPoint
    }

    override fun getPocessableMvoeChessPoint(): IChessPoint? {
        // TODO Auto-generated method stub
        return null
    }
}
