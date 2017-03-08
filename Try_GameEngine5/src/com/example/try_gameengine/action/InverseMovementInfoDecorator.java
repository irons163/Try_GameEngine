package com.example.try_gameengine.action;

import java.util.List;

import android.util.Log;

public class InverseMovementInfoDecorator extends MovementDecorator{

	public InverseMovementInfoDecorator(MovementAction action) {
		this.action = action;
		this.copyMovementActionList = action.copyMovementActionList;
	}

	private MovementActionInfo coreCalculationMovementActionInfo(
			MovementActionInfo info) {
		info.setTotal(info.getTotal());
		info.setDelay(info.getDelay());
		info.setDx(-info.getDx());
		info.setDy(-info.getDy());
//		if(info.getRotationController()!=null)
//			info.getRotationController().setRotation(-info.getRotationController().getRotation());
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
	public MovementAction initMovementAction(){	
		return initTimer();
	}

	@Override
	protected MovementAction initTimer(){ super.initTimer();

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
	public List<MovementActionInfo> getMovementInfoList() {
		return action.getMovementInfoList();
	}
	
	@Override
	public void doIn(){
		action.doIn();
		this.getAction().getCurrentInfoList();
		int i = 0;
		for (MovementActionInfo info : this.getAction().currentInfoList) {
			Log.e("count", ++i + "");
			Log.e("info", info.getDx() + "");
			this.getAction().setInfo(info);
			coreCalculationMovementActionInfo(this.getAction().getInfo());
		}

//		for (MovementAction movementItem : this.getAction().movementItemList) {
//			movementItem.initTimer();
//		}
	}
}
