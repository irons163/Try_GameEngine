package com.example.try_gameengine.framework;

import android.view.MotionEvent;

public class StandardTouchHandler extends TouchHandler{

	public StandardTouchHandler(ITouchable touch, int priority) {
		super(touch, priority);
		// TODO Auto-generated constructor stub
	}

	public StandardTouchHandler(ITouchable touch, int priority, boolean consumeTouch) {
		super(touch, priority, consumeTouch);
		// TODO Auto-generated constructor stub
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		return delegate.onTouchEvent(event);
	}
}
