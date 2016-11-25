package com.example.try_gameengine.framework;

import android.view.MotionEvent;

public class TouchHandler {
	protected ITouchable delegate;
	protected int priority;
	protected boolean claimed;
	protected boolean consumeTouch;
	
	public TouchHandler(ITouchable touch, int priority){
		this(touch, priority, true);
	}
	
	public TouchHandler(ITouchable touch, int priority, boolean consumeTouch){
		this.delegate = touch;
		this.priority = priority;
		this.consumeTouch = consumeTouch;
	}
	
	public ITouchable getDelegate() {
		return delegate;
	}
	
	public void setDelegate(ITouchable delegate) {
		this.delegate = delegate;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public boolean isClaimed() {
		return claimed;
	}
	
	public void setClaimed(boolean claimed) {
		this.claimed = claimed;
	}
	
	public boolean isConsumeTouch() {
		return consumeTouch;
	}

	public void setConsumeTouch(boolean consumeTouch) {
		this.consumeTouch = consumeTouch;
	}

	public boolean onTouchEvent(MotionEvent event) {
		return delegate.onTouchEvent(event);
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
