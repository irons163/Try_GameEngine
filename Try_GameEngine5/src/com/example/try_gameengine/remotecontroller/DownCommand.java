package com.example.try_gameengine.remotecontroller;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

public class DownCommand implements Command{
	UpKey upKey;
	private int pointerId = -1;
	
	public DownCommand(UpKey upKey){
		this.upKey = upKey;
	}
	
	@Override
	public CommandType execute() {
		// TODO Auto-generated method stub
		return CommandType.UPKeyUpCommand;
	}

	@Override
	public boolean checkExecute(float x, float y) {
		// TODO Auto-generated method stub
		return upKey.pressDown(x, y);
	}

	@Override
	public void setMotionEventPointerId(int pointerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMotionEventPointerId() {
		// TODO Auto-generated method stub
		return -1;
	}

}
