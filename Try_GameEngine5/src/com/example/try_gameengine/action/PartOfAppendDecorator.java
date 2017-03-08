package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

public class PartOfAppendDecorator extends MovementDecorator {
	private MovementAction action;
	boolean isCancelFocusAppendPart = false;
	
	public PartOfAppendDecorator(MovementAction action) {
		this.action = action;
		this.copyMovementActionList = action.copyMovementActionList;
	}

	protected MovementActionInfo coreCalculationMovementActionInfo(
			MovementActionInfo info) {
		
		MovementActionInfo newInfo = new MovementActionInfo(info.getTotal(), info.getDelay(), info.getDx(), info.getDy(), info.getDescription());
		if(this.getAction().getActions().size() != 0){
			MovementAction action = new MovementActionItemCountDownTimer(newInfo);
			copyMovementActionList.add(action);
		}
		return newInfo;
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
		return "PartOfAppendDecorator  " + action.getDescription();
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
	public MovementActionInfo getInfo() {
		return coreCalculationMovementActionInfo(action.getInfo());
	}

	@Override
	public List<MovementAction> getCurrentActionList() {
		return action.getCurrentActionList();
	}

	@Override
	public List<MovementActionInfo> getCurrentInfoList() {
//		if(this.getAction().isCancelFocusAppendPart || isCancelFocusAppendPart){
		if(isCancelFocusAppendPart){
			return action.getCurrentInfoList();
		}else{
			List<MovementActionInfo> infos = action.getCurrentInfoList();
			List<MovementActionInfo> newInfos = new ArrayList<MovementActionInfo>();
			for(int i = infos.size() - copyMovementActionList.size(); i < infos.size(); i++){
				MovementActionInfo info = infos.get(i);
				newInfos.add(info);
			}
			return newInfos;
		}
	}
	
	@Override
	public List<MovementActionInfo> getMovementInfoList() {
		return action.getMovementInfoList();
	}
	
	@Override
	public void doIn(){		
		action.doIn();
		
		this.getAction().currentInfoList =  this.getCurrentInfoList();
		this.isCancelFocusAppendPart = true;
	}
}