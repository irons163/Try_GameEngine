package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.visitor.IMovementActionVisitor;
import com.example.try_gameengine.action.visitor.MovementActionCreateMementoVisitor;
import com.example.try_gameengine.action.visitor.MovementActionObjectStructure;
import com.example.try_gameengine.action.visitor.MovementActionRestoreMementoVisitor;

//import com.rits.cloning.Cloner;

/**
 * RepeatDecorator is a decorator for repeat.
 * @author irons
 *
 */
public class RepeatDecorator extends MovementDecorator {
	private long count;
	private boolean isTheOuterActionForInitMovementAction;
	
	/**
	 * constructor.
	 * @param action
	 * 			action for repeat.
	 * @param count
	 * 			count for repeat.
	 */
	public RepeatDecorator(MovementAction action, long count) {
		this.action = action;
		this.count = count;
		this.copyMovementActionList = action.copyMovementActionList;
		
		List<MovementAction> actions = new ArrayList<MovementAction>(); // add 105/09/01
		actions.add(this.action);
		this.actions = actions; 
	}

	/**
	 * coreCalculationMovementActionInfo for calculate.
	 * @param info
	 * 			info for calculate.
	 * @return
	 */
	protected MovementActionInfo coreCalculationMovementActionInfo(
			MovementActionInfo info) {
		return info;
	}

	@Override
	public void start() {
		if(isTheOuterActionForInitMovementAction){
			MovementAction movementAction = new MovementActionSetWithThreadPool();
			movementAction.addMovementAction(createStartActionBlock());
			movementAction.start(); //add 105/09/01
		}else{
//			runRepeat();
			//Change to this, because the outer action maybe not a Thread set. This RepeatDecorater need run in thread.
			MovementAction movementAction = new MovementActionSetWithThreadPool();
			movementAction.addMovementAction(createStartActionBlock());
			movementAction.start(); //add 105/09/01
		}
			
	}
	
	/**
	 * 
	 * @return
	 */
	private MovementAction createStartActionBlock(){
		return MAction.runBlockNoDelay(new MAction.MActionBlock() {
			
			@Override
			public void runBlock() {
				// TODO Auto-generated method stub
				runRepeat();
			}
		});
	}
	
	/**
	 * 
	 */
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
//			for(MovementAction movementAction : movementItemList){
//				movementAction.restoreMovementActionMemento(null);
//			}
			MovementActionObjectStructure objectStructure = new MovementActionObjectStructure();
			objectStructure.setRoot(this);
			IMovementActionVisitor movementActionVisitor = new MovementActionRestoreMementoVisitor();
			objectStructure.handleRequest(movementActionVisitor);
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
	
	@Override
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
	protected MovementAction initTimer(){ super.initTimer();
		isTheOuterActionForInitMovementAction = false;
		
		if (this.action.getActions().size() == 0) {
			action.getAction().setInfo(getInfo());
			action.getAction().initTimer();
		} else {
			this.action.initTimer();
			doIn();
		}
		
//		for(MovementAction movementAction : getCurrentActionList()){
//			movementAction.createMovementActionMemento();
//		}
		
		MovementActionObjectStructure objectStructure = new MovementActionObjectStructure();
		objectStructure.setRoot(this);
		IMovementActionVisitor movementActionVisitor = new MovementActionCreateMementoVisitor();
		objectStructure.handleRequest(movementActionVisitor);
		
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
	public void setInfo(MovementActionInfo info) {
		// TODO Auto-generated method stub
		action.setInfo(info);
	}

	@Override
	public MovementActionInfo getInfo() {
		return coreCalculationMovementActionInfo(action.getInfo());
	}

	@Override
	public List<MovementAction> getCurrentActionList() {
		// TODO Auto-generated method stub
		
//		movementItemList.clear();
//		for(MovementAction actionItem : action.getCurrentActionList()){
//			movementItemList.add(actionItem);
//		}
//		
//		return movementItemList;
		
		return null;
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
	
//	@Override
//	public IMovementActionMemento createMovementActionMemento(){
//		movementActionMemento = new RepeatDecoratorMementoImpl(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCancelFocusAppendPart, isFinish, isLoop, isSigleThread, name, cancelAction, action, isRepeatSpriteActionIfMovementActionRepeat, count);
//		return movementActionMemento;
//	}
//	
//	/* (non-Javadoc)
//	 * @see com.example.try_gameengine.action.MovementAction#restoreMovementActionMemento(com.example.try_gameengine.action.IMovementActionMemento)
//	 */
//	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
////		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento;
//		super.restoreMovementActionMemento(this.movementActionMemento);
//		RepeatDecoratorMementoImpl mementoImpl = (RepeatDecoratorMementoImpl) this.movementActionMemento;
//		this.count = mementoImpl.count;
//	}
//	
//	protected static class RepeatDecoratorMementoImpl extends MovementDecoratorMementoImpl{
//		private long count;
//		
//		public RepeatDecoratorMementoImpl(List<MovementAction> actions,
//				Thread thread, TimerOnTickListener timerOnTickListener,
//				String description,
//				List<MovementAction> copyMovementActionList,
//				List<MovementActionInfo> currentInfoList,
//				List<MovementAction> movementItemList,
//				List<MovementAction> totalCopyMovementActionList,
//				boolean isCancelFocusAppendPart, boolean isFinish,
//				boolean isLoop, boolean isSigleThread, String name,
//				MovementAction cancelAction,
//				MovementAction action, boolean isRepeatSpriteActionIfMovementActionRepeat, long count) {
//			super(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCancelFocusAppendPart, isFinish, isLoop, isSigleThread, name, cancelAction, action, isRepeatSpriteActionIfMovementActionRepeat);
//			this.count = count;
//		}
//
//		public long getCount() {
//			return count;
//		}
//
//		public void setCount(long count) {
//			this.count = count;
//		}	
//		
//	}
}
