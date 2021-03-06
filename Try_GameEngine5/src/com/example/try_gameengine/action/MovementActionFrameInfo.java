package com.example.try_gameengine.action;

import com.example.try_gameengine.framework.Sprite;

public class MovementActionFrameInfo extends MovementActionInfo{
	private long[] frameTimes;
	
	public MovementActionFrameInfo(long[] frameTimes, float dx, float dy){
		this(frameTimes, dx, dy, null);
	}
	
	public MovementActionFrameInfo(long[] frameTimes, float dx, float dy, String description){
		this(frameTimes, dx, dy, description, null, null);
	}
	
	public MovementActionFrameInfo(long[] frameTimes, float dx, float dy, String description, Sprite sprite, String spriteActionName){
		super(0, 0, dx, dy, description, sprite, spriteActionName);
		this.frameTimes = frameTimes;
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
