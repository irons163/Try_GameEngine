package com.example.try_gameengine.action

open class CopyMoveDecorator(action: MovementAction) : MovementDecorator() {
    //	boolean doing = false;
    //	MovementActionSet actionSet;
    init {
        this.action = action
        //		this.actionSet = actionSet;
//		this.copyMovementActionList = action.copyMovementActionList;
    }

    public override fun coreCalculationMovementActionInfo(
        action: MovementAction
    ): MovementAction? {
        var newAction: MovementAction? = null
        try {
            newAction = action.clone() as MovementAction
        } catch (e: CloneNotSupportedException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        //		if (this.getAction().getActions().size() != 0) {
//			MovementAction action = new MovementActionItemCountDownTimer(newInfo);
//			copyMovementActionList.add(action);
//			this.getAction().totalCopyMovementActionList.add(action);
//		}

//		this.action.addMovementAction(newAction);
        return newAction
    }

    override fun start() {
        action.getAction().start()
    }

    override fun getAction(): MovementAction {
        return action.getAction()
    }

    override fun getDescription(): String {
        return "Copy " + action.getDescription()
    }

    public override fun initTimer(): MovementAction {
        super.initTimer()

        if (this.getAction()!!.getActions().size == 0) {
            val info = action.getInfo()
            action.getAction().setInfo(info)
            action.getAction().initTimer()
        } else {
            this.getAction()!!.initTimer()
            //			doIn(null);
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

    public override fun doIn(actionSet: MovementActionSet?): MutableList<MovementAction> {
        val baseAction = action!!
        val actions = baseAction.doIn(actionSet)

        val newactions: MutableList<MovementAction> = ArrayList<MovementAction>(actions)
        if (actionSet != null) {
//			actionSet.addMovementAction(coreCalculationMovementActionInfo(action));
            coreCalculationMovementActionInfo(baseAction)?.let { newactions.add(it) }
        }

        for (action in actions) {
            coreCalculationMovementActionInfo(action!!)?.let { newactions.add(it) }
        }
        //		for(MovementAction action : this.getAction().getActions()){
//			coreCalculationMovementActionInfo(action);
//		}
        return newactions
    }

    //	public IMovementActionMemento createMovementActionMemento(){
    //		movementActionMemento = new CopyMoveDecoratorMementoImpl(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCancelFocusAppendPart, isFinish, isLoop, isSigleThread, name, cancelAction, action, isRepeatSpriteActionIfMovementActionRepeat);
    //		return movementActionMemento;
    //	}
    //	
    //	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
    // /**/        MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento; */ //		super.restoreMovementActionMemento(this.movementActionMemento);
    //		CopyMoveDecoratorMementoImpl mementoImpl = (CopyMoveDecoratorMementoImpl) this.movementActionMemento;
    //		this.action = mementoImpl.action;
    //	}
    //	
    //	protected static class CopyMoveDecoratorMementoImpl extends MovementActionMementoImpl{
    //	
    //		private MovementAction action; //Decorator
    //		
    //		public CopyMoveDecoratorMementoImpl(List<MovementAction> actions,
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
    public override fun clone(): CopyMoveDecorator {
        val copy = CopyMoveDecorator(this.action.clone() as MovementActionSet)
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
