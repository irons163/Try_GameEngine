package com.example.try_gameengine.Camera;

public abstract class ACamera {
	
	public abstract void rotation();
	public abstract void translate();
	public abstract void zoom();
	
	public abstract void bindLayerXY();
	public abstract void bindLayerX();
	public abstract void bindLayerY();
	public abstract void setIsAutoStopOnBound();
}
