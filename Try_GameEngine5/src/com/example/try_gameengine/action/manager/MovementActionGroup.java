package com.example.try_gameengine.action.manager;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.listener.IActionListener;

public class MovementActionGroup {
	String id;
	List<MovementAction> movementActions = new ArrayList<MovementAction>();
	OnGroupListener onGroupListener;
	int startCount, finishCount;
	boolean isAutoResetAfterLastFinish = true;
	
	public MovementActionGroup(String id) {
		// TODO Auto-generated constructor stub
		this.id = id;

		onGroupListener = new OnGroupListener() {
			
			@Override
			public void onStart(int startIndex) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLastStart() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLastFinish() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFirstStart() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFirstFinish() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFinish(int finishIndex) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	public void addMovementAction(String id, MovementAction movementAction) {
		// TODO Auto-generated constructor stub
		this.id = id;
		movementActions.add(movementAction);
	}
	
	public void setOnGroupListener(OnGroupListener onGroupListener){
		this.onGroupListener = onGroupListener;
	}
	
	public void setMovementActionListener(String id, final IActionListener actionListener){
		MovementAction action = movementActions.get(0);
		action.setActionListener(new IActionListener() {
			
			@Override
			public void beforeChangeFrame(int nextFrameId) {
				// TODO Auto-generated method stub
				actionListener.beforeChangeFrame(nextFrameId);
			}
			
			@Override
			public void afterChangeFrame(int periousFrameId) {
				// TODO Auto-generated method stub
				actionListener.afterChangeFrame(periousFrameId);
			}
			
			@Override
			public void actionStart() {
				// TODO Auto-generated method stub
				actionListener.actionStart();
				
				onGroupListener.onStart(startCount);
				if(startCount==movementActions.size()-1)
					onGroupListener.onLastStart();
				startCount++;
			}
			
			@Override
			public void actionFinish() {
				// TODO Auto-generated method stub
				actionListener.actionFinish();
				
				onGroupListener.onFinish(finishCount);
				if(finishCount==movementActions.size()-1)
					onGroupListener.onLastFinish();
				finishCount++;
			}
			
			@Override
			public void actionCycleFinish() {
				// TODO Auto-generated method stub
				actionListener.actionCycleFinish();
			}
		});
	}
	
	public void setMovementActionListener(MovementAction action, final IActionListener actionListener){
		action.setActionListener(new IActionListener() {
			
			@Override
			public void beforeChangeFrame(int nextFrameId) {
				// TODO Auto-generated method stub
				actionListener.beforeChangeFrame(nextFrameId);
			}
			
			@Override
			public void afterChangeFrame(int periousFrameId) {
				// TODO Auto-generated method stub
				actionListener.afterChangeFrame(periousFrameId);
			}
			
			@Override
			public void actionStart() {
				// TODO Auto-generated method stub
				actionListener.actionStart();
				
				if(startCount==0)
					onGroupListener.onFirstStart();
				onGroupListener.onStart(startCount);
				if(startCount==movementActions.size()-1){
					onGroupListener.onLastStart();
					startCount=0;
				}else{
					startCount++;
				}				
			}
			
			@Override
			public void actionFinish() {
				// TODO Auto-generated method stub
				actionListener.actionFinish();
				
				if(finishCount==0)
					onGroupListener.onFirstFinish();
				onGroupListener.onFinish(finishCount);
				Log.e(MovementActionGroup.class.getName(), "finishCount: "+finishCount+"");
				Log.e(MovementActionGroup.class.getName(), "movementActions size: " + movementActions.size()+"");
				if(finishCount==movementActions.size()-1){
					onGroupListener.onLastFinish();
					if(isAutoResetAfterLastFinish){
						movementActions.clear();
						finishCount=0;
					}
				}else{
					finishCount++;
				}
				
			}
			
			@Override
			public void actionCycleFinish() {
				// TODO Auto-generated method stub
				actionListener.actionCycleFinish();
			}
		});
	}
	
	public void removeMovementAction(MovementAction action){
		movementActions.remove(action);
	}
	
	public void startAll(){
		for(int i = 0; i<movementActions.size(); i++){
			MovementAction action = movementActions.get(i);
			action.start();
		}
	}
	
	public void start(MovementAction action){
//		startCount--;
//		finishCount--;
		action.start();
	}
	
	public void stop(MovementAction action){
		action.controller.cancelAllMove();
	}
	
	public void stopAll(){
		for(int i = 0; i<movementActions.size(); i++){
			MovementAction action = movementActions.get(i);
			action.controller.cancelAllMove();
		}
	}
	
	public void reset(){
		movementActions.clear();
		startCount=0;
		finishCount=0;
	}
	
	public void setAutoResetAfterLastFinish(boolean isAutoResetAfterLastFinish ){
		this.isAutoResetAfterLastFinish = isAutoResetAfterLastFinish;
	}
	
	public interface OnGroupListener{
		public void onFirstStart();
		public void onLastStart();
		public void onStart(int startIndex);
		public void onFirstFinish();
		public void onLastFinish();
		public void onFinish(int finishIndex);
	}
}
