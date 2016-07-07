package com.example.try_gameengine.utils;

import com.example.try_gameengine.framework.Sprite;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

public class DetectAreaSpriteRect extends DetectAreaRect{
//	private RectF rectF;
	private Sprite sprite;
	
	private SpriteRectListener spriteRectListener = new DetectAreaSpriteRect.SpriteRectListener() {
		
		@Override
		public RectF caculateSpriteRect() {
			// TODO Auto-generated method stub
			if(sprite==null)
				return null;
			RectF rectF;
			if(sprite.getLocationInScene()!=null)
				rectF = new RectF(sprite.getLocationInScene().x, sprite.getLocationInScene().y, sprite.getLocationInScene().x + sprite.w, sprite.getLocationInScene().y + sprite.h);
			else
				rectF = sprite.getFrame();
			return rectF;
		}
		
		@Override
		public PointF caculateSpriteCenter() {
			// TODO Auto-generated method stub;
			if(sprite==null)
				return null;
			PointF pointF;
			if(sprite.getLocationInScene()!=null)
				pointF = new PointF(sprite.getLocationInScene().x + sprite.w/2, sprite.getLocationInScene().y + sprite.h/2);
			else
				pointF = new PointF(sprite.getFrame().centerX(), sprite.getFrame().centerY());
			return pointF;
		}
	};
	
	public interface SpriteRectListener{
		public RectF caculateSpriteRect();
		public PointF caculateSpriteCenter();
	}
	
	public DetectAreaSpriteRect(Sprite sprite){
		super(new RectF());
		this.sprite = sprite;
//		setRectF(getRectF()); //not good.
	}
	
	public DetectAreaSpriteRect(RectF rectF, SpriteRectListener spriteRectListener){
		super(rectF);
//		setRectF(rectF);
		this.spriteRectListener = spriteRectListener;
//		setRectF(rectF); //not good.
	}
	
	@Override
	public boolean detect(IDetectAreaRequest request){
		boolean isDetected = detectConditionWithTwoArea(this, request.getDetectArea());
		if(isDetected){
			Log.e("Sprite RectF", "detected!");
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
	
	@Override
	public RectF getRectF(){
		return super.getRectF();
	}
	
	@Override
	public void setRectF(RectF rectF){
//		this.rectF = rectF;
		if(spriteRectListener!=null)
			super.setRectF(spriteRectListener.caculateSpriteRect());
		if(getRectF()!=null && spriteRectListener!=null)
			this.center = spriteRectListener.caculateSpriteCenter();
	}
	
	@Override
	public void setCenter(PointF center) {
		// TODO Auto-generated method stub
//		rectF.offset(center.x - rectF.centerX(), center.y - rectF.centerY()); //this use rectF.center to calculate.
//		this.center = center;
		setRectF(getRectF());
		if(spriteRectListener!=null)
			this.center = spriteRectListener.caculateSpriteCenter();
	}
}
