package com.example.try_gameengine.action

import com.example.try_gameengine.action.visitor.IMovementActionVisitor
import com.example.try_gameengine.action.visitor.MovementActionObjectStructure

//import com.example.try_gameengine.action.DoubleDecorator.DoubleDecoratorMementoImpl;

/**
 * `MovementDecorator` is a decorator
 * @author irons
 // */
abstract class MovementDecorator : MovementAction() {
    @get:kotlin.jvm.JvmName("getDecoratedAction")
    @set:kotlin.jvm.JvmName("setDecoratedAction")
    protected lateinit var action: MovementAction

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementAction#getDescription()
	 // */
    abstract override fun getDescription(): String?

    /* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementAction#accept(com.example.try_gameengine.action.visitor.IMovementActionVisitor)
	 // */
    override fun accept(movementActionVisitor: IMovementActionVisitor) {
//		movementActionVisitor.visitComposite(this);
//		for(MovementAction movementAction : actions){
//			movementAction.accept(movementActionVisitor);
//		}

//		movementActionVisitor.visitComposite(getAction());

//		for(MovementAction movementAction : getAction().getActions()){
//			movementAction.accept(movementActionVisitor);
//		}

        action.accept(movementActionVisitor)
    }

    public override fun initTimer(): MovementAction? {
        // TODO Auto-generated method stub
        return super.initTimer()
    }

    public override fun doIn(actionSet: MovementActionSet?): MutableList<MovementAction> {
        val actions = action.doIn(actionSet)


        //		copyMovementActionList.clear();

//		for (MovementActionInfo info : this.getAction().currentInfoList) {
//			this.getAction().setInfo(info); //set info to composite like a temp info.
//			coreCalculationMovementActionInfo(this.getAction().getInfo());
//		}
        var objectStructure = MovementActionObjectStructure()
        objectStructure.setRoot(this)
        var movementActionVisitor: IMovementActionVisitor = MovementActionItemVisitor(this)
        objectStructure.handleRequest(movementActionVisitor)

        for (action in actions) {
            objectStructure = MovementActionObjectStructure()
            objectStructure.setRoot(action)
            movementActionVisitor = MovementActionItemVisitor(this)
            objectStructure.handleRequest(movementActionVisitor)
        }

        return actions


//		coreCalculationMovementActionInfo(this.getAction().getInfo());

//		for (MovementAction action : copyMovementActionList) {
//			this.getAction().addMovementAction(action);
        // /**/            this.getAction().movementItemList.add(action); */
//			action.description = "copyAppend";
//			action.initTimer();
//		}

//		for (MovementAction movementItem : this.getAction().movementItemList) {
//			movementItem.initTimer();
//		}
    }

    open fun coreCalculationMovementActionInfo(
        action: MovementAction
    ): MovementAction? {
        return action
    }

    override fun getInfo(): MovementActionInfo {
//		return coreCalculationMovementActionInfo(action.getInfo());
        return action.getInfo()
    } //	@Override

    override fun setInfo(info: MovementActionInfo?) {
        action.setInfo(info)
    }
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
    // /**/        MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento; */ //		super.restoreMovementActionMemento(this.movementActionMemento);
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
