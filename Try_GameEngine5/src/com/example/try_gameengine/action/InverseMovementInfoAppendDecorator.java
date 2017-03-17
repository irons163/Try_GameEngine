package com.example.try_gameengine.action;

import java.util.List;

import android.util.Log;

public class InverseMovementInfoAppendDecorator extends MovementDecorator {

	public InverseMovementInfoAppendDecorator(MovementAction action) {
		this.action = action;
//		this.copyMovementActionList = action.copyMovementActionList;
	}

	protected MovementActionInfo coreCalculationMovementActionInfo(
			MovementActionInfo info) {

		MovementActionInfo newInfo = new MovementActionInfo(info.getTotal(),
				info.getDelay(), info.getDx(), info.getDy(),
				info.getDescription());
		if (this.getAction().getActions().size() != 0) {
			MovementAction action = new MovementActionItemCountDownTimer(newInfo);
//			copyMovementActionList.add(action);
//			this.getAction().totalCopyMovementActionList.add(action);
		}

		newInfo.setTotal(info.getTotal());
		newInfo.setDelay(info.getDelay());
		newInfo.setDx(-info.getDx());
		newInfo.setDy(-info.getDy());
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
