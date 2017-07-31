package com.example.try_gameengine.framework;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.view.MotionEvent;

public class TouchEventManager {
	List<MotionEvent> eventList = new CopyOnWriteArrayList<MotionEvent>();
	List<MotionEvent> moveEventList = new CopyOnWriteArrayList<MotionEvent>();
	int maxMoveEventCount = 10;
	int moveEventCount;
	
	private static class TouchEventManagerHolder{
		public static TouchEventManager touchEventManager = new TouchEventManager();
	}
	
	private TouchEventManager(){

	}
	
	public static TouchEventManager getInstance(){
		return TouchEventManagerHolder.touchEventManager;
	}
	
	public void addEvent(MotionEvent event){
		MotionEvent motionEvent = MotionEvent.obtain(event);
		eventList.add(motionEvent);
		if(event.getAction() == MotionEvent.ACTION_MOVE){
			if(moveEventList.size() >= maxMoveEventCount)
				moveEventList.clear();
			moveEventList.add(motionEvent);
		}
	}
	
	public MotionEvent getEvent(){
		if(eventList.size() > 0){
			MotionEvent event = eventList.remove(0);
			return event;
		}
		
		return null;
	}
	
	public void reset(){
		eventList.clear();
	}
	
//	public void processEvent(){
//		MotionEvent event = eventList.get(0);
//		LayerManager.getInstance().touc
//	}
}
