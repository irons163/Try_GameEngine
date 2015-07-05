package com.example.try_gameengine.action;

import javax.xml.datatype.Duration;

import com.example.try_gameengine.framework.Config;
import com.example.try_gameengine.framework.Sprite;

public class MovementActionFPSInfo extends MovementActionInfo{
	private long total;
	private long delay;
	private float dx, dy;
	private String description;
	private IRotationController rotationController;
	private IGravityController gravityController;
	private boolean enableGravity;
	private Sprite sprite;
	private String spriteActionName;
	private boolean isLoop = false;
	private boolean isSettingTargetXY = false;
	private float targetX, targetY;
	
	public MovementActionFPSInfo(long count, long durationFPSFream, float dx, float dy){
		this(count, durationFPSFream, dx, dy, null);
	}
	
	public MovementActionFPSInfo(long count, long durationFPSFream, float dx, float dy, String description){
		this(count, durationFPSFream, dx, dy, description, null);
	}
	
	public MovementActionFPSInfo(long count, long durationFPSFream, float dx, float dy, String description, IRotationController rotationController){
		this(count, durationFPSFream, dx, dy, description, rotationController, false);
	}
	
	public MovementActionFPSInfo(long count, long durationFPSFream, float dx, float dy, String description, boolean enableGravity){
		this(count, durationFPSFream, dx, dy, description, null, enableGravity);
	}
	
	public MovementActionFPSInfo(long count, long durationFPSFream, float dx, float dy, String description, IRotationController rotationController, boolean enableGravity){
		super(count, durationFPSFream, dx, dy, description, rotationController, enableGravity);
	}
	
	public MovementActionFPSInfo(long count, long durationFPSFream, float dx, float dy, String description, IRotationController rotationController, boolean enableGravity, Sprite sprite, String spriteActionName){
		super(count, durationFPSFream, dx, dy, description, rotationController, enableGravity, sprite, spriteActionName);
	}

	@Override
	public void modifyInfoWithSpriteXY(float spriteX, float spriteY) {
		if(isSettingTargetXY){
			float distanceX = targetX - spriteX;
			float distanceY = targetY - spriteY;
			dx = distanceX / delay;
			float fps = Config.fps;
			float perFrame = 1000.0f/total/fps;
			float perMoveX = distanceX * perFrame;
			float perMoveY = distanceY * perFrame;
			delay = (long)(perFrame*1000);
			dx = perMoveX;
			dy = perMoveY;
			
//			float distanceX = targetX - spriteX;
//			float distanceY = targetY - spriteY;
//			float fps = Config.fps;
//			float perFrame = 1000.0f/total/fps;
//			float perMoveX = perFrame / distanceX;
//			float perMoveY = perFrame / distanceY;
//			total = (long) (total*fps);
//			delay = 1;
//			dx = perMoveX;
//			dy = perMoveY;
		}
	}
	
	@Override
	public boolean equals(Object obj) {  
        if (obj == null) return false;  
        if (!(obj instanceof MovementActionFPSInfo)) return false;
        MovementActionFPSInfo info = (MovementActionFPSInfo) obj;
        return (this.total == info.getTotal() && this.delay == info.getDelay() && this.dx == info.getDx() && this.dy == info.getDy());  
    }
	
	public MovementActionItem clone(){
		MovementActionFPSInfo info = new MovementActionFPSInfo(total, delay, dx, dy, description, rotationController, enableGravity, sprite, spriteActionName);
		return new MovementActionItem(info);
	}
}
