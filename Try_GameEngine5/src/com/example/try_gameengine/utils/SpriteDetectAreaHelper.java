package com.example.try_gameengine.utils;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * This Helper help to create DetectArea.
 * 
 * @author irons
 * 
 */
public class SpriteDetectAreaHelper {

	/**
	 * create a DetectAreaPoint by point.
	 * @param point 
	 * 			for detected.
	 * @return DetectArea
	 */
	public static DetectArea createDetectAreaPoint(PointF point) {
		return new DetectAreaPoint(point);
	}

	/**
	 * create a DetectAreaRoundt by center and radius.
	 * @param center
	 * 			the center of round for detected.
	 * @param radius
	 * 			the radius of round for detected.
	 * @return DetectArea
	 */
	public static DetectArea createDetectAreaRound(PointF center, float radius) {
		return new DetectAreaRound(center, radius);
	}

	/**
	 * create a DetectAreaRect by rectF.
	 * @param rect
	 * 			for detected.
	 * @return DetectArea
	 */
	public static DetectArea createDetectAreaRect(RectF rect) {
		return new DetectAreaRect(rect);
	}
}
