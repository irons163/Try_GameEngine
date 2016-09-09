package com.example.try_gameengine.remotecontroller;

import android.view.MotionEvent;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

public class RightKeyCommandPressUp implements Command{
	RightKey rightKey;
	private int pointerId = -1;
	
	public RightKeyCommandPressUp(RightKey rightKey){
		this.rightKey = rightKey;
	}
	
	@Override
	public CommandType execute() {
		return CommandType.RightKeyUpCommand;
	}

	@Override
	public boolean checkExecute(float x, float y, MotionEvent event) {
		return rightKey.pressUp(x, y, event);
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
