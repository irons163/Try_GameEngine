package com.example.try_gameengine.remotecontroller.custome;

import android.view.MotionEvent;

public class Custom4D2FKeyCommandPressUp implements Custom4D2FCommand{
	Key key;
	Custom4D2FCommandType custome4D2FCommandType;
	private int pointerId = -1;
	
	public Custom4D2FKeyCommandPressUp(Key key, Custom4D2FCommandType custome4D2FCommandType) {
		// TODO Auto-generated constructor stub
		this.key = key;
		this.custome4D2FCommandType = custome4D2FCommandType;
	}
	
	@Override
	public boolean checkExecute(float x, float y, MotionEvent event) {
		// TODO Auto-generated method stub
		return key.pressUp(x, y, event);
	}

	@Override
	public Custom4D2FCommandType execute() {
		// TODO Auto-generated method stub
		return this.custome4D2FCommandType;
	}

	@Override
	public void setMotionEventPointerId(int pointerId) {
		// TODO Auto-generated method stub
		this.pointerId = pointerId;
	}

	@Override
	public int getMotionEventPointerId() {
		// TODO Auto-generated method stub
		return pointerId;
	}
}
