package com.example.try_gameengine.framework;

import android.view.MotionEvent;

public class TargetTouchHandler extends TouchHandler {
	
	public TargetTouchHandler(ITouchable touch, int priority){
		super(touch, priority, true);
	}
	
	public TargetTouchHandler(ITouchable touch, int priority, boolean consumeTouch){
		super(touch, priority, consumeTouch);
	}
	
	public boolean onTouchBegan(MotionEvent event) {
		// TODO Auto-generated method stub
		return delegate.onTouchBegan(event);
	}

	public void onTouchEnded(MotionEvent event) {
		// TODO Auto-generated method stub
		delegate.onTouchEnded(event);
	}

	public void onTouchMoved(MotionEvent event) {
		// TODO Auto-generated method stub
		delegate.onTouchMoved(event);
	}

	public void onTouchCancelled(MotionEvent event) {
		// TODO Auto-generated method stub
		delegate.onTouchCancelled(event);
	}
}
