package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.CopyMoveDecorator.CopyMoveDecoratorMementoImpl;
import com.example.try_gameengine.action.MovementAction.MovementActionMementoImpl;
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener;

//import com.rits.cloning.Cloner;

import android.util.Log;

public class DoubleDecorator extends MovementDecorator {
	private MovementAction action;

	public DoubleDecorator(MovementAction action) {
		this.action = action;
		allMovementActoinList.add(this);
		this.copyMovementActionList = action.copyMovementActionList;
	}

	private MovementActionInfo coreCalculationMovementActionInfo(
			MovementActionInfo info) {
		info.setTotal(info.getTotal());
		info.setDelay(info.getDelay());
		info.setDx(2 * info.getDx());
		info.setDy(2 * info.getDy());
		return info;
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
	public MovementAction initMovementAction() {
		return initTimer();
	}

	@Override
	protected MovementAction initTimer() {

		if (this.getAction().getActions().size() == 0) {
			action.getAction().setInfo(getInfo());
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
	public MovementActionInfo getInfo() {
		return coreCalculationMovementActionInfo(action.getInfo());
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
	public List<MovementAction> getMovementItemList() {
		return action.getMovementItemList();
	}

	@Override
	public List<MovementActionInfo> getMovementInfoList() {
		return action.getMovementInfoList();
	}

	@Override
	public void doIn() {
		action.doIn();
		int i = 0;
		for (MovementActionInfo info : this.getAction().currentInfoList) {
			Log.e("count", ++i + "");
			Log.e("info", info.getDx() + "");
			this.getAction().setInfo(info);
			coreCalculationMovementActionInfo(this.getAction().getInfo());
		}

		for (MovementAction movementItem : this.getAction().movementItemList) {
			movementItem.initTimer();
		}
	}

	@Override
	public void cancelMove() {
		action.getAction().cancelMove();
	}

	@Override
	void pause() {
		action.getAction().pause();
	}
	
	public IMovementActionMemento createMovementActionMemento(){
		movementActionMemento = new DoubleDecoratorMementoImpl(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCancelFocusAppendPart, isFinish, isLoop, isSigleThread, name, cancelAction, allMovementActoinList, action);
		return movementActionMemento;
	}
	
	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
//		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento;
		super.restoreMovementActionMemento(this.movementActionMemento);
		DoubleDecoratorMementoImpl mementoImpl = (DoubleDecoratorMementoImpl) this.movementActionMemento;
		this.action = mementoImpl.action;
	}
	
	protected static class DoubleDecoratorMementoImpl extends MovementActionMementoImpl{
	
		private MovementAction action; //Decorator
		
		public DoubleDecoratorMementoImpl(List<MovementAction> actions,
				Thread thread, TimerOnTickListener timerOnTickListener,
				String description,
				List<MovementAction> copyMovementActionList,
				List<MovementActionInfo> currentInfoList,
				List<MovementAction> movementItemList,
				List<MovementAction> totalCopyMovementActionList,
				boolean isCancelFocusAppendPart, boolean isFinish,
				boolean isLoop, boolean isSigleThread, String name,
				MovementAction cancelAction, List<MovementAction> allMovementActoinList, MovementAction action) {
			super(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCancelFocusAppendPart, isFinish, isLoop, isSigleThread, name, cancelAction, allMovementActoinList);
			this.action = action;
		}

		public MovementAction getAction() {
			return action;
		}

		public void setAction(MovementAction action) {
			this.action = action;
		}			
	}
}
