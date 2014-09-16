package com.example.try_gameengine.framework;

import java.util.List;

import android.graphics.Point;

public abstract class BasePlayer implements IPlayer{
//	private IWinLoseLogic winLoseLogic;

	public abstract boolean run();

	@Override
	public void setThinkingTime() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getThinkingTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCurrentThinkingTime() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCurrentThinkingTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCurrentMove() {
		// TODO Auto-generated method stub

	}


//	@Override
//	public boolean isSuccessArrival() {
//		// TODO Auto-generated method stub
//		winLoseLogic.isSuccessArrival(this);
//		return false;
//	}

	int count = 0;
	
//	public void doAutoPlay(PlayerManager playerManager, Logic logic, List<Point> allFreePoints) {
//
//
//	}
//	
//	public void doAutoPlay2(PlayerManager playerManager, Logic logic, List<Point> allFreePoints) {
//
//
//	}
}
