package com.example.try_gameengine.action

import android.util.Log
import com.example.try_gameengine.action.visitor.IMovementActionVisitor

//import com.rits.cloning.Cloner;
class MovementActionSetGroupWithOutThread : MovementActionSet() {
    protected override var isActionFinish = true
    private var info: MovementActionInfo? = null
    var isStop: Boolean = false
    private val actionIndex = 0

    public override fun addMovementAction(action: MovementAction): MovementAction {
        // TODO Auto-generated method stub
        actions.add(action)

        getCurrentActionList()
        getCurrentInfoList()

        return this
    }

    protected override fun setActionsTheSameTimerOnTickListener() {
        for (action in actions) {
            action.getAction().setTimerOnTickListener(timerOnTickListener)
        }
    }

    private fun frameStart() {
        for (action in actions) {
            cancelAction = action

            action.start()
        }
    }

    override fun start() {
        // TODO Auto-generated method stub

        if (isActionFinish) {
            isActionFinish = false
            actionListener.actionStart()
            if (actions.size > 0) {
                for (action in actions) {
//					cancelAction = action;
                    action.start()
                }
            } else {
                isActionFinish = true
                actionListener.actionFinish()
            }


//			isActionFinish = false;

//					actionListener.actionStart();
//					do{
//						for(MovementAction action : actions){
//							if(isStop){
//								isLoop = false;
//								break;
//							}
//							cancelAction = action;
//							action.start();	
//						}
//						actionListener.actionCycleFinish();
//					}while(isLoop);

//					isActionFinish = true;
//					actionListener.actionFinish();
        }
    }

    public override fun initTimer(): MovementAction {
        super.initTimer()

        for (action in this.actions) {
            if (action.getAction().getActions().size == 0) {
                action.initTimer()
            } else {
                action.initTimer()
            }

            //			for(MovementAction movementAction : action.getAction().totalCopyMovementActionList){
//				this.getAction().movementItemList.add(movementAction);
//			}

//			action.getAction().setCancelFocusAppendPart(true);
        }
        this.getAction().getCurrentInfoList()

        return this
    }

    override fun getAction(): MovementAction {
        return this
    }

    override fun getActions(): MutableList<MovementAction> {
        return actions
    }

    override fun getInfo(): MovementActionInfo {
        // TODO Auto-generated method stub
        return info!!
    }

    override fun setInfo(info: MovementActionInfo?) {
        this.info = info ?: return
    }

    override fun getDescription(): String {
        // TODO Auto-generated method stub
        description = "Set["
        for (action in actions) {
            description += action.getDescription()
        }
        description += "]"
        return description ?: ""
    }

    public override fun getCurrentActionList(): MutableList<MovementAction> {
        // TODO Auto-generated method stub

//		movementItemList.clear();
//		for(MovementAction action : actions){
//			for(MovementAction actionItem : action.getCurrentActionList()){
//				movementItemList.add(actionItem);
//			}
//		}
//		
//		return movementItemList;

        return actions
    }

    public override fun getCurrentInfoList(): MutableList<MovementActionInfo?> {
        // TODO Auto-generated method stub

        currentInfoList.clear()
        for (action in actions) {
            for (actionItem in action.getCurrentInfoList()) {
                currentInfoList.add(actionItem)
            }
        }

        return currentInfoList
    }

    public override fun isFinish(): Boolean {
        return isActionFinish
    }

    override fun trigger() {
        // TODO Auto-generated method stub
//		for (MovementAction action : this.actions) {
//			action.trigger();
//		}
        if (isActionFinish) return

        var isAllFinish = true
        for (action in actions) {
            action.trigger()
        }
        for (action in actions) {
            if (!action.isFinish()) {
                isAllFinish = false
                break
            }
        }
        if (isAllFinish) {
            isActionFinish = true
            actionListener.actionFinish()
        }
    }

    public override fun cancelAllMove() {
        if (this.getAction().actions.size != 0) {
            for (action in this.getAction().actions) {
                action.cancelMove()
                Log.e("action", "cancel")
            }
            //			this.thread.interrupt();
        }
        cancelMove()
    }

    override fun cancelMove() {
        isStop = true
        isActionFinish = true


//		if(!isSigleThread)
//			this.thread.interrupt();
    }

    //	public IMovementActionMemento createMovementActionMemento(){
    //		movementActionMemento = new MovementActionSetGroupWithOutThreadMementoImpl(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isActionFinish, isActionFinish, isActionFinish, isActionFinish, name, cancelAction, isActionFinish, info, isStop, actionIndex, isRepeatSpriteActionIfMovementActionRepeat);
    //		return movementActionMemento;
    //	}
    //	
    //	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
    // /**/        MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento; */ //		super.restoreMovementActionMemento(this.movementActionMemento);
    //		MovementActionSetGroupWithOutThreadMementoImpl mementoImpl = (MovementActionSetGroupWithOutThreadMementoImpl) this.movementActionMemento;
    //		this.isActionFinish = mementoImpl.isActionFinish;
    //		this.info = mementoImpl.info;
    //		this.isStop = mementoImpl.isStop;
    //		this.actionIndex = mementoImpl.actionIndex;
    //	}
    //	
    //	protected static class MovementActionSetGroupWithOutThreadMementoImpl extends MovementActionMementoImpl{
    //	
    //		private boolean isActionFinish;
    //		private MovementActionInfo info;
    //		private boolean isStop;
    //		private int actionIndex;
    //		
    //		public MovementActionSetGroupWithOutThreadMementoImpl(List<MovementAction> actions,
    //				Thread thread, TimerOnTickListener timerOnTickListener,
    //				String description,
    //				List<MovementAction> copyMovementActionList,
    //				List<MovementActionInfo> currentInfoList,
    //				List<MovementAction> movementItemList,
    //				List<MovementAction> totalCopyMovementActionList,
    //				boolean isCancelFocusAppendPart, boolean isFinish,
    //				boolean isLoop, boolean isSigleThread, String name,
    //				MovementAction cancelAction,
    //				boolean isActionFinish, MovementActionInfo info,
    //				boolean isStop, int actionIndex, boolean isRepeatSpriteActionIfMovementActionRepeat) {
    //			super(actions, thread, timerOnTickListener, description,
    //					copyMovementActionList, currentInfoList, movementItemList,
    //					totalCopyMovementActionList, isCancelFocusAppendPart,
    //					isFinish, isLoop, isSigleThread, name, cancelAction, isRepeatSpriteActionIfMovementActionRepeat);
    //			this.isActionFinish = isActionFinish;
    //			this.info = info;
    //			this.isStop = isStop;
    //			this.actionIndex = actionIndex;
    //		}
    //			
    //	}
    public override fun accept(movementActionVisitor: IMovementActionVisitor) {
        movementActionVisitor.visitComposite(this)
        for (movementAction in actions) {
            movementAction.accept(movementActionVisitor)
        }
    }
}
