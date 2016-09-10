package com.example.try_gameengine.utils;

import android.graphics.PointF;
import android.util.Log;

/**
 * 
 * @author irons
 *
 */
public class DetectAreaPoint extends DetectArea{
	
	public DetectAreaPoint(PointF point){
		super(DetectAreaType.POINT);
		this.center = point;
	}

	@Override
	public boolean detect(IDetectAreaRequest request) {
		// TODO Auto-generated method stub
		boolean isDetected = detectConditionWithTwoArea(this, request.getDetectArea());
		if(isDetected){
			Log.e("Point", "detected!");
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
	 * @return
	 */
	public PointF getPoint(){
		return center;
	}
}
