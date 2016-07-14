package com.example.try_gameengine.remotecontroller.custome;

import android.view.MotionEvent;

public class Custome4D2FKeyCommandPressUp implements Custome4D2FCommand{
	Key key;
	Custome4D2FCommandType custome4D2FCommandType;
	private int pointerId = -1;
	
	public Custome4D2FKeyCommandPressUp(Key key, Custome4D2FCommandType custome4D2FCommandType) {
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
	public Custome4D2FCommandType execute() {
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
