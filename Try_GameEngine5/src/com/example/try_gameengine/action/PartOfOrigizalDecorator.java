package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

public class PartOfOrigizalDecorator extends MovementDecorator {
	private MovementAction action;
	boolean isCancelFocusAppendPart = false;
	
	public PartOfOrigizalDecorator(MovementAction action) {
		this.action = action;
//		this.copyMovementActionList = action.copyMovementActionList;
	}

	protected MovementAction coreCalculationMovementActionInfo(
			MovementAction action) {
		
//		MovementActionInfo newInfo = new MovementActionInfo(action.getTotal(), action.getDelay(), action.getDx(), action.getDy(), action.getDescription());
//		if(this.getAction().getActions().size() != 0){
//			MovementAction action = new MovementActionItemCountDownTimer(newInfo);
////			copyMovementActionList.add(action);
//		}
//		return newInfo;
		
		MovementAction copy = null;
		
		try {
			copy = (MovementAction) action.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return copy;
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
		return "PartOfOrigizalDecorator " + action.getDescription();
	}

	@Override
	protected MovementAction initTimer(){ super.initTimer();
		if (this.getAction().getActions().size() == 0) {
			MovementActionInfo info = action.getInfo();
			action.getAction().setInfo(info);
			action.getAction().initTimer();

		} else {		
			this.getAction().initTimer();
//			doIn(null);
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
//			for(int i = infos.size() - 2*copyMovementActionList.size() ; i < infos.size() - copyMovementActionList.size(); i++){
//				MovementActionInfo info = infos.get(i);
//				newInfos.add(info);
//			}
			return newInfos;
		}
	}
	
	@Override
	public List<MovementActionInfo> getMovementInfoList() {
		return action.getMovementInfoList();
	}
	
	@Override
	protected List<MovementAction> doIn(MovementActionSet actionSet){		
		List<MovementAction> actions = action.doIn(actionSet);
		
		this.getAction().currentInfoList =  this.getCurrentInfoList();
		this.isCancelFocusAppendPart = true;
		
		return new ArrayList<MovementAction>();
	}
}