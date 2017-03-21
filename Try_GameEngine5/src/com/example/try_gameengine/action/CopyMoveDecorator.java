package com.example.try_gameengine.action;

import java.util.List;

import android.util.Log;

public class CopyMoveDecorator extends MovementDecorator {
//	boolean doing = false;

	public CopyMoveDecorator(MovementAction action) {
		this.action = action;
//		this.copyMovementActionList = action.copyMovementActionList;
	}

	protected MovementAction coreCalculationMovementActionInfo(
			MovementAction action) {
		MovementAction newAction = null;
		try {
			newAction = (MovementAction) action.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if (this.getAction().getActions().size() != 0) {
//			MovementAction action = new MovementActionItemCountDownTimer(newInfo);
//			copyMovementActionList.add(action);
//			this.getAction().totalCopyMovementActionList.add(action);
//		}
		
		return newAction;
	}

	@Override
	public void start() {
		action.getAction().start();
	}

	@Override
	public MovementAction getAction() {
		return action.getAction();
	}

	@Override
	public String getDescription() {
		return "Copy " + action.getDescription();
	}

	@Override
	protected MovementAction initTimer(){ super.initTimer();

		if (this.getAction().getActions().size() == 0) {
			MovementActionInfo info = action.getInfo();
			action.getAction().setInfo(info);
			action.getAction().initTimer();

		} else {
			this.getAction().initTimer();
			doIn();
		}
		return this;
	}

	@Override
	public MovementAction addMovementAction(MovementAction action) {
		getAction().addMovementAction(action);
		return this;
	}

	@Override
	protected void setActionsTheSameTimerOnTickListener() {
		getAction().setTimerOnTickListener(timerOnTickListener);
	}

	@Override
	public List<MovementAction> getCurrentActionList() {
		// TODO Auto-generated method stub
		return action.getCurrentActionList();
	}

	@Override
	public List<MovementActionInfo> getCurrentInfoList() {
		// TODO Auto-generated method stub
		return action.getCurrentInfoList();
	}

	@Override
	public List<MovementActionInfo> getMovementInfoList() {
		return action.getMovementInfoList();
	}

//	public IMovementActionMemento createMovementActionMemento(){
//		movementActionMemento = new CopyMoveDecoratorMementoImpl(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCancelFocusAppendPart, isFinish, isLoop, isSigleThread, name, cancelAction, action, isRepeatSpriteActionIfMovementActionRepeat);
//		return movementActionMemento;
//	}
//	
//	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
////		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento;
//		super.restoreMovementActionMemento(this.movementActionMemento);
//		CopyMoveDecoratorMementoImpl mementoImpl = (CopyMoveDecoratorMementoImpl) this.movementActionMemento;
//		this.action = mementoImpl.action;
//	}
//	
//	protected static class CopyMoveDecoratorMementoImpl extends MovementActionMementoImpl{
//	
//		private MovementAction action; //Decorator
//		
//		public CopyMoveDecoratorMementoImpl(List<MovementAction> actions,
//				Thread thread, TimerOnTickListener timerOnTickListener,
//				String description,
//				List<MovementAction> copyMovementActionList,
//				List<MovementActionInfo> currentInfoList,
//				List<MovementAction> movementItemList,
//				List<MovementAction> totalCopyMovementActionList,
//				boolean isCancelFocusAppendPart, boolean isFinish,
//				boolean isLoop, boolean isSigleThread, String name,
//				MovementAction cancelAction, MovementAction action,
//				boolean isRepeatSpriteActionIfMovementActionRepeat) {
//			super(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCancelFocusAppendPart, isFinish, isLoop, isSigleThread, name, cancelAction, isRepeatSpriteActionIfMovementActionRepeat);
//			this.action = action;
//		}
//
//		public MovementAction getAction() {
//			return action;
//		}
//
//		public void setAction(MovementAction action) {
//			this.action = action;
//		}			
//	}
	
	@Override
	protected CopyMoveDecorator clone() throws CloneNotSupportedException {
		CopyMoveDecorator copy = new CopyMoveDecorator((MovementAction) this.action.clone());
		copy.actionListener = this.actionListener;
		copy.timerOnTickListener = this.timerOnTickListener;
		copy.controller = this.controller;
		copy.timerOnTickListener = this.timerOnTickListener;
		for(MovementAction action : this.actions){
			MovementAction subCopy = (MovementAction) action.clone();
			copy.addMovementAction(subCopy);
		}
		copy.name = name;
		return copy;
	}
}