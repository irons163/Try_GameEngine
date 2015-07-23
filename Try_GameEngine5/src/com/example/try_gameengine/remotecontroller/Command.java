package com.example.try_gameengine.remotecontroller;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;


public interface Command {
	public boolean checkExecute(float x, float y);
	public CommandType execute();
}
