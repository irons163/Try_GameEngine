package com.example.try_gameengine.Camera;

/**
 * Camera is common object in game engine. It can control the whole display. 
 * @author irons
 *
 */
public abstract class ACamera {
	
	/**
	 * rotation camera.
	 * @param rotation
	 */
	public abstract void rotation(float rotation);
	/**
	 * translate camera.
	 * @param dx for x-dir translate.
	 * @param dy for y-dir translate.
	 */
	public abstract void translate(float dx, float dy);
	/**
	 * zoom(change xscale or yscale) cameara.
	 * @param scale
	 */
	public abstract void zoom(float scale);
	
	public abstract void bindLayerXY();
	public abstract void bindLayerX();
	public abstract void bindLayerY();
	public abstract void setIsAutoStopOnBound();
}
