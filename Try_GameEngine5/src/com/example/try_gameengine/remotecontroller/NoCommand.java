package com.example.try_gameengine.remotecontroller;

import android.view.MotionEvent;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

public class NoCommand implements Command{
	private int pointerId = -1;
	
	@Override
	public CommandType execute() {
		// TODO Auto-generated method stub
		return CommandType.None;
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
