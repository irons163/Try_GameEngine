package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.rits.cloning.Cloner;

import android.util.Log;

public class CircleDecorator extends MovementDecorator {
	private MovementAction action;
	private float x, y, mx, my;
	private MathUtil mathUtil;
	private CircleController circleController;

	public CircleDecorator(MovementAction action, float x, float y, float mx,
			float my) {
		this.action = action;
		this.copyMovementActionList = action.copyMovementActionList;
		this.x = x;
		this.y = y;
		this.mx = mx;
		this.my = my;
		mathUtil = new MathUtil(mx - x, my - y);

	}

	public CircleDecorator(MovementAction action, MovementActionInfo circleInfo) {
		this.action = action;
		this.copyMovementActionList = action.copyMovementActionList;
		this.x = x;
		this.y = y;
		this.mx = mx;
		this.my = my;
		mathUtil = new MathUtil(mx - x, my - y);
		circleInfo.getRotationController().execute(circleInfo);
		this.circleController = circleController;
	}

	public void setSubCircleController(CircleController circleController) {
		this.circleController = circleController;
	}

	private MovementActionInfo coreCalculationMovementActionInfo(
			MovementActionInfo info) {
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
}
