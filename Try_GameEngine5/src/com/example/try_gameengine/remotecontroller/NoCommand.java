package com.example.try_gameengine.remotecontroller;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

public class NoCommand implements Command{

	@Override
	public CommandType execute() {
		// TODO Auto-generated method stub
		return CommandType.None;
	}

	@Override
	public boolean checkExecute(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

}
