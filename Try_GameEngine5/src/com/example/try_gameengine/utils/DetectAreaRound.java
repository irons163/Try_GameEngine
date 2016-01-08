package com.example.try_gameengine.utils;

import android.graphics.PointF;
import android.util.Log;

public class DetectAreaRound extends DetectArea{
	private float radius;
	
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

	
	public float getRadius(){
		return radius;
	}
}
