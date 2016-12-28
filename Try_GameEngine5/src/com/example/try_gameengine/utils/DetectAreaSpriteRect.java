package com.example.try_gameengine.utils;

import com.example.try_gameengine.framework.Sprite;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

/**
 * {@code DetectAreaSpriteRect} is an class for an {@code SpriteRect} to detect collision. It extends {@link DetectArea}.
 * @author irons
 */
public class DetectAreaSpriteRect extends DetectAreaRect{
	private Sprite sprite;
	
	/**
	 * An default {@code SpriteRectListener} to listen frame of Sprite and center of Sprite.  
	 */
	private SpriteRectListener spriteRectListener = new DetectAreaSpriteRect.SpriteRectListener() {
		
		@Override
		public RectF calculateSpriteRect() {
			// TODO Auto-generated method stub
			if(sprite==null)
				return null;
			RectF rectF;
			if(sprite.getLocationInScene()!=null)
				rectF = new RectF(sprite.getLocationInScene().x, sprite.getLocationInScene().y, sprite.getLocationInScene().x + sprite.getWidth(), sprite.getLocationInScene().y + sprite.getHeight());
			else
				rectF = sprite.getFrame();
			return rectF;
		}
		
		@Override
		public PointF calculateSpriteCenter() {
			// TODO Auto-generated method stub;
			if(sprite==null)
				return null;
			PointF pointF;
			if(sprite.getLocationInScene()!=null)
				pointF = new PointF(sprite.getLocationInScene().x + sprite.getWidth()/2, sprite.getLocationInScene().y + sprite.getHeight()/2);
			else
				pointF = new PointF(sprite.getFrame().centerX(), sprite.getFrame().centerY());
			return pointF;
		}
	};
	
	/**
	 * {@code SpriteRectListener} is use to calculate the rect(frame) of sprite and center of sprite. 
	 * @author irons
	 *
	 */
	public interface SpriteRectListener{
		/**
		 * calculate the rect of sprite. 
		 * @return RectF
		 */
		public RectF calculateSpriteRect();
		/**
		 * calculate the center of sprite. 
		 * @return PointF
		 */
		public PointF calculateSpriteCenter();
	}
	
	/**
	 * constructor
	 * @param sprite
	 */
	public DetectAreaSpriteRect(Sprite sprite){
		super(new RectF());
		this.sprite = sprite;
	}
	
	/**
	 * constructor
	 * @param rectF
	 * @param spriteRectListener
	 */
	public DetectAreaSpriteRect(RectF rectF, SpriteRectListener spriteRectListener){
		super(rectF);
		this.spriteRectListener = spriteRectListener;
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
			super.setRectF(spriteRectListener.calculateSpriteRect());
		if(getRectF()!=null && spriteRectListener!=null)
			this.center = spriteRectListener.calculateSpriteCenter();
	}
	
	@Override
	public void setCenter(PointF center) {
		// TODO Auto-generated method stub
//		rectF.offset(center.x - rectF.centerX(), center.y - rectF.centerY()); //this use rectF.center to calculate.
//		this.center = center;
		setRectF(getRectF());
		if(spriteRectListener!=null)
			this.center = spriteRectListener.calculateSpriteCenter();
	}
}
