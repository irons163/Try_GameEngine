package com.example.try_gameengine.action;

import javax.xml.datatype.Duration;

import com.example.try_gameengine.framework.Config;
import com.example.try_gameengine.framework.Sprite;

public class MovementActionInfo {
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
	
	public MovementActionInfo(long total, long delay, float dx, float dy){
		this(total, delay, dx, dy, null);
	}
	
	public MovementActionInfo(long total, long delay, float dx, float dy, String description){
		this(total, delay, dx, dy, description, null);
	}
	
	public MovementActionInfo(long total, long delay, float dx, float dy, String description, IRotationController rotationController){
		this(total, delay, dx, dy, description, rotationController, false);
	}
	
	public MovementActionInfo(long total, long delay, float dx, float dy, String description, boolean enableGravity){
		this(total, delay, dx, dy, description, null, enableGravity);
	}
	
	public MovementActionInfo(long total, long delay, float dx, float dy, String description, IRotationController rotationController, boolean enableGravity){
		this.total = total;
		this.delay = delay;
		this.dx = dx;
		this.dy = dy;
		this.description = description;
		this.rotationController = rotationController;
		this.enableGravity = enableGravity;
		if(enableGravity)
			this.gravityController = new GravityController();
	}
	
	public MovementActionInfo(long total, long delay, float dx, float dy, String description, IRotationController rotationController, boolean enableGravity, Sprite sprite, String spriteActionName){
		this.total = total;
		this.delay = delay;
		this.dx = dx;
		this.dy = dy;
		this.description = description;
		this.rotationController = rotationController;
		this.enableGravity = enableGravity;
		if(enableGravity)
			this.gravityController = new GravityController();
		this.sprite = sprite;
		this.spriteActionName = spriteActionName;
	}
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getDelay() {
		return delay;
	}
	public void setDelay(long delay) {
		this.delay = delay;
	}
	public float getDx() {
		return dx;
	}
	public void setDx(float dx) {
		this.dx = dx;
	}
	public float getDy() {
		return dy;
	}
	public void setDy(float dy) {
		this.dy = dy;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}	
	public IRotationController getRotationController() {
		return rotationController;
	}
	public void setRotationController(IRotationController rotationController) {
		this.rotationController = rotationController;
	}
	public IGravityController getGravityController() {
		return gravityController;
	}	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public String getSpriteActionName() {
		return spriteActionName;
	}

	public void setSpriteActionName(String spriteActionName) {
		this.spriteActionName = spriteActionName;
	}

	public boolean isEnableGravity(){
		return enableGravity;
	}
	public void isEnableGravity(boolean enableGravity){
		if(enableGravity){
			if(!this.enableGravity){
				this.enableGravity = enableGravity;
				gravityController = new GravityController();
			}
		}else{
			this.enableGravity = enableGravity;
			gravityController = null;
		}
	}
	
	public boolean isLoop() {
		return isLoop;
	}

	public void isLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}
	
	public void setTargetXY(float targetX, float targetY){
		this.targetX = targetX;
		this.targetY = targetY;
		isSettingTargetXY = true;
	}

	public void modifyInfoWithSpriteXY(float spriteX, float spriteY) {
		if(isSettingTargetXY){
			float distanceX = targetX - spriteX;
			float distanceY = targetY - spriteY;
			float fps = Config.fps;
			float perFrame = 1000.0f/total/fps;
			float perMoveX = distanceX * perFrame;
			float perMoveY = distanceY * perFrame;
			delay = (long)(perFrame*1000);
			dx = perMoveX;
			dy = perMoveY;
		}
	}
	
	@Override
	public boolean equals(Object obj) {  
        if (obj == null) return false;  
        if (!(obj instanceof MovementActionInfo)) return false;
        MovementActionInfo info = (MovementActionInfo) obj;
        return (this.total == info.getTotal() && this.delay == info.getDelay() && this.dx == info.getDx() && this.dy == info.getDy());  
    }
	
	public MovementActionItem clone(){
		MovementActionInfo info = new MovementActionInfo(total, delay, dx, dy, description, rotationController, enableGravity, sprite, spriteActionName);
		return new MovementActionItem(info);
	}
}
