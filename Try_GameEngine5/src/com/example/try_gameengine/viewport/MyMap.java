package com.example.try_gameengine.viewport;

import android.graphics.Point;
import android.graphics.PointF;
import android.widget.ImageView;

import com.example.try_gameengine.framework.CommonUtil;

public class MyMap {
	
	private static float totalX = 1;
	private static float totalY = 1;
	private static float displayX = 1;
	private static float displayY = 1;
	
	public static void setTotalXY(float totalX, float totalY){
		MyMap.totalX = totalX;
		MyMap.totalY = totalY;
	}
	
	public static void setDisplayXY(float displayX, float displayY){
		MyMap.displayX = displayX;
		MyMap.displayY = displayY;
	}
	
	public static PointF setImageXYByOldXY(int oldX, int oldY) {
		float newX = (float) (oldX / totalX) * displayX;
		float newY = (float) (oldY / totalY) * displayY;

		return new PointF(newX, newY);
	}
	
	public static Point getWH(int oldW, int oldH){
		Point point = new Point((int) ((oldW / totalX) * displayX), (int) ((oldH / totalY) * displayY));
		return point;
	}
	
	public static void setInfo(int orangeW, int orangeH, int displayW, int displayH){
		totalX = orangeW;
		totalY = orangeH;
		displayX = displayW;
		displayY = displayH;
	} 
}
