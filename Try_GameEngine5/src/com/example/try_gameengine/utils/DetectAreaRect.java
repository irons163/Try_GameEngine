package com.example.try_gameengine.utils;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

public class DetectAreaRect extends DetectArea{
	private RectF rectF;
	
	public DetectAreaRect(RectF rectF){
		super(DetectAreaType.RECT);
		setRectF(rectF);
	}
	
	@Override
	public boolean detect(IDetectAreaRequest request){
		boolean isDetected = detectConditionWithTwoArea(this, request.getDetectArea());
		if(isDetected){
			Log.e("RectF", "detected!");
			if(this.spriteDetectAreaListener!=null)
				this.spriteDetectAreaListener.didDetected(this, request);
		}else{
			if(successor!=null){
				return this.successor.detect(request);
			}
		}
		return isDetected;
	}	
	
	public RectF getRectF(){
		return rectF;
	}
	
	public void setRectF(RectF rectF){
		this.rectF = rectF;
		if(rectF!=null)
			this.center = new PointF(rectF.centerX(), rectF.centerY());
	}
	
	@Override
	public void setCenter(PointF center) {
		// TODO Auto-generated method stub
		rectF.offset(center.x - this.center.x, center.y - this.center.y);
		this.center = center;
	}
}
