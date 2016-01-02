package com.example.try_gameengine.remotecontroller;

import android.view.MotionEvent;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

public class UpKeyCommandPressDown implements Command{
	UpKey upKey;
	private int pointerId = -1;
	
	public UpKeyCommandPressDown(UpKey upKey){
		this.upKey = upKey;
	}
	
	@Override
	public CommandType execute() {
		// TODO Auto-generated method stub
		return CommandType.UPKeyDownCommand;
	}

	@Override
	public boolean checkExecute(float x, float y, MotionEvent event) {
		// TODO Auto-generated method stub
		return upKey.pressUp(x, y, event);
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
