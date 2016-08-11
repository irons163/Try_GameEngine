package com.example.try_gameengine.Camera;

public abstract class ACamera {
	
	public abstract void rotation(float rotation);
	public abstract void translate(float dx, float dy);
	public abstract void zoom(float scale);
	
	public abstract void bindLayerXY();
	public abstract void bindLayerX();
	public abstract void bindLayerY();
	public abstract void setIsAutoStopOnBound();
}
