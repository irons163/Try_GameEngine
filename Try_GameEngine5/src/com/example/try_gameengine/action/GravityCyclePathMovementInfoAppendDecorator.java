package com.example.try_gameengine.action;

import java.util.List;

import com.example.try_gameengine.action.visitor.IMovementActionVisitor;
//import com.example.try_gameengine.action.visitor.MovementActionItemVisitor;
import com.example.try_gameengine.action.visitor.MovementActionObjectStructure;

import android.util.Log;

public class GravityCyclePathMovementInfoAppendDecorator extends
		MovementDecorator {
	private MovementAction action;

	public GravityCyclePathMovementInfoAppendDecorator(MovementActionItemMoveByCurve action) {
		this.action = action;
//		this.copyMovementActionList = action.copyMovementActionList;
	}

//	private MovementActionInfo coreCalculationMovementActionInfo(
//			MovementActionInfo info) {
//
//		MovementActionInfo newInfo = new MovementActionInfo(info.getTotal(),
//				info.getDelay(), info.getDx(), info.getDy(),
//				info.getDescription());
//		if (this.getAction().getActions().size() != 0) {
//			MovementAction action = new MovementActionItemCountDownTimer(newInfo);
//			copyMovementActionList.add(action);
//			this.getAction().totalCopyMovementActionList.add(action);
//		}
//
//		newInfo.setTotal(info.getTotal());
//		newInfo.setDelay(info.getDelay());
//		newInfo.setDx(info.getDx());
//		newInfo.setDy(info.getDy());
//
//		if (newInfo.getGravityController() != null) {
//			newInfo.setRotationController(info.getRotationController()
//					.copyNewRotationController());
//			newInfo.getRotationController().setRotation(
//					info.getRotationController().getRotation() + 180);
//		}
//		if (newInfo.getGravityController() != null) {
//			MathUtil mathUtil = info.getGravityController().getMathUtil();
//			newInfo.getGravityController().setMathUtil(mathUtil);
//			newInfo.getGravityController().isCyclePath();
//		}
//		return info;
//	}
//	
//	private MovementActionItem coreCalculationMovementActionItem(
//			MovementActionItem item) {
//
//		MovementActionItemMoveByCurve newItem = (MovementActionItemMoveByCurve) item.clone();
//		newItem.getGravityController().isCyclePath();
//		newItem
//		
//		return newItem;
//	}

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
	protected MovementAction initTimer(){ super.initTimer();

		if (this.getAction().getActions().size() == 0) {

			action.getAction().setInfo(getInfo());
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
	public List<MovementActionInfo> getMovementInfoList() {
		return action.getMovementInfoList();
	}

	@Override
	protected void doIn() {
		action.doIn();
//		copyMovementActionList.clear();
		this.getAction().getCurrentInfoList();

		for (int j = 0; j < this.getAction().currentInfoList.size(); j++) {
			MovementActionInfo info = this.getAction().currentInfoList.get(j);
			Log.e("info", info.getDx() + "");
			this.getAction().setInfo(info);
//			coreCalculationMovementActionInfo(this.getAction().getInfo());
			
		}
		
//		MovementActionObjectStructure objectStructure = new MovementActionObjectStructure();
//		objectStructure.setRoot(this);
//		IMovementActionVisitor movementActionVisitor = new MovementActionItemVisitor(this);
//		objectStructure.handleRequest(movementActionVisitor);

//		for (MovementAction action : copyMovementActionList) {
//			this.getAction().addMovementAction(action);
////			this.getAction().movementItemList.add(action);
//			action.description = "inverseAppend";
//			action.initTimer();
//		}

//		for (MovementAction movementItem : this.getAction().movementItemList) {
//			movementItem.initTimer();
//		}

	}
	
//	@Override
//	public IMovementActionMemento createMovementActionMemento(){
//		movementActionMemento = new GravityCyclePathMovementInfoAppendDecoratorMementoImpl(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCancelFocusAppendPart, isFinish, isLoop, isSigleThread, name, cancelAction, action, isRepeatSpriteActionIfMovementActionRepeat);
//		return movementActionMemento;
//	}
//	
//	@Override
//	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
////		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento;
//		super.restoreMovementActionMemento(this.movementActionMemento);
//		GravityCyclePathMovementInfoAppendDecoratorMementoImpl mementoImpl = (GravityCyclePathMovementInfoAppendDecoratorMementoImpl) this.movementActionMemento;
//		this.action = mementoImpl.action;
//	}
//	
//	protected static class GravityCyclePathMovementInfoAppendDecoratorMementoImpl extends MovementActionMementoImpl{
//	
//		private MovementAction action; //Decorator
//		
//		public GravityCyclePathMovementInfoAppendDecoratorMementoImpl(List<MovementAction> actions,
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
