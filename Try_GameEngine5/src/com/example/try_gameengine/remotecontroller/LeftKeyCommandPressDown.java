package com.example.try_gameengine.remotecontroller;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

public class LeftKeyCommandPressDown implements Command{
	LeftKey leftKey;
	
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

}
