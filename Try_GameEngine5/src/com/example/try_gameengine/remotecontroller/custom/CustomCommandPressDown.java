package com.example.try_gameengine.remotecontroller.custom;

import android.view.MotionEvent;

import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.remotecontroller.custome.Key;

/**
 * {@code CustomCommandPressDown}, this is press down command.
 * @author irons
 *
 */
public class CustomCommandPressDown implements CustomCommand{
	Key key;
	MotionEvent event;
	private int pointerId = -1;
	
	/**
	 * constructor.
	 * @param key key.
	 * @param custome4D2FCommandType command type.
	 */
	public CustomCommandPressDown(Key key) {
		// TODO Auto-generated constructor stub
		this.key = key;
//		this.event = event;
	}
	
	@Override
	public boolean checkExecute(float x, float y, MotionEvent event) {
		// TODO Auto-generated method stub
		return key.pressDown(x, y, event);
	}

	@Override
	public ALayer execute() {
		// TODO Auto-generated method stub
		return this.key;
	}

	@Override
	public void setMotionEventPointerId(int pointerId) {
		// TODO Auto-generated method stub
		this.pointerId = pointerId;
	}

	@Override
	public int getMotionEventPointerId() {
		// TODO Auto-generated method stub
		return pointerId;
	}

}