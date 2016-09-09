package com.example.try_gameengine.remotecontroller;

import android.view.MotionEvent;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

public class RightKeyCommandPressDown implements Command{
	RightKey rightKey;
	private int pointerId = -1;
	
	public RightKeyCommandPressDown(RightKey rightKey){
		this.rightKey = rightKey;
	}
	
	@Override
	public CommandType execute() {
		return CommandType.RightKeyDownCommand;
	}

	@Override
	public boolean checkExecute(float x, float y, MotionEvent event) {
		return rightKey.pressDown(x, y, event);
	}

	@Override
	public void setMotionEventPointerId(int pointerId) {
		this.pointerId = pointerId;
	}

	@Override
	public int getMotionEventPointerId() {
		return pointerId;
	}

}
