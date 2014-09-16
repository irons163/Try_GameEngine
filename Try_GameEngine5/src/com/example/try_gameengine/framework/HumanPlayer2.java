package com.example.try_gameengine.framework;

import java.util.Iterator;
import java.util.List;

import android.graphics.Point;
import android.view.MotionEvent;

public class HumanPlayer2 implements IPlayer{
//	private IWinLoseLogic winLoseLogic;
	
	public HumanPlayer2(){
//		this.winLoseLogic = new NormalWinLoseLogic();
	}
	
	private boolean player1Run(Point point, Point clickPoint, List<Point> allFreePoints){
		boolean isFinishMove = false;

			isFinishMove = true;
		
		return isFinishMove;
	}

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
	
}
