package com.example.try_gameengine.remotecontroller;

import android.view.MotionEvent;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

/**
 * {@code Command} is a default command handler for 4D(4 Direction keys
, total 4 keys.
 * 
 * @author irons
 */
public interface Command {
	/**
	 * @param x
	 * @param y
	 * @param event
	 * @return
	 */
	public boolean checkExecute(float x, float y, MotionEvent event);

	/**
	 * @return
	 */
	public CommandType execute();

	/**
	 * @param pointerId
	 */
	public void setMotionEventPointerId(int pointerId);

	/**
	 * @return
	 */
	public int getMotionEventPointerId();
}
