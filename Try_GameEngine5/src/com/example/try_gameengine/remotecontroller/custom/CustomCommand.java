package com.example.try_gameengine.remotecontroller.custom;

import com.example.try_gameengine.framework.ALayer;

import android.view.MotionEvent;

/**
 * {@code Custom4D2FCommand} is a custom command handler for 4D2F(4 Direction keys
 * and 2 Function keys), total 6 keys.
 * 
 * @author irons
 * 
 */
public interface CustomCommand {
	/**
	 * checkExecute 
	 * @param x
	 * 			not
	 * @param y
	 * @param event
	 * @return
	 */
	public boolean checkExecute(float x, float y, MotionEvent event);

	/**
	 * execute.
	 * @return
	 */
	public ALayer execute();

	/**
	 * @param pointerId
	 */
	public void setMotionEventPointerId(int pointerId);

	/**
	 * @return
	 */
	public int getMotionEventPointerId();
}