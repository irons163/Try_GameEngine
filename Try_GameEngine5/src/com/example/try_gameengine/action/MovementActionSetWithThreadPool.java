package com.example.try_gameengine.action;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;

import com.example.try_gameengine.action.CopyMoveDecorator.CopyMoveDecoratorMementoImpl;
import com.example.try_gameengine.action.MovementAction.MovementActionMementoImpl;
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener;
import com.example.try_gameengine.action.visitor.IMovementActionVisitor;

import android.R.bool;
import android.util.Log;

public class MovementActionSetWithThreadPool extends MovementAction {
	private boolean isActionFinish = true;
	private MovementActionInfo info;
	public boolean isStop = false;
	Future future;
	
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
//			isActionFinish = false;
			
			Log.e("MovementActionSetWithThreadPool", "[MovementAction]:action start");
			
			future = executor.submit(new Runnable() {
				@Override
				public void run(){
					// TODO Auto-generated method stub
					
					Log.e("MovementActionSetWithThreadPool", "[MovementAction]:future start");
					
					List<MovementAction> actionss = actions;
					actionListener.actionStart();
					
					do{
						if(isActionFinish){
							
							Log.e("MovementActionSetWithThreadPool", "[MovementAction]:future start2");
							
						isActionFinish = false;
						for(MovementAction action : actions){
							if(isStop){
								isLoop = false;
								break;
							}
							cancelAction = action;
							
							
							if(!isStop)
							synchronized (action.getAction()) {
								action.start();
								Log.e("MovementActionSetWithThreadPool", "[MovementAction]:child action start");
								try {
									action.getAction().wait();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									Log.e("MovementActionThreadPool", "ThreadPoolChildThreadInterrupt");
								}
							}
							
							if(isStop)
								break;
	
//							if(!isStop)
//								isLoop = false;
//							try {
//								Thread.sleep(50000000);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//								Thread.interrupted();
//								Log.e("MovementActionThreadPool", "ThreadPoolChildThreadInterrupt");
//								return;
//							}
							
						}
						actionListener.actionCycleFinish();
						
						}
					}while(isLoop);
					
					synchronized (MovementActionSetWithThreadPool.this) {
						MovementActionSetWithThreadPool.this.notifyAll();
					}
					isActionFinish = true;
					actionListener.actionFinish();
				}
			});
			
//			synchronized (MovementActionSetWithThreadPool.this.getAction()) {
//				try {
//					MovementActionSetWithThreadPool.this.getAction().wait();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					Log.e("MovementActionThreadPool", "ThreadPoolChildThreadInterrupt");
//				}
//			}
			
		}
	}
	
	@Override
	public MovementAction initMovementAction(){		
		return initTimer();
	}
	
	@Override
	protected MovementAction initTimer() {
	
		for (MovementAction action : this.actions) {
			
			if(action.getAction().getActions().size()==0){
				action.initTimer();
			}else{
				action.initTimer();
			}
			for(MovementAction movementAction : action.getAction().totalCopyMovementActionList){
				this.getAction().movementItemList.add(movementAction);
			}
			
			action.getAction().setCancelFocusAppendPart(true);
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
		for (MovementAction action : this.actions) {
			action.trigger();
		}
	}
	
	@Override
	void cancelAllMove() {
		// TODO Auto-generated method stub
		isStop = true;
		isLoop = false;
		future.cancel(true);
		
//		executor.shutdown();
		super.cancelAllMove();
	}
	
	@Override
	void cancelMove() {
		// TODO Auto-generated method stub
		isStop = true;
		isLoop = false;
		future.cancel(true);
//		((Thread)future).interrupt();
//		executor.shutdown();
//		super.cancelMove();
		super.cancelAllMove();
	}
	
	public IMovementActionMemento createMovementActionMemento(){
		movementActionMemento = new MovementActionSetWithThreadPoolMementoImpl(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isActionFinish, isActionFinish, isActionFinish, isActionFinish, name, cancelAction, isActionFinish, info, isStop, future);
		return movementActionMemento;
	}
	
	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
//		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento;
		super.restoreMovementActionMemento(this.movementActionMemento);
		MovementActionSetWithThreadPoolMementoImpl mementoImpl = (MovementActionSetWithThreadPoolMementoImpl) this.movementActionMemento;
		this.isActionFinish = mementoImpl.isActionFinish;
	}
	
	protected static class MovementActionSetWithThreadPoolMementoImpl extends MovementActionMementoImpl{
	
		private boolean isActionFinish;
		private MovementActionInfo info;
		private boolean isStop;
		private Future future;
		
		public MovementActionSetWithThreadPoolMementoImpl(List<MovementAction> actions,
				Thread thread, TimerOnTickListener timerOnTickListener,
				String description,
				List<MovementAction> copyMovementActionList,
				List<MovementActionInfo> currentInfoList,
				List<MovementAction> movementItemList,
				List<MovementAction> totalCopyMovementActionList,
				boolean isCancelFocusAppendPart, boolean isFinish,
				boolean isLoop, boolean isSigleThread, String name,
				MovementAction cancelAction,
				boolean isActionFinish, MovementActionInfo info,
				boolean isStop, Future future) {
			super(actions, thread, timerOnTickListener, description,
					copyMovementActionList, currentInfoList, movementItemList,
					totalCopyMovementActionList, isCancelFocusAppendPart,
					isFinish, isLoop, isSigleThread, name, cancelAction);
			this.isActionFinish = isActionFinish;
			this.info = info;
			this.isStop = isStop;
			this.future = future;
		}
			
	}
	
	@Override
	public void accept(IMovementActionVisitor movementActionVisitor){
		movementActionVisitor.visitComposite(this);
		for(MovementAction movementAction : actions){
			movementAction.accept(movementActionVisitor);
		}
	}
}
