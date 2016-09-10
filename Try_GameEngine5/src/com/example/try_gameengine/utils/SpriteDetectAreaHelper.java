package com.example.try_gameengine.utils;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * @author irons
 *
 */
public class SpriteDetectAreaHelper {	
	
	public static DetectArea createDetectAreaPoint(PointF point){
		return  new DetectAreaPoint(point);
	}
	public static DetectArea createDetectAreaRound(PointF center, float radius){
		return  new DetectAreaRound(center, radius);
	}
	public static DetectArea createDetectAreaRect(RectF rect){
		return  new DetectAreaRect(rect);
	}
}


