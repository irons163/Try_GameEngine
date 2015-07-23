package com.example.try_gameengine.remotecontroller;

import android.graphics.Bitmap;

import com.example.try_gameengine.framework.Sprite;

public class UpKey extends Sprite{
	
	public UpKey(Bitmap bitmap, float x, float y, int scale, boolean autoAdd) {
		super(bitmap, x, y, scale, autoAdd);
		// TODO Auto-generated constructor stub
	}
	
	public boolean pressDown(float x, float y){
		return dst.contains(x, y);
	}
	
	public boolean pressUp(float x, float y){  
		return dst.contains(x, y);
	}
}
