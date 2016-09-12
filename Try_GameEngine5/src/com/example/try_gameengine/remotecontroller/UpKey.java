package com.example.try_gameengine.remotecontroller;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.Sprite;

public class UpKey extends Sprite{
	
	public UpKey(Bitmap bitmap, float x, float y, int scale, boolean autoAdd) {
		super(bitmap, x, y, scale, autoAdd);
	}
	
	public boolean pressDown(float x, float y, MotionEvent event){
		return onTouchEvent(event);
	}
	
	public boolean pressUp(float x, float y, MotionEvent event){  
		return onTouchEvent(event);
	}
}
