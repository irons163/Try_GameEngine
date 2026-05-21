package com.example.try_gameengine.action

class DoubleDecorator(action: MovementAction) : MovementDecorator() {
    init {
        this.action = action
        //		this.copyMovementActionList = action.copyMovementActionList;
    }

    public override fun coreCalculationMovementActionInfo(
        action: MovementAction
    ): MovementAction {
        val info = action.getInfo()
        info.setTotal(info.getTotal())
        info.setDelay(info.getDelay())
        info.setDx(2 * info.getDx())
        info.setDy(2 * info.getDy())
        return action
    }

    override fun start() {
        action.getAction().start()
    }

    override fun getAction(): MovementAction {
        return action.getAction()
    }

    override fun getDescription(): String {
        return "Double " + action.getDescription()
    }

    public override fun initTimer(): MovementAction {
        super.initTimer()

        if (this.getAction()!!.getActions().size == 0) {
            action.getAction().setInfo(getInfo())
            action.getAction().initTimer()
        } else { //this.getAction() is a MovementAction set or group or decorator. 
            this.getAction()!!.initTimer()
            //			doIn();
        }
        return this
    }

    override fun addMovementAction(action: MovementAction): MovementAction {
        getAction()!!.addMovementAction(action)
        return this
    }

    override fun setActionsTheSameTimerOnTickListener() {
        getAction()!!.setTimerOnTickListener(timerOnTickListener)
    }

    override fun getCurrentActionList(): MutableList<MovementAction> {
        // TODO Auto-generated method stub
        return action.getCurrentActionList()
    }

    override fun getCurrentInfoList(): MutableList<MovementActionInfo?> {
        // TODO Auto-generated method stub
        return action.getCurrentInfoList()
    }


    override fun getMovementInfoList(): MutableList<MovementActionInfo?> {
        return action.getMovementInfoList()
    }

    public override fun cancelMove() {
        action.getAction().cancelMove()
    }

    override fun pause() {
        action.getAction().pause()
    }

    //	@Override
    //	public IMovementActionMemento createMovementActionMemento(){
    //		movementActionMemento = new DoubleDecoratorMementoImpl(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCancelFocusAppendPart, isFinish, isLoop, isSigleThread, name, cancelAction, action, isRepeatSpriteActionIfMovementActionRepeat);
    //		return movementActionMemento;
    //	}
    //	
    //	@Override
    //	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
    // /**/        MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento; */ //		super.restoreMovementActionMemento(this.movementActionMemento);
    //		DoubleDecoratorMementoImpl mementoImpl = (DoubleDecoratorMementoImpl) this.movementActionMemento;
    //		this.action = mementoImpl.action;
    //	}
    //	
    //	protected static class DoubleDecoratorMementoImpl extends MovementActionMementoImpl{
    //	
    //		private MovementAction action; //Decorator
    //		
    //		public DoubleDecoratorMementoImpl(List<MovementAction> actions,
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
    @Throws(CloneNotSupportedException::class)
    public override fun clone(): DoubleDecorator {
        val copy = DoubleDecorator(this.action.clone() as MovementAction)
        copy.actionListener = this.actionListener
        copy.timerOnTickListener = this.timerOnTickListener
        copy.controller = this.controller
        copy.timerOnTickListener = this.timerOnTickListener
        for (action in this.actions) {
            val subCopy = action.clone() as MovementAction
            copy.addMovementAction(subCopy)
        }
        copy.name = name
        return copy
    }
}
