package com.example.try_gameengine.action;

import java.util.List;


/**
 * MovementActionSet is a set of MovementAcion.
 * @author irons
 *
 */
public class MovementActionSetWithThread extends MovementActionSet {
	
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
					// TODO Auto-generated method stub
					List<MovementAction> actionss = actions;
					actionListener.actionStart();
					do{
						for(MovementAction action : actions){
							cancelAction = action;
							action.start();
							synchronized (action.getAction()) {
								try {
									action.getAction().wait();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
//									throw new RuntimeException();
									Thread.currentThread().interrupt();
								}
							}
	
						} 
						actionListener.actionCycleFinish();
					}while(isLoop);
					
					synchronized (MovementActionSetWithThread.this) {
						MovementActionSetWithThread.this.notifyAll();
					}
					isActionFinish = true;
					actionListener.actionFinish();
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
			
//			action.getAction().setCancelFocusAppendPart(true);
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
		return null;
	}
	
	@Override
	public void setInfo(MovementActionInfo info){
//		this.info = info;
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
	public void trigger() {
		// TODO Auto-generated method stub
		for (MovementAction action : this.actions) {
			action.trigger();
		}
	}
	
	@Override
	protected MovementActionSetWithThread clone() throws CloneNotSupportedException {
		MovementActionSetWithThread copy = new MovementActionSetWithThread();
		copy.actionListener = this.actionListener;
		copy.timerOnTickListener = this.timerOnTickListener;
		copy.controller = this.controller;
		copy.timerOnTickListener = this.timerOnTickListener;
		for(MovementAction action : this.actions){
			MovementAction subCopy = (MovementAction) action.clone();
			copy.addMovementAction(subCopy);
		}
		return copy;
	}
}
