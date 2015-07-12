package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.CopyMoveDecorator.CopyMoveDecoratorMementoImpl;
import com.example.try_gameengine.action.MovementAction.MovementActionMementoImpl;
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener;

//import com.rits.cloning.Cloner;

import android.util.Log;

public class RepeatDecorator extends MovementDecorator {
	
	private long count;
	private boolean isTheOuterActionForInitMovementAction;
	
	public RepeatDecorator(MovementAction action, long count) {
		this.action = action;
		this.count = count;
		allMovementActoinList.add(this);
		this.copyMovementActionList = action.copyMovementActionList;
		
//		getCurrentInfoList();
	}

	private MovementActionInfo coreCalculationMovementActionInfo(
			MovementActionInfo info) {
		return info;
	}

	@Override
	public void start() {
		if(isTheOuterActionForInitMovementAction){
			MovementAction movementAction = new MovementActionSetWithThreadPool();
			movementAction.addMovementAction(createStartActionBlock());
		}else{
			runRepeat();
		}
			
	}
	
	private MovementAction createStartActionBlock(){
		return MAction.runBlockNoDelay(new MAction.MActionBlock() {
			
			@Override
			public void runBlock() {
				// TODO Auto-generated method stub
				runRepeat();
			}
		});
	}
	
	private void runRepeat(){
		while(count>0 || isLoop){
			
			synchronized (action.getAction()) {
				try {		
					action.getAction().start();
					action.getAction().wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
//					isActionFinish =false;
				}
			}
			for(MovementAction movementAction : movementItemList){
				movementAction.restoreMovementActionMemento(null);
			}
			count--;
		}
		synchronized (RepeatDecorator.this) {
			RepeatDecorator.this.notifyAll();
		}
	}

	@Override
	public MovementAction getAction() {
//		return action.getAction();
		return this;
	}

	public void trigger(){
		action.trigger();
	}
	
	@Override
	public String getDescription() {
		return "Double " + action.getDescription();
	}

	@Override
	public MovementAction initMovementAction() {
		MovementAction movementAction = initTimer();
		isTheOuterActionForInitMovementAction = true;
		return movementAction;
	}

	@Override
	protected MovementAction initTimer() {
		isTheOuterActionForInitMovementAction = false;
		
		if (this.action.getActions().size() == 0) {
			action.getAction().setInfo(getInfo());
			action.getAction().initTimer();
		} else {
			this.action.initTimer();
			doIn();
		}
		
		for(MovementAction movementAction : getCurrentActionList()){
			movementAction.createMovementActionMemento();
		}
		
		return this;
	}

	@Override
	public MovementAction addMovementAction(MovementAction action) {
		this.action.addMovementAction(action);
		return this;
	}

	@Override
	protected void setActionsTheSameTimerOnTickListener() {
		action.setTimerOnTickListener(timerOnTickListener);
	}

	@Override
	public MovementActionInfo getInfo() {
		return coreCalculationMovementActionInfo(action.getInfo());
	}

	@Override
	public List<MovementAction> getCurrentActionList() {
		// TODO Auto-generated method stub
		
		movementItemList.clear();
		for(MovementAction actionItem : action.getCurrentActionList()){
			movementItemList.add(actionItem);
		}
		
		return movementItemList;
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
//		for (MovementActionInfo info : this.getAction().currentInfoList) {
//			this.getAction().setInfo(info);
//			coreCalculationMovementActionInfo(this.getAction().getInfo());
//		}
//
//		for (MovementAction movementItem : this.getAction().movementItemList) {
//			movementItem.initTimer();
//		}
		
		
	}
	
	@Override
	void cancelAllMove() {
		// TODO Auto-generated method stub
		count = 0;
		isLoop = false;
		
		action.getAction().cancelMove();
		synchronized (RepeatDecorator.this) {
			RepeatDecorator.this.notifyAll();
		}
	}

	@Override
	public void cancelMove() {
		count = 0;
		isLoop = false;
		
		action.getAction().cancelMove();
		synchronized (RepeatDecorator.this) {
			RepeatDecorator.this.notifyAll();
		}
	}

	@Override
	void pause() {
		action.getAction().pause();
	}
	
	public IMovementActionMemento createMovementActionMemento(){
		movementActionMemento = new RepeatDecoratorMementoImpl(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCancelFocusAppendPart, isFinish, isLoop, isSigleThread, name, cancelAction, allMovementActoinList, action, count);
		return movementActionMemento;
	}
	
	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
//		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento;
		super.restoreMovementActionMemento(this.movementActionMemento);
		RepeatDecoratorMementoImpl mementoImpl = (RepeatDecoratorMementoImpl) this.movementActionMemento;
		this.action = mementoImpl.action;
	}
	
	protected static class RepeatDecoratorMementoImpl extends MovementActionMementoImpl{
	
		private MovementAction action; //Decorator
		private long count;
		
		public RepeatDecoratorMementoImpl(List<MovementAction> actions,
				Thread thread, TimerOnTickListener timerOnTickListener,
				String description,
				List<MovementAction> copyMovementActionList,
				List<MovementActionInfo> currentInfoList,
				List<MovementAction> movementItemList,
				List<MovementAction> totalCopyMovementActionList,
				boolean isCancelFocusAppendPart, boolean isFinish,
				boolean isLoop, boolean isSigleThread, String name,
				MovementAction cancelAction, List<MovementAction> allMovementActoinList,
				MovementAction action, long count) {
			super(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCancelFocusAppendPart, isFinish, isLoop, isSigleThread, name, cancelAction, allMovementActoinList);
			this.action = action;
			this.count = count;
		}

		public MovementAction getAction() {
			return action;
		}

		public void setAction(MovementAction action) {
			this.action = action;
		}

		public long getCount() {
			return count;
		}

		public void setCount(long count) {
			this.count = count;
		}	
		
	}
}
