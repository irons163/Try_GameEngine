package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.rits.cloning.Cloner;

import android.util.Log;

public class TwoCurveDecorator extends MovementDecorator {
	private MovementAction action;

	public TwoCurveDecorator(MovementAction action) {
		this.action = action;
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
	public MovementAction initMovementAction(){
//		Cloner cloner=new Cloner();
//
//		MovementAction actionClone = cloner.deepClone(this);
//		
//		this.actions = actionClone.actions;
//		action = actionClone.getAction();
//		action.copyMovementActionList = actionClone.copyMovementActionList;
		
//		if (this.getAction().getActions().size() == 0) {
////			this.getAction().setInfo(action.getInfo());
//			action.getAction().setInfo(getInfo());
//			action.getAction().initTimer();
//
//		}
		
		return initTimer();
	}

	@Override
	protected MovementAction initTimer() {

		if (this.getAction().getActions().size() == 0) {

//			for (MovementAction action : this.getAction().getActions()) {
//				this.getAction().setInfo(action.getInfo());
				action.getAction().setInfo(getInfo());
				action.getAction().initTimer();
//			}

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
	public void doIn(){
		action.doIn();
//		this.getAction().getCurrentInfoList();
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
	public void cancelMove(){
		action.getAction().cancelMove();
	}
	
	@Override
	void pause(){
		action.getAction().pause();
	}
}
