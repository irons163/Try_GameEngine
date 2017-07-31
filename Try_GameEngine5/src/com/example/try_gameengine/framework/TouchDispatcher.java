package com.example.try_gameengine.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

import android.R.bool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

public class TouchDispatcher implements ISystemTouchDelegate{
	List<TargetTouchHandler> touchHandlers = new CopyOnWriteArrayList<TargetTouchHandler>();
	List<StandardTouchHandler> standardTouchHandlers = new CopyOnWriteArrayList<StandardTouchHandler>();
	int touchDispatcherEnableFlag;
	int touchDispatcherConsumeFlag;
	boolean hasTouchableObjectConsumed;
	TouchDispatcherType touchDispatcherType = TouchDispatcherType.DISPATCH_IMMEDIATE;
	
	enum TouchDispatcherType{
		DISPATCH_IMMEDIATE, DISPATCH_WHEN_GAME_PROCESS
	}
	
/////////////////////
////	SystemTouchDisPatcher(Android View)
////		V	
////	Check TouchDisPatcher enabled
////		V
////	Check TargetTouchDisPatcher enabled 
////		V
////	Loop TargetTouch(if one of TargetTouchObject claimed and consumed event, break the loop.)	
////		V
////	Check TargetTouchDisPatcher consumed touch event
////		V
////	Check StandardTouchDisPatcher enabled 
////		V
////	Loop StandardTouch(if one of StandardTouchObject claimed and consumed event, break the loop.)	
////		V
////	Check StandardTouchDisPatcher consumed touch event
////		V	
////	Check DefaultDrawOrderTouchDisPatcher enabled 
////		V
////	Loop default touch(if one of default touch object(Layers) claimed and consumed event, break the loop.)	
////		V
////	Check DefaultDrawOrderTouchDisPatcher consumed touch event
////		V
////	back to SystemTouchDisPatcher(Android View)
/////////////////////
	enum TouchDispatcherFlagType{
		ENABLE_FALG, CONSUME_FALG
	}
	
    static class TouchDispatcherFlag{
		public final static int ENABLE_NONE = 0;
		public final static int ENABLE_TOUCH_DISPATCHER = 1<<0;
		public final static int ENABLE_TARGET_TOUCH_DISPATCHER = 1<<1;
		public final static int ENABLE_STANDARD_TOUCH_DISPATCHER = 1<<2;
		public final static int ENABLE_STANDARD_DRAW_ORDER_TOUCH_DISPATCHER = 1<<3;
	/////////////////////
		public final static int CONSUME_NONE = 0;
		public final static int CONSUME_TOUCH_EVENT_BY_TOUCH_DISPATCHER = 1<<0;
		public final static int CONSUME_TOUCH_EVENT_BY_TARGET_TOUCH_DISPATCHER = 1<<1;
		public final static int CONSUME_TOUCH_EVENT_BY_STANDARD_TOUCH_DISPATCHER = 1<<2;
		public final static int CONSUME_TOUCH_EVENT_BY_STANDARD_ORDER_TOUCH_DISPATCHER = 1<<3;
    }
/////////////////////	
	
	private static class TouchDispatcherHolder{
		public static TouchDispatcher touchDispatcher = new TouchDispatcher();
	}
	
	private TouchDispatcher(){
		addFlag(TouchDispatcherFlag.ENABLE_TOUCH_DISPATCHER
				|TouchDispatcherFlag.ENABLE_TARGET_TOUCH_DISPATCHER
				|TouchDispatcherFlag.ENABLE_STANDARD_TOUCH_DISPATCHER
				|TouchDispatcherFlag.ENABLE_STANDARD_DRAW_ORDER_TOUCH_DISPATCHER
				, TouchDispatcherFlagType.ENABLE_FALG);
		addFlag(TouchDispatcherFlag.CONSUME_TOUCH_EVENT_BY_TOUCH_DISPATCHER
				|TouchDispatcherFlag.CONSUME_TOUCH_EVENT_BY_TARGET_TOUCH_DISPATCHER
				|TouchDispatcherFlag.CONSUME_TOUCH_EVENT_BY_STANDARD_TOUCH_DISPATCHER
				|TouchDispatcherFlag.CONSUME_TOUCH_EVENT_BY_STANDARD_ORDER_TOUCH_DISPATCHER
				, TouchDispatcherFlagType.CONSUME_FALG);
		
		setTouchDispatcherType(getTouchDispatcherType());
	}
	
