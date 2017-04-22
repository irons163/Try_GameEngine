package com.example.try_gameengine.remotecontroller.custom;

import android.view.MotionEvent;

import com.example.try_gameengine.framework.ALayer;

public class CustomTouch {
	private ALayer touch;
	private MotionEvent event;
	
	public CustomTouch(ALayer touch, MotionEvent event) {
		this.touch = touch;
		this.event = event;
	}
	
	public ALayer getTouch() {
		return touch;
	}
	public void setTouch(ALayer touch) {
		this.touch = touch;
	}
	public MotionEvent getEvent() {
		return event;
	}
	public void setEvent(MotionEvent event) {
		this.event = event;
	}
	
	
}
