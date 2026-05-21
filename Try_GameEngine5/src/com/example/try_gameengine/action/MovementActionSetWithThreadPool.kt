package com.example.try_gameengine.action

import android.util.Log
import com.example.try_gameengine.action.visitor.IMovementActionVisitor
import java.util.concurrent.Future

class MovementActionSetWithThreadPool : MovementActionSet() {
    protected override var isActionFinish = false
    private var info: MovementActionInfo? = null
    var isStop: Boolean = false
    var future: Future<*>? = null

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

    override fun start() {
        // TODO Auto-generated method stub

        if (!isStop) {
//			isActionFinish = false;

            Log.e("MovementActionSetWithThreadPool", "[MovementAction]:action start")

            future = MovementAction.Companion.executor!!.submit(object : Runnable {
                override fun run() {
                    // TODO Auto-generated method stub

                    Log.e("MovementActionSetWithThreadPool", "[MovementAction]:future start")

                    val actionss = actions
                    actionListener.actionStart()

                    do {
                        if (!isStop) {
                            Log.e(
                                "MovementActionSetWithThreadPool",
                                "[MovementAction]:future start2"
                            )


//						isActionFinish = false;
                            for (action in actions) {
                                if (isStop) {
                                    isLoop = false
                                    break
                                }
                                cancelAction = action


                                if (!isStop) synchronized(action.getAction()) {
                                    action.start()
                                    Log.e(
                                        "MovementActionSetWithThreadPool",
                                        "[MovementAction]:child action start"
                                    )
                                    try {
                                        (action.getAction() as Object).wait()
                                    } catch (e: InterruptedException) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace()
                                        Log.e(
                                            "MovementActionThreadPool",
                                            "ThreadPoolChildThreadInterrupt"
                                        )
                                    }
                                }

                                if (isStop) break


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
                            actionListener.actionCycleFinish()
                        }
                    } while (isLoop)

                    synchronized(this@MovementActionSetWithThreadPool) {
                        (this@MovementActionSetWithThreadPool as Object).notifyAll()
                    }
                    isActionFinish = true
                    actionListener.actionFinish()
                }
            })


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
        return info ?: MovementActionInfo(0, 0, 0f, 0f)
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
//		// TODO Auto-generated method stub
//		
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
        cancelAction?.trigger()
    }

    public override fun cancelAllMove() {
        // TODO Auto-generated method stub
        isStop = true
        isLoop = false
        future!!.cancel(true)


//		executor.shutdown();
        super.cancelAllMove()
    }

    override fun cancelMove() {
        // TODO Auto-generated method stub
        isStop = true
        isLoop = false
        future!!.cancel(true)
        //		((Thread)future).interrupt();
//		executor.shutdown();
//		super.cancelMove();
        super.cancelAllMove()
    }

    //	public IMovementActionMemento createMovementActionMemento(){
    //		movementActionMemento = new MovementActionSetWithThreadPoolMementoImpl(actions, thread, timerOnTickListener, description, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isActionFinish, isActionFinish, isActionFinish, isActionFinish, name, cancelAction, isActionFinish, info, isStop, future, isRepeatSpriteActionIfMovementActionRepeat);
    //		return movementActionMemento;
    //	}
    //	
    //	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
    // /**/        MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento; */ //		super.restoreMovementActionMemento(this.movementActionMemento);
    //		MovementActionSetWithThreadPoolMementoImpl mementoImpl = (MovementActionSetWithThreadPoolMementoImpl) this.movementActionMemento;
    //		this.isActionFinish = mementoImpl.isActionFinish;
    //	}
    //	
    //	protected static class MovementActionSetWithThreadPoolMementoImpl extends MovementActionMementoImpl{
    //	
    //		private boolean isActionFinish;
    //		private MovementActionInfo info;
    //		private boolean isStop;
    //		private Future future;
    //		
    //		public MovementActionSetWithThreadPoolMementoImpl(List<MovementAction> actions,
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
    //				boolean isStop, Future future, boolean isRepeatSpriteActionIfMovementActionRepeat) {
    //			super(actions, thread, timerOnTickListener, description,
    //					copyMovementActionList, currentInfoList, movementItemList,
    //					totalCopyMovementActionList, isCancelFocusAppendPart,
    //					isFinish, isLoop, isSigleThread, name, cancelAction, isRepeatSpriteActionIfMovementActionRepeat);
    //			this.isActionFinish = isActionFinish;
    //			this.info = info;
    //			this.isStop = isStop;
    //			this.future = future;
    //		}
    //			
    //	}
    //	
    public override fun accept(movementActionVisitor: IMovementActionVisitor) {
        movementActionVisitor.visitComposite(this)
        for (movementAction in actions) {
            movementAction.accept(movementActionVisitor)
        }
    }
}
