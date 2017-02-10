package com.example.try_gameengine.action;

import java.util.List;

import android.util.Log;

public class InverseMovementInfoAppendDecorator extends MovementDecorator {

	public InverseMovementInfoAppendDecorator(MovementAction action) {
		this.action = action;
		this.copyMovementActionList = action.copyMovementActionList;
	}

	private MovementActionInfo coreCalculationMovementActionInfo(
			MovementActionInfo info) {

		MovementActionInfo newInfo = new MovementActionInfo(info.getTotal(),
				info.getDelay(), info.getDx(), info.getDy(),
				info.getDescription());
		if (this.getAction().getActions().size() != 0) {
			MovementAction action = new MovementActionItem(newInfo);
			copyMovementActionList.add(action);
			this.getAction().totalCopyMovementActionList.add(action);
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
		copyMovementActionList.clear();
		this.getAction().getCurrentInfoList();
		int i = 0;

		for (int j = 0; j < this.getAction().currentInfoList.size(); j++) {
			MovementActionInfo info = this.getAction().currentInfoList.get(j);
			Log.e("count", ++i + "");
			Log.e("info", info.getDx() + "");
			this.getAction().setInfo(info);

			coreCalculationMovementActionInfo(this.getAction().getInfo());
		}

		for (MovementAction action : copyMovementActionList) {
			this.getAction().addMovementAction(action);
			this.getAction().movementItemList.add(action);
			action.description = "inverseAppend";
			action.initTimer();
		}

		for (MovementAction movementItem : this.getAction().movementItemList) {
			movementItem.initTimer();
		}

	}
}
