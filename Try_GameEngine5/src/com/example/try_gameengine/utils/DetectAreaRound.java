package com.example.try_gameengine.utils;

import android.graphics.PointF;
import android.util.Log;

/**
 * {@code DetectAreaRound} is an class for an round to detect collision. It extends {@link DetectArea}.
 * @author irons
 *
 */
public class DetectAreaRound extends DetectArea{
	private float radius;
	
	/**
	 * Constructor of DetectAreaRound.
	 * @param center
	 * 			center of the round.
	 * @param radius
	 * 			radius of the round.
	 */
	public DetectAreaRound(PointF center, float radius){
		super(DetectAreaType.ROUND);
		this.center = center;
		this.radius = radius;
	}

	@Override
	public boolean detect(IDetectAreaRequest request) {
		// TODO Auto-generated method stub
		boolean isDetected = detectConditionWithTwoArea(this, request.getDetectArea());
		if(isDetected){
			Log.e("Round", "detected!");
			if(this.spriteDetectAreaListener!=null)
				this.spriteDetectAreaListener.didDetected(this, request);
		}else{
			if(successor!=null){
				if(this.spriteDetectAreaListener==null || !this.spriteDetectAreaListener.stopDoSuccessorDetected(this, request, isDetected))
					return this.successor.detect(request);
			}
		}
		return isDetected;
	}

	
	/**
	 * get radius of this DetectAreaRect.
	 * @return radius.
	 */
	public float getRadius(){
		return radius;
	}
	
	/**
	 * set radius to this DetectAreaRect.
	 * @param radius
	 */
	public void setRadius(float radius){
		this.radius = radius;
	}
}
