package com.example.try_gameengine.remotecontroller.custome;

import android.view.MotionEvent;

public interface Custome4D2FCommand {
	public boolean checkExecute(float x, float y, MotionEvent event);
	public Custome4D2FCommandType execute();
	public void setMotionEventPointerId(int pointerId);
	public int getMotionEventPointerId();
}