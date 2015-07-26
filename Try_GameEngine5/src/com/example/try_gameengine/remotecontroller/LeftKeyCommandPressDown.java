package com.example.try_gameengine.remotecontroller;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

public class LeftKeyCommandPressDown implements Command{
	LeftKey leftKey;
	private int pointerId = -1;
	
	public LeftKeyCommandPressDown(LeftKey leftKey){
		this.leftKey = leftKey;
	}
	
	@Override
	public CommandType execute() {
		// TODO Auto-generated method stub
		return CommandType.LeftKeyDownCommand;
	}

	@Override
	public boolean checkExecute(float x, float y) {
		// TODO Auto-generated method stub
		return leftKey.pressDown(x, y);
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
