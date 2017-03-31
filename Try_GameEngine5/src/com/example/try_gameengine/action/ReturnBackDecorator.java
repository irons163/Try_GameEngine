package com.example.try_gameengine.action;

import java.util.Collections;
import java.util.List;

import android.util.Log;

public class ReturnBackDecorator extends MovementDecorator{
	private MovementAction action;

	public ReturnBackDecorator(MovementAction action) {
		this.action = action;
//		this.copyMovementActionList = action.copyMovementActionList;
	}

	protected MovementAction coreCalculationMovementActionInfo(
			MovementAction action) {
		MovementActionInfo info = action.getInfo();
		info.setTotal(info.getTotal());
		info.setDelay(info.getDelay());
		info.setDx(-info.getDx());
		info.setDy(-info.getDy());
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

		} else {	
			this.getAction().initTimer();
			doIn(null);
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
	protected List<MovementAction> doIn(MovementActionSet actionSet){
		List<MovementAction> actions = action.doIn(actionSet);
		this.getAction().getCurrentInfoList();
		for (MovementActionInfo info : this.getAction().currentInfoList) {
			this.getAction().setInfo(info);
			coreCalculationMovementActionInfo(this.getAction());
		}

		inverseOrder(this);
		
		return actions;
		
//		for (MovementAction movementItem : this.getAction().movementItemList) {
//			movementItem.initTimer();
//		}
	}
	
	private void inverseOrder(MovementAction targetAction){
		Collections.reverse(targetAction.getAction().getActions());
		for(MovementAction action : targetAction.getAction().getActions()){
			inverseOrder(action);
		}
//			inverseOrder(targetAction.get)
	}
}
