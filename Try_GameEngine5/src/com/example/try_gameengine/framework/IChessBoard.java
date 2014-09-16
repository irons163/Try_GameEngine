package com.example.try_gameengine.framework;

import java.util.List;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

public interface IChessBoard extends IDrawEvent{
	public void createLines();
	public void createPoints();
	public Point newPoint(Float x, Float y);
	public int[][] getAllExistPoints();
	public void setAllExistPoints(int[][] allExistPoints);
	public int getLineDistance();
	public void setPlayersBySquential(List<IChessPlayer> playersBySquential);
	public PointF getScreenXYByChessPoint(Point p);
}
