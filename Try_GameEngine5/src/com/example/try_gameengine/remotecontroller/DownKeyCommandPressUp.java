package com.example.try_gameengine.remotecontroller;

import android.view.MotionEvent;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

public class DownKeyCommandPressUp implements Command {
	DownKey downKey;
	private int pointerId = -1;

	public DownKeyCommandPressUp(DownKey downKey) {
		this.downKey = downKey;
	}

	@Override
	public CommandType execute() {
		return CommandType.DownKeyUpCommand;
	}

	@Override
	public boolean checkExecute(float x, float y, MotionEvent event) {
		return downKey.pressUp(x, y, event);
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
