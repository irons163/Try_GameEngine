package com.example.try_gameengine.framework;

import android.view.MotionEvent;

public interface ITouchable extends ISystemTouchDelegate{
	public boolean onTouchBegan(MotionEvent event);
	public void onTouchMoved(MotionEvent event);
	public void onTouchEnded(MotionEvent event);
	public void onTouchCancelled(MotionEvent event);
}