	public static TouchDispatcher getInstance(){
		return TouchDispatcherHolder.touchDispatcher;
	}
	
	public void addTargetTouchDelegate(ITouchable delegate, int priority){
		forceAddTouchHandler(new TargetTouchHandler(delegate, priority));
	}
	
	private void forceAddTouchHandler(TargetTouchHandler touchHandler){
		int i = 0;
		for(TouchHandler handler : touchHandlers){
			if(handler.getPriority() <= touchHandler.getPriority())
				i++;
			
			if(handler.getDelegate() == touchHandler.getDelegate())
				throw new RuntimeException("Delegate already added to touch dispatcher.");
		}
		
		touchHandlers.add(i, touchHandler);
	}
	
	public void removeTargetTouchDelegate(ITouchable delegate){
		for(TouchHandler handler : touchHandlers){
			if(handler.getDelegate() == delegate){
				touchHandlers.remove(handler);
				break;
			}
		}
	}
	
	public boolean containTargetTouchDelegate(ITouchable delegate){
		for(TouchHandler handler : touchHandlers){
			if(handler.getDelegate() == delegate)
				return true;
		}
		return false;
	}
	
	public void addStandardTouchDelegate(ITouchable delegate, int priority){
		forceAddStandardTouchHandler(new StandardTouchHandler(delegate, priority));
	}
	
	private void forceAddStandardTouchHandler(StandardTouchHandler touchHandler){
		int i = 0;
		for(StandardTouchHandler handler : standardTouchHandlers){
			if(handler.getPriority() <= touchHandler.getPriority())
				i++;
			
			if(handler.getDelegate() == touchHandler.getDelegate())
				throw new RuntimeException("Delegate already added to touch dispatcher.");
		}
		
		standardTouchHandlers.add(i, touchHandler);
	}
	
	public void removeStandardTouchDelegate(ITouchable delegate){
		for(StandardTouchHandler handler : standardTouchHandlers){
			if(handler.getDelegate() == delegate){
				touchHandlers.remove(handler);
				break;
			}
		}
	}
	
	public boolean containStandardTouchDelegate(ITouchable delegate){
		for(TouchHandler handler : standardTouchHandlers){
			if(handler.getDelegate() == delegate)
				return true;
		}
		return false;
	}
	
	public void removeTouchDelegates(ITouchable delegate){
		removeTargetTouchDelegate(delegate);
		removeStandardTouchDelegate(delegate);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(getTouchDispatcherType()==TouchDispatcherType.DISPATCH_IMMEDIATE){
			return dispatch(event);
		}else if(getTouchDispatcherType()==TouchDispatcherType.DISPATCH_WHEN_GAME_PROCESS){
			TouchEventManager.getInstance().addEvent(event);
		}

		return false;
	}
	
	public boolean onTouchEvent(MotionEvent event, ITouchable eventLisntener) {
		// TODO Auto-generated method stub
		if(getTouchDispatcherType()==TouchDispatcherType.DISPATCH_IMMEDIATE){
			eventLisntener.onTouchEvent(event);
			return dispatch(event);
		}else if(getTouchDispatcherType()==TouchDispatcherType.DISPATCH_WHEN_GAME_PROCESS){
			TouchEventManager.getInstance().addEvent(event);
		}

		return false;
	}
	
	public void addToFirstTargetTouchDelegate(ITouchable delegate){
		touchHandlers.add(0, new TargetTouchHandler(delegate, Integer.MIN_VALUE));
	}
	
