package com.example.try_gameengine.framework;

import android.graphics.Point;

public interface IWinLoseLogic {
	public boolean isWin(Point p);
	public void rank();
	public void countScore();
}
