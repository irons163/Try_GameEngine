package com.example.try_gameengine.remotecontroller.custome;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.Sprite;

public class Key extends Sprite{
	
	public Key(Bitmap bitmap, float x, float y, int scale, boolean autoAdd) {
		super(bitmap, x, y, scale, autoAdd);
		// TODO Auto-generated constructor stub
	}
	
	public boolean pressDown(float x, float y, MotionEvent event){
		return onTouchEvent(event);
	}
	
	public boolean pressUp(float x, float y, MotionEvent event){  
		return onTouchEvent(event);
	}
}
