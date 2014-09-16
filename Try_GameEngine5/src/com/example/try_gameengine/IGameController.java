package com.example.try_gameengine;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface IGameController {
//	public void moveChess(ChessPoint chessPoint);
	public void start();
	public void stop();
	public void showWin();
	public void showLose();
	public void onTouchEvent(MotionEvent event);
}
