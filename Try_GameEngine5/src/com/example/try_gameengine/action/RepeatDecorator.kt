package com.example.try_gameengine.action

import com.example.try_gameengine.action.MAction.MActionBlock
import com.example.try_gameengine.action.visitor.IMovementActionVisitor
import com.example.try_gameengine.action.visitor.MovementActionCreateMementoVisitor
import com.example.try_gameengine.action.visitor.MovementActionObjectStructure
import com.example.try_gameengine.action.visitor.MovementActionRestoreMementoVisitor

//import com.rits.cloning.Cloner;
/**
 * RepeatDecorator is a decorator for repeat.
 * @author irons
 // */
class RepeatDecorator(action: MovementAction, count: Long) : MovementDecorator() {
    private var count: Long
    private var isTheOuterActionForInitMovementAction = false

    /**
     * constructor.
     * @param action
     * action for repeat.
     * @param count
     * count for repeat.
     // */
    init {
        this.action = action
        this.count = count

        //		this.copyMovementActionList = action.copyMovementActionList;
        val actions: MutableList<MovementAction> = ArrayList<MovementAction>() // add 105/09/01
        actions.add(this.action)
        this.actions = actions
    }

    /**
     * coreCalculationMovementActionInfo for calculate.
     * @param action
     * info for calculate.
     * @return
     // */
    public override fun coreCalculationMovementActionInfo(
        action: MovementAction
    ): MovementAction? {
        return action
    }

    override fun start() {
        if (isTheOuterActionForInitMovementAction) {
            val movementAction: MovementAction = MovementActionSetWithThreadPool()
            movementAction.addMovementAction(createStartActionBlock())
            movementAction.start() //add 105/09/01
        } else {
//			runRepeat();
            //Change to this, because the outer action maybe not a Thread set. This RepeatDecorater need run in thread.
            val movementAction: MovementAction = MovementActionSetWithThreadPool()
            movementAction.addMovementAction(createStartActionBlock())
            movementAction.start() //add 105/09/01
        }
    }

    /**
     * 
     * @return
     // */
    private fun createStartActionBlock(): MovementAction {
        return MAction.runBlockNoDelay(object : MActionBlock {
            override fun runBlock() {
                // TODO Auto-generated method stub
                runRepeat()
            }
        })
    }

    /**
     * 
     // */
    private fun runRepeat() {
        while (count > 0 || isLoop) {
            synchronized(action.getAction()) {
                try {
                    action.getAction().start()
                    (action.getAction() as Object).wait()
                } catch (e: InterruptedException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                    //					isActionFinish =false;
                }
            }
            //			for(MovementAction movementAction : movementItemList){
//				movementAction.restoreMovementActionMemento(null);
//			}
            val objectStructure = MovementActionObjectStructure()
            objectStructure.setRoot(this)
            val movementActionVisitor: IMovementActionVisitor =
                MovementActionRestoreMementoVisitor()
            objectStructure.handleRequest(movementActionVisitor)
            count--
        }
        synchronized(this@RepeatDecorator) {
            (this@RepeatDecorator as Object).notifyAll()
        }
    }

    override fun getAction(): MovementAction {
//		return action.getAction();
        return this
    }

    override fun trigger() {
        action.trigger()
    }

    override fun getDescription(): String {
        return "Double " + action.getDescription()
    }

    override fun initMovementAction(): MovementAction? {
        val movementAction = super.initMovementAction()
        isTheOuterActionForInitMovementAction = true
        return movementAction
    }

    public override fun initTimer(): MovementAction {
        super.initTimer()
        isTheOuterActionForInitMovementAction = false

        if (this.action.getActions().size == 0) {
            action.getAction().setInfo(getInfo())
            action.getAction().initTimer()
        } else {
            this.action.initTimer()
            doIn(null)
        }


//		for(MovementAction movementAction : getCurrentActionList()){
//			movementAction.createMovementActionMemento();
//		}
        val objectStructure = MovementActionObjectStructure()
        objectStructure.setRoot(this)
        val movementActionVisitor: IMovementActionVisitor = MovementActionCreateMementoVisitor()
        objectStructure.handleRequest(movementActionVisitor)

        return this
    }

    override fun addMovementAction(action: MovementAction): MovementAction {
        this.action.addMovementAction(action)
        return this
    }

    override fun setActionsTheSameTimerOnTickListener() {
        action.setTimerOnTickListener(timerOnTickListener)
    }

    override fun setInfo(info: MovementActionInfo?) {
        // TODO Auto-generated method stub
        action.setInfo(info)
    }

    override fun getCurrentActionList(): MutableList<MovementAction> {
        // TODO Auto-generated method stub

//		movementItemList.clear();
//		for(MovementAction actionItem : action.getCurrentActionList()){
//			movementItemList.add(actionItem);
//		}
//		
//		return movementItemList;

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
//		for (MovementActionInfo info : this.getAction().currentInfoList) {
//			this.getAction().setInfo(info);
//			coreCalculationMovementActionInfo(this.getAction().getInfo());
//		}
//
//		for (MovementAction movementItem : this.getAction().movementItemList) {
//			movementItem.initTimer();
//		}

        return super.doIn(actionSet)
    }

    override fun cancelAllMove() {
        // TODO Auto-generated method stub
        count = 0
        isLoop = false

        action.getAction().cancelMove()
        synchronized(this@RepeatDecorator) {
            (this@RepeatDecorator as Object).notifyAll()
        }
    }

    public override fun cancelMove() {
        count = 0
        isLoop = false

        action.getAction().cancelMove()
        synchronized(this@RepeatDecorator) {
            (this@RepeatDecorator as Object).notifyAll()
        }
    }

    override fun pause() {
        action.getAction().pause()
    } //	@Override
    //	public IMovementActionMemento createMovementActionMemento(){
    //		movementActionMemento = new RepeatDecoratorMementoImpl(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCancelFocusAppendPart, isFinish, isLoop, isSigleThread, name, cancelAction, action, isRepeatSpriteActionIfMovementActionRepeat, count);
    //		return movementActionMemento;
    //	}
    //	
    //	/* (non-Javadoc)
    //	 * @see com.example.try_gameengine.action.MovementAction#restoreMovementActionMemento(com.example.try_gameengine.action.IMovementActionMemento)
    //	 */
    //	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
    // /**/        MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento; */ //		super.restoreMovementActionMemento(this.movementActionMemento);
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
