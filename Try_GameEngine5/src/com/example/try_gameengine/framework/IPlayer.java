package com.example.try_gameengine.framework;

import java.util.List;

import android.graphics.Point;

public interface IPlayer {
	
//	boolean run(Point point, Point clickPoint, List<Point> allFreePoints);
	void setThinkingTime();
	int getThinkingTime();
	void setCurrentThinkingTime();
	int getCurrentThinkingTime();
	void setCurrentMove();
//	IChessPoint getChessPoint();
//	public IChessPoint getPocessableMvoeChessPoint();
	
//	boolean isSuccessArrival();
//	boolean isAutoPlayable();
//	void doAutoPlay();
}
