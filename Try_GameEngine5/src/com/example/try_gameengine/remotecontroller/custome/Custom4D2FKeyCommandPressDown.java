package com.example.try_gameengine.remotecontroller.custome;

import android.view.MotionEvent;


/**
 * {@code Custom4D2FKeyCommandPressDown}, this is press down command.
 * @author irons
 *
 */
public class Custom4D2FKeyCommandPressDown implements Custom4D2FCommand{
	Key key;
	Custom4D2FCommandType custome4D2FCommandType;
	private int pointerId = -1;
	
	/**
	 * constructor.
	 * @param key key.
	 * @param custome4D2FCommandType command type.
	 */
	public Custom4D2FKeyCommandPressDown(Key key, Custom4D2FCommandType custome4D2FCommandType) {
		// TODO Auto-generated constructor stub
		this.key = key;
		this.custome4D2FCommandType = custome4D2FCommandType;
	}
	
	@Override
	public boolean checkExecute(float x, float y, MotionEvent event) {
		// TODO Auto-generated method stub
		return key.pressDown(x, y, event);
	}

	@Override
	public Custom4D2FCommandType execute() {
		// TODO Auto-generated method stub
		return this.custome4D2FCommandType;
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
