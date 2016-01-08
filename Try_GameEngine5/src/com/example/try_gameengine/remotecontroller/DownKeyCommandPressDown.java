package com.example.try_gameengine.remotecontroller;

import android.view.MotionEvent;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

public class DownKeyCommandPressDown implements Command{
	DownKey downKey;
	private int pointerId = -1;
	
	public DownKeyCommandPressDown(DownKey downKey){
		this.downKey = downKey;
	}
	
	@Override
	public CommandType execute() {
		// TODO Auto-generated method stub
		return CommandType.DownKeyDownCommand;
	}

	@Override
	public boolean checkExecute(float x, float y, MotionEvent event) {
		// TODO Auto-generated method stub
		return downKey.pressDown(x, y, event);
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
