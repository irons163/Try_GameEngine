package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.visitor.IMovementActionVisitor;

public abstract class MovementActionSet extends MovementAction {
	protected boolean isActionFinish = true;

	public MovementActionSet() {
		super();
	}

	@Override
	public MovementAction addMovementAction(MovementAction action) {
		// TODO Auto-generated method stub
		actions.add(action);
		
		getCurrentActionList();
		getCurrentInfoList();
		
		return this;
	}
	
	@Override
	protected List<MovementAction> doIn(MovementActionSet actionSet) {
		// TODO Auto-generated method stub
		List<MovementAction> actions = super.doIn(this);
//		for(MovementAction action : actions){
//			addMovementAction(action);
//		}
		return new ArrayList<MovementAction>();
	}

	@Override
	protected void setActionsTheSameTimerOnTickListener() {
		for (MovementAction action : actions) {
			action.getAction().setTimerOnTickListener(timerOnTickListener);
		}
	}

	@Override
	public List<MovementAction> getCurrentActionList() {
			// TODO Auto-generated method stub
			
	//		movementItemList.clear();
	//		for(MovementAction action : actions){
	//			for(MovementAction actionItem : action.getCurrentActionList()){
	//				movementItemList.add(actionItem);
	//			}
	//		}
	//		
	//		return movementItemList;
			
			return null;
		}

	@Override
	public List<MovementActionInfo> getCurrentInfoList() {
		// TODO Auto-generated method stub
	
		currentInfoList.clear();
		for(MovementAction action : actions){
			for(MovementActionInfo actionItem : action.getCurrentInfoList()){
				currentInfoList.add(actionItem);
			}
		}
		
		return currentInfoList;
	}

	@Override
	public boolean isFinish() {
		return isActionFinish;
	}

	@Override
	protected void cancelAllMove() {
		// TODO Auto-generated method stub
		isLoop = false;
		super.cancelAllMove();
	}

	@Override
	public void accept(IMovementActionVisitor movementActionVisitor) {
		movementActionVisitor.visitComposite(this);
		for(MovementAction movementAction : actions){
			movementAction.accept(movementActionVisitor);
		}
	}

}