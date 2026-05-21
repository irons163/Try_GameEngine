package com.example.try_gameengine.framework

abstract class BasePlayer : IPlayer {
    //	private IWinLoseLogic winLoseLogic;
    abstract fun run(): Boolean

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
    var count: Int =
        0 //	public void doAutoPlay(PLayerManager.getInstance() pLayerManager.getInstance(), Logic logic, List<Point> allFreePoints) {
    //
    //
    //	}
    //	
    //	public void doAutoPlay2(PLayerManager.getInstance() pLayerManager.getInstance(), Logic logic, List<Point> allFreePoints) {
    //
    //
    //	}
}
