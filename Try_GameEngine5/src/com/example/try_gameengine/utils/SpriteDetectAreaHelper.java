package com.example.try_gameengine.utils;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

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

enum DetectAreaType{
	POINT, ROUND, RECT
}

//abstract class DetectAreaRequest{
//	
//}

//class DetectAreaRequestPoint{
//	private Point point;
//	
//	public DetectAreaRequestPoint(Point request){
//		this.point = request;
//	}
//}










//class DetectAreaDetecor{
//	
//	public DetectAreaDetecor(DetectAreaType detectAreaType, DetectAreaType detectAreaType2) {
//		
//		switch (key) {
//		case value:
//			
//			break;
//
//		default:
//			break;
//		}
//	}
//}