	public void addToFirstStandardTouchDelegate(ITouchable delegate){
		standardTouchHandlers.add(0, new StandardTouchHandler(delegate, Integer.MIN_VALUE));
	}
	
//	public void addToLastTargetTouchDelegate(ITouchable delegate){
//		touchHandlers.add(0, new TargetTouchHandler(delegate, Integer.MIN_VALUE));
//	}
//	
//	public void addToLasttStandardTouchDelegate(ITouchable delegate){
//		standardTouchHandlers.add(0, new StandardTouchHandler(delegate, Integer.MIN_VALUE));
//	}
	
	public boolean dispatch(){
		MotionEvent event = TouchEventManager.getInstance().getEvent();
		if(event != null){
			Log.e("event", "has");
			return dispatch(event);
		}
		Log.e("event", "no has");
		return false;
	}
	
	public boolean dispatch(MotionEvent event){
		hasTouchableObjectConsumed = false;
		
		if(!checkIsFlagEnabled(TouchDispatcherFlag.ENABLE_TOUCH_DISPATCHER, TouchDispatcherFlagType.ENABLE_FALG))
			return false;
		
		hasTouchableObjectConsumed = dispatchTouchEvent(event);
		
		return hasTouchableObjectConsumed && checkIsFlagEnabled(TouchDispatcherFlag.CONSUME_TOUCH_EVENT_BY_TOUCH_DISPATCHER, TouchDispatcherFlagType.CONSUME_FALG);
	}
	
	private boolean dispatchTouchEvent(MotionEvent event){
		return dispatchTouchEventByTargetTouchDispatcher(event) ||
				dispatchTouchEventByStandardTouchDispatcher(event) ||
				dispatchTouchEventByStandardDrawOrderTouchDispatcher(event);
	}
	
