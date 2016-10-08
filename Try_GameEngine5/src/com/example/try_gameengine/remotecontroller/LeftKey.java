package com.example.try_gameengine.remotecontroller;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.Sprite;

/**
 * Left key for remote controller.
 * @author irons
 *
 */
public class LeftKey extends Sprite{
	
	/**
	 * constructor.
	 * @param bitmap
	 * 			bitmap.
	 * @param x
	 * 			position x.
	 * @param y
	 * 			position y.
	 * @param scale
	 * 			scale.
	 * @param autoAdd
	 * 			is autoAdd all not,
	 */
	public LeftKey(Bitmap bitmap, float x, float y, int scale, boolean autoAdd) {
		super(bitmap, x, y, scale, autoAdd);
	}
	
	/**
	 * do preesDown, if MotionEvent do.
	 * @param x
	 * @param y
	 * @param event
	 * @return
	 */
	public boolean pressDown(float x, float y, MotionEvent event){
		return onTouchEvent(event);
	}
	
	/**
	 *  do preess up, if MotionEvent can do.
	 * @param x
	 * @param y
	 * @param event
	 * @return
	 */
	public boolean pressUp(float x, float y, MotionEvent event){  
		return onTouchEvent(event);
	}
}
