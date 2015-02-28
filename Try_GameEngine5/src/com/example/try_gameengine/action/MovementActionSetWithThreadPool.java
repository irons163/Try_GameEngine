package com.example.try_gameengine.action;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;

public class MovementActionSetWithThreadPool extends MovementAction {
	private boolean isActionFinish = true;
	private MovementActionInfo info;
	public boolean isStop = false;
	Future future;
	
	@Override
	public MovementAction addMovementAction(MovementAction action) {
		// TODO Auto-generated method stub
		actions.add(action);
		
		getCurrentActionList();
		getCurrentInfoList();
		
		return this;
	}

	@Override
	protected void setActionsTheSameTimerOnTickListener() {
		for (MovementAction action : actions) {
			action.getAction().setTimerOnTickListener(timerOnTickListener);
		}
	}

	private void frameStart(){
		for(MovementAction action : actions){
			cancelAction = action;
			
			action.start();
			
		}
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub

		if (isActionFinish) {
//			isActionFinish = false;
			
			
			future = executor.submit(new Runnable() {
				@Override
				public void run(){
					// TODO Auto-generated method stub
					List<MovementAction> actionss = actions;
					actionListener.actionStart();
					do{
						if(isActionFinish){
							
						
						isActionFinish = false;
						for(MovementAction action : actions){
							if(isStop){
								isLoop = false;
								break;
							}
							cancelAction = action;
							action.start();
							
//							if(!isStop)
//							synchronized (action.getAction()) {
//								try {
//									action.getAction().wait();
//								} catch (InterruptedException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//									isLoop = false;
//								}
//							}
	
							if(!isStop)
								isLoop = false;
//							try {
//								Thread.sleep(50000000);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//								Thread.currentThread().interrupt();
//								return;
//							}
							
						}
						actionListener.actionCycleFinish();
						
						}
					}while(isLoop);
					
//					synchronized (MovementActionSetWithThreadPool.this) {
//						MovementActionSetWithThreadPool.this.notifyAll();
//					}
					isActionFinish = true;
					actionListener.actionFinish();
				}
			});
			
			

//			thread.start();

			
		}
	}
	
	@Override
	public MovementAction initMovementAction(){		
		return initTimer();
	}
	
	@Override
	protected MovementAction initTimer() {
	
		for (MovementAction action : this.actions) {
			
			if(action.getAction().getActions().size()==0){
				action.initTimer();
			}else{
				action.initTimer();
			}
			for(MovementAction movementAction : action.getAction().totalCopyMovementActionList){
				this.getAction().movementItemList.add(movementAction);
			}
			
			action.getAction().setCancelFocusAppendPart(true);
		}
		this.getAction().getCurrentInfoList();

		return this;
	}	
	
	@Override
	public MovementAction getAction(){
		return this;
	}
	
	public List<MovementAction> getActions(){
		return actions;
	}

	@Override
	public MovementActionInfo getInfo() {
		// TODO Auto-generated method stub
		return info;
	}
	
	@Override
	public void setInfo(MovementActionInfo info){
		this.info = info;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		description = "Set[";
		for (MovementAction action : actions) {
			description += action.getDescription();
		}
		description += "]";
		return description;
	}
	
	@Override
	public List<MovementAction> getCurrentActionList() {
		// TODO Auto-generated method stub
		
		movementItemList.clear();
		for(MovementAction action : actions){
			for(MovementAction actionItem : action.getCurrentActionList()){
				movementItemList.add(actionItem);
			}
		}
		
		return movementItemList;
	}
	
	@Override
	public List<MovementActionInfo> getCurrentInfoList() {
		// TODO Auto-generated method stub

		currentInfoList.clear();
		for(MovementAction action : actions){
			for(MovementActionInfo actionItem : action.getCurrentInfoList()){
				currentInfoList.add(actionItem);
			}
		}
		
		return currentInfoList;
	}
	
	@Override
	public boolean isFinish(){
		return isActionFinish;
	}
	
	@Override
	public void trigger() {
		// TODO Auto-generated method stub
		for (MovementAction action : this.actions) {
			action.trigger();
		}
	}
	
	@Override
	void cancelAllMove() {
		// TODO Auto-generated method stub
		isStop = true;
		isLoop = false;
		future.cancel(true);
//		((Thread)future).interrupt();
//		executor.shutdown();
		super.cancelAllMove();
	}
	
	@Override
	void cancelMove() {
		// TODO Auto-generated method stub
		isStop = true;
		isLoop = false;
		future.cancel(true);
//		((Thread)future).interrupt();
//		executor.shutdown();
		super.cancelMove();
	}
}
