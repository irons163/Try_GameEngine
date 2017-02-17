package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import com.example.try_gameengine.action.MovementAction.MovementActionMementoImpl;
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener;
import com.example.try_gameengine.action.visitor.IMovementActionVisitor;

import android.util.Log;

//import com.rits.cloning.Cloner;

public class MovementActionSetGroupWithOutThread extends MovementAction {
	private boolean isActionFinish = true;
	private MovementActionInfo info;
	public boolean isStop = false;
	private int actionIndex;
	
	@Override
	public MovementAction addMovementAction(MovementAction action) {
		// TODO Auto-generated method stub
		actions.add(action);
		
		getCurrentActionList();
		getCurrentInfoList();
		
		return this;
	}

	@Override
	protected void setActionsTheSameTimerOnTickListener() {
		for (MovementAction action : actions) {
			action.getAction().setTimerOnTickListener(timerOnTickListener);
		}
	}

	private void frameStart(){
		for(MovementAction action : actions){
			cancelAction = action;
			
			action.start();
			
		}
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub

		if (isActionFinish) {
			isActionFinish = false;
			actionListener.actionStart();
			if(actions.size()>0){
				for(MovementAction action : actions){
//					cancelAction = action;
					action.start();
				}				
			}else{
				isActionFinish = true;
				actionListener.actionFinish();
			}
			
//			isActionFinish = false;

//					actionListener.actionStart();
//					do{
//						for(MovementAction action : actions){
//							if(isStop){
//								isLoop = false;
//								break;
//							}
//							cancelAction = action;
//							action.start();	
//						}
//						actionListener.actionCycleFinish();
//					}while(isLoop);

//					isActionFinish = true;
//					actionListener.actionFinish();

		}
	}
	
	@Override
	public MovementAction initMovementAction(){		
		return initTimer();
	}
	
	@Override
	protected MovementAction initTimer(){ super.initTimer();
	
		for (MovementAction action : this.actions) {
			
			if(action.getAction().getActions().size()==0){
				action.initTimer();
			}else{
				action.initTimer();
			}
			for(MovementAction movementAction : action.getAction().totalCopyMovementActionList){
				this.getAction().movementItemList.add(movementAction);
			}
			
//			action.getAction().setCancelFocusAppendPart(true);
		}
		this.getAction().getCurrentInfoList();

		return this;
	}	
	
	@Override
	public MovementAction getAction(){
		return this;
	}
	
	public List<MovementAction> getActions(){
		return actions;
	}

	@Override
	public MovementActionInfo getInfo() {
		// TODO Auto-generated method stub
		return info;
	}
	
	@Override
	public void setInfo(MovementActionInfo info){
		this.info = info;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		description = "Set[";
		for (MovementAction action : actions) {
			description += action.getDescription();
		}
		description += "]";
		return description;
	}
	
	@Override
	public List<MovementAction> getCurrentActionList() {
		// TODO Auto-generated method stub
		
		movementItemList.clear();
		for(MovementAction action : actions){
			for(MovementAction actionItem : action.getCurrentActionList()){
				movementItemList.add(actionItem);
			}
		}
		
		return movementItemList;
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
	public boolean isFinish(){
		return isActionFinish;
	}
	
	@Override
	public void trigger() {
		// TODO Auto-generated method stub
//		for (MovementAction action : this.actions) {
//			action.trigger();
//		}
		if(isActionFinish)
			return;

		boolean isAllFinish = true;
		for(MovementAction action : actions){
			action.trigger();
		}
		for(MovementAction action : actions){
			if(!action.isFinish()){
				isAllFinish = false;
				break;
			}
		}
		if(isAllFinish){
			isActionFinish = true;
			actionListener.actionFinish();
		}

	}
	
	@Override
	void cancelAllMove(){
		if(this.getAction().actions.size()!=0){
			for(MovementAction action : this.getAction().actions){
				action.cancelMove();
				Log.e("action", "cancel");
			}
//			this.thread.interrupt();
		}
		cancelMove();	
	}
	
	@Override
	void cancelMove(){
		isStop = true;
		isActionFinish = true;
		
//		if(!isSigleThread)
//			this.thread.interrupt();
	}
	
//	public IMovementActionMemento createMovementActionMemento(){
//		movementActionMemento = new MovementActionSetGroupWithOutThreadMementoImpl(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isActionFinish, isActionFinish, isActionFinish, isActionFinish, name, cancelAction, isActionFinish, info, isStop, actionIndex, isRepeatSpriteActionIfMovementActionRepeat);
//		return movementActionMemento;
//	}
//	
//	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
////		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento;
//		super.restoreMovementActionMemento(this.movementActionMemento);
//		MovementActionSetGroupWithOutThreadMementoImpl mementoImpl = (MovementActionSetGroupWithOutThreadMementoImpl) this.movementActionMemento;
//		this.isActionFinish = mementoImpl.isActionFinish;
//		this.info = mementoImpl.info;
//		this.isStop = mementoImpl.isStop;
//		this.actionIndex = mementoImpl.actionIndex;
//	}
//	
//	protected static class MovementActionSetGroupWithOutThreadMementoImpl extends MovementActionMementoImpl{
//	
//		private boolean isActionFinish;
//		private MovementActionInfo info;
//		private boolean isStop;
//		private int actionIndex;
//		
//		public MovementActionSetGroupWithOutThreadMementoImpl(List<MovementAction> actions,
//				Thread thread, TimerOnTickListener timerOnTickListener,
//				String description,
//				List<MovementAction> copyMovementActionList,
//				List<MovementActionInfo> currentInfoList,
//				List<MovementAction> movementItemList,
//				List<MovementAction> totalCopyMovementActionList,
//				boolean isCancelFocusAppendPart, boolean isFinish,
//				boolean isLoop, boolean isSigleThread, String name,
//				MovementAction cancelAction,
//				boolean isActionFinish, MovementActionInfo info,
//				boolean isStop, int actionIndex, boolean isRepeatSpriteActionIfMovementActionRepeat) {
//			super(actions, thread, timerOnTickListener, description,
//					copyMovementActionList, currentInfoList, movementItemList,
//					totalCopyMovementActionList, isCancelFocusAppendPart,
//					isFinish, isLoop, isSigleThread, name, cancelAction, isRepeatSpriteActionIfMovementActionRepeat);
//			this.isActionFinish = isActionFinish;
//			this.info = info;
//			this.isStop = isStop;
//			this.actionIndex = actionIndex;
//		}
//			
//	}
	
	@Override
	public void accept(IMovementActionVisitor movementActionVisitor){
		movementActionVisitor.visitComposite(this);
		for(MovementAction movementAction : actions){
			movementAction.accept(movementActionVisitor);
		}
	}
}
