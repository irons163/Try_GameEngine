package com.example.try_gameengine.action;

import java.util.List;

public class HalfDecorator extends MovementDecorator{
	
	public HalfDecorator(MovementAction action){
		this.action = action;
	}
	
	protected MovementActionInfo coreCalculationMovementActionInfo(MovementActionInfo info){
		info.setTotal(info.getTotal());
		info.setDelay(info.getDelay());
		info.setDx(0.5f * info.getDx());
		info.setDy(0.5f * info.getDy());
		return info;
	}
	
	@Override
	public void start(){
		action.getAction().start();
	}

	@Override
	public MovementAction getAction(){
		return action.getAction();
	}
	
	@Override
	public String getDescription() {
		return "Half " + action.getDescription();
	}

	@Override
	protected MovementAction initTimer(){ super.initTimer();
		for(MovementAction action : this.getAction().getActions()){
			this.getAction().setInfo(action.getInfo());
			action.getAction().setInfo(getInfo());
			action.getAction().initTimer();
		}
		return this;
	}

	@Override
	public MovementAction addMovementAction(MovementAction action) {
		getAction().addMovementAction(action);
		return this;
	}
	
	@Override
	protected void setActionsTheSameTimerOnTickListener(){
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
}
