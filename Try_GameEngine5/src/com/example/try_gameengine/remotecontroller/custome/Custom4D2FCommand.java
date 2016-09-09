package com.example.try_gameengine.remotecontroller.custome;

import android.view.MotionEvent;

/**
 * {@code Custom4D2FCommand} is a custom command handler for 4D2F(4 Direction keys
 * and 2 Function keys), total 6 keys.
 * 
 * @author irons
 * 
 */
public interface Custom4D2FCommand {
	/**
	 * 
	 * @param x
	 * @param y
	 * @param event
	 * @return
	 */
	public boolean checkExecute(float x, float y, MotionEvent event);

	/**
	 * @return
	 */
	public Custom4D2FCommandType execute();

	/**
	 * @param pointerId
	 */
	public void setMotionEventPointerId(int pointerId);

	/**
	 * @return
	 */
	public int getMotionEventPointerId();
}