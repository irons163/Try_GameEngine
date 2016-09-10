package com.example.try_gameengine.utils;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

/**
 * @author irons
 *
 */
public class DetectAreaRect extends DetectArea{
	private RectF rectF;
	
	/**
	 * @param rectF
	 */
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
				if(this.spriteDetectAreaListener==null || !this.spriteDetectAreaListener.stopDoSuccessorDetected(this, request, isDetected))
					return this.successor.detect(request);
			}
		}
		return isDetected;
	}	
	
	/**
	 * @return
	 */
	public RectF getRectF(){
		return rectF;
	}
	
	/**
	 * @param rectF
	 */
	public void setRectF(RectF rectF){
		this.rectF = rectF;
		if(rectF!=null)
			this.center = new PointF(rectF.centerX(), rectF.centerY());
	}
	
	@Override
	public void setCenter(PointF center) {
//		rectF.offset(center.x - this.center.x, center.y - this.center.y); //this use point center to calculate, but sometimes the rectF is updated, center point not.
		rectF.offset(center.x - rectF.centerX(), center.y - rectF.centerY()); //this use rectF.center to calculate.
		this.center = center;
	}
}
