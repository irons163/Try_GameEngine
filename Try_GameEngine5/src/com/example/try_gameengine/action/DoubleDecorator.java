package com.example.try_gameengine.action;

import java.util.List;

import android.util.Log;

public class DoubleDecorator extends MovementDecorator {

	public DoubleDecorator(MovementAction action) {
		this.action = action;
//		this.copyMovementActionList = action.copyMovementActionList;
	}

	protected MovementAction coreCalculationMovementActionInfo(
			MovementAction action) {
		MovementActionInfo info = action.getInfo();
		info.setTotal(info.getTotal());
		info.setDelay(info.getDelay());
		info.setDx(2 * info.getDx());
		info.setDy(2 * info.getDy());
		return action;
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
		return "Double " + action.getDescription();
	}

	@Override
	protected MovementAction initTimer(){ super.initTimer();

		if (this.getAction().getActions().size() == 0) {
			action.getAction().setInfo(getInfo());
			action.getAction().initTimer();
		} else { //this.getAction() is a MovementAction set or group or decorator. 
			this.getAction().initTimer();
//			doIn();
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

	@Override
	public void cancelMove() {
		action.getAction().cancelMove();
	}

	@Override
	void pause() {
		action.getAction().pause();
	}
	
//	@Override
//	public IMovementActionMemento createMovementActionMemento(){
//		movementActionMemento = new DoubleDecoratorMementoImpl(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCancelFocusAppendPart, isFinish, isLoop, isSigleThread, name, cancelAction, action, isRepeatSpriteActionIfMovementActionRepeat);
//		return movementActionMemento;
//	}
//	
//	@Override
//	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
////		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento;
//		super.restoreMovementActionMemento(this.movementActionMemento);
//		DoubleDecoratorMementoImpl mementoImpl = (DoubleDecoratorMementoImpl) this.movementActionMemento;
//		this.action = mementoImpl.action;
//	}
//	
//	protected static class DoubleDecoratorMementoImpl extends MovementActionMementoImpl{
//	
//		private MovementAction action; //Decorator
//		
//		public DoubleDecoratorMementoImpl(List<MovementAction> actions,
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
	protected DoubleDecorator clone() throws CloneNotSupportedException {
		DoubleDecorator copy = new DoubleDecorator((MovementAction) this.action.clone());
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
