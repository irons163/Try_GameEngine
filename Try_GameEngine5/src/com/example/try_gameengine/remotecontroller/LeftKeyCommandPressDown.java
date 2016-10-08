package com.example.try_gameengine.remotecontroller;

import android.view.MotionEvent;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

/**
 * left key press down.
 * @author irons
 *
 */
public class LeftKeyCommandPressDown implements Command{
	LeftKey leftKey;
	private int pointerId = -1;
	
	public LeftKeyCommandPressDown(LeftKey leftKey){
		this.leftKey = leftKey;
	}
	
	@Override
	public CommandType execute() {
		return CommandType.LeftKeyDownCommand;
	}

	@Override
	public boolean checkExecute(float x, float y, MotionEvent event) {
		return leftKey.pressDown(x, y, event);
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
