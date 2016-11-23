package com.example.try_gameengine.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import android.R.bool;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

public class TouchDispatcher implements ISystemTouchDelegate{
	List<TouchHandler> touchHandlers = new ArrayList<TouchHandler>();
	boolean dispatchEvent = true;
	
	private static class TouchDispatcherHolder{
		public static TouchDispatcher touchDispatcher = new TouchDispatcher();
	}
	
	private TouchDispatcher(){};
	
	public static TouchDispatcher getInstance(){
		return TouchDispatcherHolder.touchDispatcher;
	}
	
	public void addTouchDelegate(ITouchable delegate, int priority){
		forceAddTouchHandler(new TouchHandler(delegate, priority));
	}
	
	private void forceAddTouchHandler(TouchHandler touchHandler){
		int i = 0;
		for(TouchHandler handler : touchHandlers){
			if(handler.getPriority() <= touchHandler.getPriority())
				i++;
			
			if(handler.getDelegate() == touchHandler.getDelegate())
				throw new RuntimeException("Delegate already added to touch dispatcher.");
		}
		
		touchHandlers.add(i, touchHandler);
	}
	
	public void removeTouchDelegate(ITouchable delegate){
		for(TouchHandler handler : touchHandlers){
			if(handler.getDelegate() == delegate){
				touchHandlers.remove(handler);
				break;
			}
		}
	}
	
	public boolean isDispatchEvent() {
		return dispatchEvent;
	}

	public void setDispatchEvent(boolean dispatchEvent) {
		this.dispatchEvent = dispatchEvent;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		ListIterator<TouchHandler> iterator = touchHandlers.listIterator(touchHandlers.size());
		
		boolean isTouched = false;
//		if(dispatchEvent){
//			while(iterator.hasPrevious()){
//				TouchHandler handler = iterator.previous();
//				if(handler.onTouchEvent(event)){
//					isTouched =  true;
//					break;
//				}
//			}
//		}	
		
//		for(TouchHandler handler : touchHandlers){
		while(iterator.hasPrevious()){
			TouchHandler handler = iterator.previous();
			boolean claimed = false;
			
			if(dispatchEvent){
				if(handler.onTouchEvent(event)){
					isTouched =  true;
					break;
				}
			}
			
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
			
			if(claimed && handler.isConsumeTouch())
				break;
		}

		return !dispatchEvent||isTouched;
	}
}
