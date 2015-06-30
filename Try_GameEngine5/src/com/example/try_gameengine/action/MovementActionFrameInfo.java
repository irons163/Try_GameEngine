package com.example.try_gameengine.action;

import com.example.try_gameengine.framework.Sprite;

public class MovementActionFrameInfo extends MovementActionInfo{
	private long total=1;
	private long delay;
	private float dx, dy;
	private String description;
	private IRotationController rotationController;
	private IGravityController gravityController;
	private boolean enableGravity;
	private long[] frameTimes;
	private Sprite sprite;
	private String spriteActionName;
	
	public MovementActionFrameInfo(long[] frameTimes, float dx, float dy){
		this(frameTimes, dx, dy, null);
	}
	
	public MovementActionFrameInfo(long[] frameTimes, float dx, float dy, String description){
		this(frameTimes, dx, dy, description, null);
	}
	
	public MovementActionFrameInfo(long[] frameTimes, float dx, float dy, String description, IRotationController rotationController){
		this(frameTimes, dx, dy, description, rotationController, false);
	}
	
	public MovementActionFrameInfo(long[] frameTimes, float dx, float dy, String description, boolean enableGravity){
		this(frameTimes, dx, dy, description, null, enableGravity);
	}
	
	public MovementActionFrameInfo(long[] frameTimes, float dx, float dy, String description, IRotationController rotationController, boolean enableGravity){
		super(0, 0, dx, dy);
		
		this.frameTimes = frameTimes;
		this.dx = dx;
		this.dy = dy;
		this.description = description;
		this.rotationController = rotationController;
		this.enableGravity = enableGravity;
		if(enableGravity)
			this.gravityController = new GravityController();
	}
	
	public MovementActionFrameInfo(long[] frameTimes, float dx, float dy, String description, IRotationController rotationController, boolean enableGravity, Sprite sprite, String spriteActionName){
		super(0, 0, dx, dy, description, rotationController, enableGravity, sprite, spriteActionName);
		
		this.frameTimes = frameTimes;
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

	@Override
	public boolean equals(Object obj) {  
        if (obj == null) return false;  
        if (!(obj instanceof MovementActionFrameInfo)) return false;
        MovementActionFrameInfo info = (MovementActionFrameInfo) obj;
        return (this.total == info.getTotal() && this.delay == info.getDelay() && this.dx == info.getDx() && this.dy == info.getDy());  
      }
	
	public long[] getFrame() {
		return frameTimes;
	}
	
	public void setFrame(long[] frameTimes) {
		this.frameTimes = frameTimes;
	}
	
	
}