	private boolean dispatchTouchEventByTargetTouchDispatcher(MotionEvent event){
		boolean isConsumed = false;
		if(checkIsFlagEnabled(TouchDispatcherFlag.ENABLE_STANDARD_TOUCH_DISPATCHER, TouchDispatcherFlagType.ENABLE_FALG)){		
			ListIterator<TargetTouchHandler> iterator = touchHandlers.listIterator(touchHandlers.size());
			while(iterator.hasPrevious()){
				TargetTouchHandler handler = iterator.previous();
				boolean claimed = false;
				
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_POINTER_DOWN:
				case MotionEvent.ACTION_DOWN:
					claimed = handler.onTouchBegan(event);
					if(claimed)
						handler.claimed = claimed;
					break;
				case MotionEvent.ACTION_MOVE:
					if(handler.claimed)
						handler.onTouchMoved(event);
					break;
				case MotionEvent.ACTION_POINTER_UP:
				case MotionEvent.ACTION_UP:
					if(handler.claimed){
						handler.onTouchEnded(event);
						handler.claimed = false;
					}
					break;
				case MotionEvent.ACTION_CANCEL:
					if(handler.claimed){
						handler.onTouchCancelled(event);
						handler.claimed = false;
					}
					break;
				default:
					break;
				}
				
				if(claimed && handler.isConsumeTouch()){
					isConsumed = true;
					break;
				}
			}
			
			if(checkIsFlagEnabled(TouchDispatcherFlag.CONSUME_TOUCH_EVENT_BY_TARGET_TOUCH_DISPATCHER, TouchDispatcherFlagType.CONSUME_FALG))
				if(isConsumed)
					return isConsumed;
		}
		return isConsumed;
	}
	
	private boolean dispatchTouchEventByStandardTouchDispatcher(MotionEvent event){
		boolean isConsumed = false;
		if(checkIsFlagEnabled(TouchDispatcherFlag.ENABLE_STANDARD_TOUCH_DISPATCHER, TouchDispatcherFlagType.ENABLE_FALG)){
			isConsumed = false;
			ListIterator<StandardTouchHandler> StandardIterator = standardTouchHandlers.listIterator(standardTouchHandlers.size());
			while(StandardIterator.hasPrevious()){
				StandardTouchHandler handler = StandardIterator.previous();
				if(handler.onTouchEvent(event) && handler.isConsumeTouch()){
					isConsumed =  true;
					break;
				}
			}
			
			if(checkIsFlagEnabled(TouchDispatcherFlag.CONSUME_TOUCH_EVENT_BY_STANDARD_TOUCH_DISPATCHER, TouchDispatcherFlagType.CONSUME_FALG))
				if(isConsumed)
					return isConsumed;
		}
		return isConsumed;
	}
	
	private boolean dispatchTouchEventByStandardDrawOrderTouchDispatcher(MotionEvent event){
		boolean isConsumed = false;
		if(checkIsFlagEnabled(TouchDispatcherFlag.ENABLE_STANDARD_DRAW_ORDER_TOUCH_DISPATCHER, TouchDispatcherFlagType.ENABLE_FALG)){
			isConsumed = LayerManager.getInstance().onTouchLayersForOppositeZOrder(event) || LayerManager.getInstance().onTouchLayersForNegativeZOrder(event);
			if(checkIsFlagEnabled(TouchDispatcherFlag.CONSUME_TOUCH_EVENT_BY_STANDARD_TOUCH_DISPATCHER, TouchDispatcherFlagType.CONSUME_FALG))
				if(isConsumed)
					return isConsumed;
		}
		return isConsumed;
	}
	
	public void setFlag(int touchDispatcherFlag, TouchDispatcherFlagType touchDispatcherFlagType){
		switch (touchDispatcherFlagType) {
			case ENABLE_FALG:
				this.touchDispatcherEnableFlag = touchDispatcherFlag;
				break;
			case CONSUME_FALG:
				this.touchDispatcherConsumeFlag = touchDispatcherFlag;
				break;
		}
	}
	
	public int getFlag(TouchDispatcherFlagType touchDispatcherFlagType){
		switch (touchDispatcherFlagType) {
			case ENABLE_FALG:
				return this.touchDispatcherEnableFlag;
			case CONSUME_FALG:
				return this.touchDispatcherConsumeFlag;
		}
		
		return 0;
	}
	
	public void addFlag(int touchDispatcherFlag, TouchDispatcherFlagType touchDispatcherFlagType){
		switch (touchDispatcherFlagType) {
		case ENABLE_FALG:
			this.touchDispatcherEnableFlag = this.touchDispatcherEnableFlag|touchDispatcherFlag;
			break;
		case CONSUME_FALG:
			this.touchDispatcherConsumeFlag = this.touchDispatcherConsumeFlag|touchDispatcherFlag;
			break;
		}
	}
	
	public void removeFlag(int touchDispatcherFlag, TouchDispatcherFlagType touchDispatcherFlagType){
		switch (touchDispatcherFlagType) {
		case ENABLE_FALG:
			this.touchDispatcherEnableFlag &= ~touchDispatcherFlag;
			break;
		case CONSUME_FALG:
			this.touchDispatcherConsumeFlag &= ~touchDispatcherFlag;
			break;
		}
	}
	
	public boolean checkIsFlagEnabled(int flagForCheck, TouchDispatcherFlagType touchDispatcherFlagType){
		return ((getFlag(touchDispatcherFlagType) & flagForCheck) == flagForCheck);
	}
	
	public boolean isEnabled(){
		return checkIsFlagEnabled(TouchDispatcherFlag.ENABLE_TOUCH_DISPATCHER, TouchDispatcherFlagType.ENABLE_FALG);
	}

	public TouchDispatcherType getTouchDispatcherType() {
		return touchDispatcherType;
	}

	public void setTouchDispatcherType(TouchDispatcherType touchDispatcherType) {
		this.touchDispatcherType = touchDispatcherType;
		
		if(touchDispatcherType == TouchDispatcherType.DISPATCH_WHEN_GAME_PROCESS){
			TouchEventManager.getInstance().reset();
		}
	}
}
