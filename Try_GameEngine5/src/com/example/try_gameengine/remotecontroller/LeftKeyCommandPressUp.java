package com.example.try_gameengine.remotecontroller;

import android.view.MotionEvent;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

/**
 * left key press up.
 * @author irons
 *
 */
public class LeftKeyCommandPressUp implements Command{
	LeftKey leftKey;
	private int pointerId = -1;
	
	public LeftKeyCommandPressUp(LeftKey leftKey){
		this.leftKey = leftKey;
	}
	
	@Override
	public CommandType execute() {
		return CommandType.LeftKeyUpCommand;
	}

	@Override
	public boolean checkExecute(float x, float y, MotionEvent event) {
		return leftKey.pressUp(x, y, event);
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
