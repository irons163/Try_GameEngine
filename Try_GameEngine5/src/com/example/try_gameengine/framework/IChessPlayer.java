package com.example.try_gameengine.framework;

import java.util.List;

import android.graphics.Point;

public interface IChessPlayer extends IPlayer{
	public IChessPoint getChessPoint();
	public IChessPoint getPocessableMvoeChessPoint();
	boolean run(Point point, Point clickPoint, List<Point> allFreePoints);
}
