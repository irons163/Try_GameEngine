package com.example.try_gameengine.remotecontroller.custome;

import android.view.MotionEvent;

public class Custome4D2FNoCommand implements Custome4D2FCommand{
	private int pointerId = -1;
	
	@Override
	public Custome4D2FCommandType execute() {
		// TODO Auto-generated method stub
		return Custome4D2FCommandType.None;
	}

	@Override
	public boolean checkExecute(float x, float y, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMotionEventPointerId(int pointerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMotionEventPointerId() {
		// TODO Auto-generated method stub
		return pointerId;
	}
}
