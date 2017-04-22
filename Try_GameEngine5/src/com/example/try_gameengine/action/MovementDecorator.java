package com.example.try_gameengine.action;

//import com.example.try_gameengine.action.DoubleDecorator.DoubleDecoratorMementoImpl;
import java.util.List;

import com.example.try_gameengine.action.visitor.IMovementActionVisitor;
import com.example.try_gameengine.action.visitor.MovementActionObjectStructure;

/**
 * {@code MovementDecorator} is a decorator
 * @author irons
 *
 */
public abstract class MovementDecorator extends MovementAction{
	protected MovementAction action;
	
	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementAction#getDescription()
	 */
	public abstract String getDescription();  
	
	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementAction#accept(com.example.try_gameengine.action.visitor.IMovementActionVisitor)
	 */
	public void accept(IMovementActionVisitor movementActionVisitor){
//		movementActionVisitor.visitComposite(this);
//		for(MovementAction movementAction : actions){
//			movementAction.accept(movementActionVisitor);
//		}
		
//		movementActionVisitor.visitComposite(getAction());
		
//		for(MovementAction movementAction : getAction().getActions()){
//			movementAction.accept(movementActionVisitor);
//		}
		
		action.accept(movementActionVisitor);
	}
	
	@Override
	protected MovementAction initTimer() {
		// TODO Auto-generated method stub
		return super.initTimer();
	}
	
	@Override
	protected List<MovementAction> doIn(MovementActionSet actionSet) {
		List<MovementAction> actions = action.doIn(actionSet);
//		copyMovementActionList.clear();

//		for (MovementActionInfo info : this.getAction().currentInfoList) {
//			this.getAction().setInfo(info); //set info to composite like a temp info.
//			coreCalculationMovementActionInfo(this.getAction().getInfo());
//		}
		

		
		MovementActionObjectStructure objectStructure = new MovementActionObjectStructure();
		objectStructure.setRoot(this);
		IMovementActionVisitor movementActionVisitor = new MovementActionItemVisitor(this);
		objectStructure.handleRequest(movementActionVisitor);
		
		for(MovementAction action : actions){
			objectStructure = new MovementActionObjectStructure();
			objectStructure.setRoot(action);
			movementActionVisitor = new MovementActionItemVisitor(this);
			objectStructure.handleRequest(movementActionVisitor);
		}
		
		return actions;
		
//		coreCalculationMovementActionInfo(this.getAction().getInfo());

//		for (MovementAction action : copyMovementActionList) {
//			this.getAction().addMovementAction(action);
////			this.getAction().movementItemList.add(action);
//			action.description = "copyAppend";
//			action.initTimer();
//		}

//		for (MovementAction movementItem : this.getAction().movementItemList) {
//			movementItem.initTimer();
//		}
	}
	
	protected MovementAction coreCalculationMovementActionInfo(
			MovementAction action) {
		return action;
	}

	@Override
	public MovementActionInfo getInfo() {
//		return coreCalculationMovementActionInfo(action.getInfo());
		return action.getInfo();
	}

//	@Override
//	public MovementAction initMovementAction() {
//		return initTimer();
//	}
	
//	@Override
//	public IMovementActionMemento createMovementActionMemento(){
//		movementActionMemento = new MovementDecoratorMementoImpl(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCancelFocusAppendPart, isFinish, isLoop, isSigleThread, name, cancelAction, action, isRepeatSpriteActionIfMovementActionRepeat);
//		return movementActionMemento;
//	}
//	
//	@Override
//	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
////		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento;
//		super.restoreMovementActionMemento(this.movementActionMemento);
//		MovementDecoratorMementoImpl mementoImpl = (MovementDecoratorMementoImpl) this.movementActionMemento;
//		this.action = mementoImpl.action;
//	}
//	
//	protected static class MovementDecoratorMementoImpl extends MovementActionMementoImpl{
//	
//		private MovementAction action; //Decorator
//		
//		public MovementDecoratorMementoImpl(List<MovementAction> actions,
//				Thread thread, TimerOnTickListener timerOnTickListener,
//				String description,
//				List<MovementAction> copyMovementActionList,
//				List<MovementActionInfo> currentInfoList,
//				List<MovementAction> movementItemList,
//				List<MovementAction> totalCopyMovementActionList,
//				boolean isCancelFocusAppendPart, boolean isFinish,
//				boolean isLoop, boolean isSigleThread, String name,
//				MovementAction cancelAction, MovementAction action,
//				boolean isRepeatSpriteActionIfMovementActionRepeat) {
//			super(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCancelFocusAppendPart, isFinish, isLoop, isSigleThread, name, cancelAction, isRepeatSpriteActionIfMovementActionRepeat);
//			this.action = action;
//		}
//
//		public MovementAction getAction() {
//			return action;
//		}
//
//		public void setAction(MovementAction action) {
//			this.action = action;
//		}			
//	}
}
