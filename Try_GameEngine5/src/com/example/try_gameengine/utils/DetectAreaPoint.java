package com.example.try_gameengine.utils;

import android.graphics.PointF;
import android.util.Log;

public class DetectAreaPoint extends DetectArea{
//	private PointF point;
	
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
				return this.successor.detect(request);
			}
		}
		return isDetected;
	}
	
	public PointF getPoint(){
		return center;
	}
}