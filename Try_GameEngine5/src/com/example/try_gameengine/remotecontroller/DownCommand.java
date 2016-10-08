package com.example.try_gameengine.remotecontroller;

import android.view.MotionEvent;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

/**
 * 
 * @author irons
 *
 */
public class DownCommand implements Command {
	UpKey upKey;
	private int pointerId = -1;

	public DownCommand(UpKey upKey) {
		this.upKey = upKey;
	}

	@Override
	public CommandType execute() {
		// TODO Auto-generated method stub
		return CommandType.UPKeyUpCommand;
	}

	@Override
	public boolean checkExecute(float x, float y, MotionEvent event) {
		// TODO Auto-generated method stub
		return upKey.pressDown(x, y, event);
	}

	@Override
	public void setMotionEventPointerId(int pointerId) {
		// TODO Auto-generated method stub
		this.pointerId = pointerId;
	}

	@Override
	public int getMotionEventPointerId() {
		// TODO Auto-generated method stub
//		return -1;
		return pointerId;
	}

}
