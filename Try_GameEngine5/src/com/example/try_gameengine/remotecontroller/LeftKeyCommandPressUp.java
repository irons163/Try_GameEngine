package com.example.try_gameengine.remotecontroller;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

public class LeftKeyCommandPressUp implements Command{
	LeftKey leftKey;
	
	public LeftKeyCommandPressUp(LeftKey leftKey){
		this.leftKey = leftKey;
	}
	
	@Override
	public CommandType execute() {
		// TODO Auto-generated method stub
		return CommandType.LeftKeyUpCommand;
	}

	@Override
	public boolean checkExecute(float x, float y) {
		// TODO Auto-generated method stub
		return leftKey.pressUp(x, y);
	}

}
