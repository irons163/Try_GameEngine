package com.example.try_gameengine.action;

import java.util.List;

import android.util.Log;

//import com.example.try_gameengine.action.DoubleDecorator.DoubleDecoratorMementoImpl;
import com.example.try_gameengine.action.MovementAction.MovementActionMementoImpl;
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener;
import com.example.try_gameengine.action.visitor.IMovementActionVisitor;

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
		movementActionVisitor.visitComposite(getAction());
		for(MovementAction movementAction : getAction().getActions()){
			movementAction.accept(movementActionVisitor);
		}
	}
	
	public void doIn() {
		action.doIn();
//		doing = true;
		copyMovementActionList.clear();

		int i = 0;
		for (MovementActionInfo info : this.getAction().currentInfoList) {
			Log.e("count", ++i + "");
			Log.e("info", info.getDx() + "");
			this.getAction().setInfo(info);
//			coreCalculationMovementActionInfo(this.getAction().getInfo());
		}

		for (MovementAction action : copyMovementActionList) {
			this.getAction().addMovementAction(action);
//			this.getAction().movementItemList.add(action);
			action.description = "copyAppend";
			action.initTimer();
		}

//		for (MovementAction movementItem : this.getAction().movementItemList) {
//			movementItem.initTimer();
//		}
	}
	
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
