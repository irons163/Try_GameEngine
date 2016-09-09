package com.example.try_gameengine.remotecontroller;

import android.view.MotionEvent;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

public interface Command {
	public boolean checkExecute(float x, float y, MotionEvent event);

	public CommandType execute();

	public void setMotionEventPointerId(int pointerId);

	public int getMotionEventPointerId();
}
