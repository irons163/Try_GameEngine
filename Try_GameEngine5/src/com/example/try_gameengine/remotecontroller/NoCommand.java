package com.example.try_gameengine.remotecontroller;

import android.view.MotionEvent;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

/**
 * This no command is a default for remote controller.
 * @author irons
 *
 */
public class NoCommand implements Command{
	private int pointerId = -1;
	
	@Override
	public CommandType execute() {
		return CommandType.None;
	}

	@Override
	public boolean checkExecute(float x, float y, MotionEvent event) {
		return false;
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
