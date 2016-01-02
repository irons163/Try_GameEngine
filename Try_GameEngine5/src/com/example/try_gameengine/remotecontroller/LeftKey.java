package com.example.try_gameengine.remotecontroller;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.Sprite;

public class LeftKey extends Sprite{
	
	public LeftKey(Bitmap bitmap, float x, float y, int scale, boolean autoAdd) {
		super(bitmap, x, y, scale, autoAdd);
		// TODO Auto-generated constructor stub
	}
	
	public boolean pressDown(float x, float y, MotionEvent event){
//		return dst.contains(x, y);
		return onTouchEvent(event);
	}
	
	public boolean pressUp(float x, float y, MotionEvent event){  
//		return dst.contains(x, y);
		return onTouchEvent(event);
	}
}
