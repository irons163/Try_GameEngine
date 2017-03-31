package com.example.try_gameengine.remotecontroller.custom;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.try_gameengine.remotecontroller.IRemoteController;
import com.example.try_gameengine.remotecontroller.custom.CustomRemoteControl;
import com.example.try_gameengine.remotecontroller.custome.Custom4D2FRemoteController;
import com.example.try_gameengine.remotecontroller.custome.Custom4D2FRemoteLoader;
import com.example.try_gameengine.remotecontroller.custome.Key;
import com.example.try_gameengine.utils.GameTimeUtil;

/**
 * 
 * @author irons
 * 
 */
public class CustomRemoteController implements IRemoteController {
	private static CustomRemoteController remoteController;
	private static CustomRemoteControl remoteControl;
	private static CustomRemoteLoader remoteLoader;

	RemoteContollerListener remoteContollerListener;
	RemoteContollerOnTouchEventListener remoteContollerOnTouchEventListener;
	List<CustomTouch> commandTypes = new ArrayList<CustomTouch>();
	GameTimeUtil remoteControllerTimeUtil;

	public interface RemoteContollerOnTouchEventListener {
		public boolean onTouchEvent(MotionEvent event);
	}

	private static final int INVALID_POINTER_ID = -1;

	// The active pointer is the one currently moving our object.
	private int mActivePointerId = INVALID_POINTER_ID;

	private RemoteContollerOnTouchEventListener defaultRemoteContollerOnTouchEventListener = new RemoteContollerOnTouchEventListener() {

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			boolean isCatchTouchEvent = false;
			float x = event.getX();
			float y = event.getY();
			int action = event.getAction();
			switch (action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				mActivePointerId = event.getPointerId(0);
				isCatchTouchEvent = pressDown(x, y, mActivePointerId, event);
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				final int downPointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
				mActivePointerId = event.getPointerId(downPointerIndex);
				x = event.getX(downPointerIndex);
				y = event.getY(downPointerIndex);
				isCatchTouchEvent = pressDown(x, y, mActivePointerId, event);
				break;
			case MotionEvent.ACTION_UP:
				mActivePointerId = event.getPointerId(0);
				isCatchTouchEvent = pressUp(x, y, mActivePointerId, event);
				break;

			case MotionEvent.ACTION_CANCEL:
				mActivePointerId = INVALID_POINTER_ID;
				break;

			case MotionEvent.ACTION_POINTER_UP:
				// Extract the index of the pointer that left the touch sensor
				final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
				final int pointerId = event.getPointerId(pointerIndex);
				x = event.getX(pointerIndex);
				y = event.getY(pointerIndex);
				isCatchTouchEvent = pressUp(x, y, pointerId, event);
				break;
			}
			return isCatchTouchEvent;
		}
	};

	public interface RemoteContollerListener {
		public void pressDown(List<CustomTouch> commandTouch);
	}

	private RemoteContollerListener defaultRemoteContollerListener = new RemoteContollerListener() {

		@Override
		public void pressDown(List<CustomTouch> commandTypes) {
			for (CustomTouch commandType : commandTypes) {
				
			}
		}

	};

	private CustomRemoteController() {
		remoteLoader = new CustomRemoteLoader();
		remoteControl = remoteLoader.getRemoteControl();
		remoteControllerTimeUtil = new GameTimeUtil(0);
		remoteContollerListener = defaultRemoteContollerListener;
		remoteContollerOnTouchEventListener = defaultRemoteContollerOnTouchEventListener;
	};

	public static CustomRemoteController createRemoteController() {
		if (remoteController == null) {
			synchronized (Custom4D2FRemoteController.class) {
				if (remoteController == null)
					remoteController = new CustomRemoteController();
			}
		}
		return remoteController;
	}

	/**
	 * press
	 * @param x
	 * @param y
	 * @param motionEventPointerId
	 * @param event
	 * @return
	 */
	public boolean pressDown(float x, float y, int motionEventPointerId,
			MotionEvent event) {
		CustomTouch customTouch = remoteControl.executePressDown(x,
				y, motionEventPointerId, event);


		if (customTouch == null) {
			return false;
		} else {
//			CustomTouch customTouch = new CustomTouch(left, event);
			commandTypes.add(customTouch);

			if (remoteControllerTimeUtil.isArriveExecuteTime()) {
				remoteContollerListener.pressDown(commandTypes);
				commandTypes.clear();
			}
			
			return true;
		}
//		return execute;
	}

	public boolean pressUp(float x, float y, int motionEventPointerId,
			MotionEvent event) {
		CustomTouch customTouch = remoteControl.executePressUp(x, y,
				motionEventPointerId, event);

		if (customTouch == null) {
			return false;
		} else {
			commandTypes.add(customTouch);

			if (remoteControllerTimeUtil.isArriveExecuteTime()) {
				remoteContollerListener.pressDown(commandTypes);
				commandTypes.clear();
			}
			return true;
		}
	}

//	public void execute() {
//		this.remoteContollerListener.pressDown(commandTypes);
//	}

	public void setRemoteContollerListener(
			RemoteContollerListener remoteContollerListener) {
		this.remoteContollerListener = remoteContollerListener;
	}

	public void setRemoteContollerOnTouchEventListener(
			RemoteContollerOnTouchEventListener remoteContollerOnTouchEventListener) {
		this.remoteContollerOnTouchEventListener = remoteContollerOnTouchEventListener;
	}

	public boolean onTouchEvent(MotionEvent event) {
		return this.remoteContollerOnTouchEventListener.onTouchEvent(event);
	}

	public void drawRemoteController(Canvas canvas, Paint paint) {
//		remoteLoader.getLeftKey().drawSelf(canvas, paint);
//		remoteLoader.getRightKey().drawSelf(canvas, paint);
//		remoteLoader.getUpKey().drawSelf(canvas, paint);
//		remoteLoader.getDownKey().drawSelf(canvas, paint);
//		remoteLoader.getEnterKey().drawSelf(canvas, paint);
//		remoteLoader.getCancelKey().drawSelf(canvas, paint);
		
		for(Key key : remoteLoader.keys){
			key.drawSelf(canvas, paint);
		}
	}
	
	public void addKey(Key key){
		remoteLoader.keys.add(key);
	}
	
	
}
