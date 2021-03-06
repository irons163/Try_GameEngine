package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.visitor.IMovementActionVisitor;

public class SimultaneouslyMovementActionSet extends MovementAction {
	private boolean isActionFinish = true;
	private MovementActionInfo info;
	
	public Object SimultaneouslyLock = new Object();
	
	private List<MovementAction> cancelActions = new ArrayList<MovementAction>();
	
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
			isActionFinish = false;

			thread = new Thread(new Runnable() {

				@Override
				public void run() {
					List<MovementAction> actionss = actions;
					for(MovementAction action : actions){
						cancelAction = action;
						action.start();
//						synchronized (action.getAction()) {
//							try {
//								action.getAction().wait();
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
//						}
//						action.getAction().thread.join();
					}
					
					for(MovementAction action : actions){
						try {
							action.getAction().thread.join();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
//					synchronized (action.getAction()) {
//						try {
//							action.getAction().wait();
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
					
					synchronized (SimultaneouslyMovementActionSet.this) {
						SimultaneouslyMovementActionSet.this.notifyAll();
					}
					isActionFinish = true;
				}
			});

			thread.start();
		}
	}
	
	@Override
	protected MovementAction initTimer(){ super.initTimer();
		for (MovementAction action : this.actions) {
			if(action.getAction().getActions().size()==0){
				action.initTimer();
			}else{
				action.initTimer();
			}
//			for(MovementAction movementAction : action.getAction().totalCopyMovementActionList){
//				this.getAction().movementItemList.add(movementAction);
//			}
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
		
//		movementItemList.clear();
//		for(MovementAction action : actions){
//			for(MovementAction actionItem : action.getCurrentActionList()){
//				movementItemList.add(actionItem);
//			}
//		}
		
//		return movementItemList;
		return null;
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
	void pause() {
		// TODO Auto-generated method stub
		for(MovementAction action : actions){
			action.getAction().pause();
		}
	}

	@Override
	public void accept(IMovementActionVisitor movementActionVisitor){
		movementActionVisitor.visitComposite(this);
		for(MovementAction movementAction : actions){
			movementAction.accept(movementActionVisitor);
		}
	}
	
	@Override
	public void trigger() {
		// TODO Auto-generated method stub
		for (MovementAction action : this.actions) {
			action.trigger();
		}
	}
}
