package com.example.try_gameengine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public interface IGameModel {
//	public void moveChess(ChessPoint chessPoint);
//	public void setChessBorad(IChessBoard chessBoard);
//	public IChessBoard getChessBorad();
	public int[][] getAllExistPoints();
	public void registerObserver(IMoveObserver moveObserver);
	public void removeObserver(IMoveObserver moveObserver);
	public void onTouchEvent(MotionEvent event);
	public void drawEnemis(Canvas canvas);
	public void drawCrosshair(Canvas canvas);
	public void process();
}
