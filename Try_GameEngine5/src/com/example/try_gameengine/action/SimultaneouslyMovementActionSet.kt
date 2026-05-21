package com.example.try_gameengine.action

import com.example.try_gameengine.action.visitor.IMovementActionVisitor

class SimultaneouslyMovementActionSet : MovementAction() {
    private var isActionFinish = true
    private var info: MovementActionInfo? = null

    var SimultaneouslyLock: Any = Any()

    private val cancelActions: MutableList<MovementAction> = ArrayList<MovementAction>()

    override fun addMovementAction(action: MovementAction): MovementAction {
        // TODO Auto-generated method stub
        actions.add(action)

        getCurrentActionList()
        getCurrentInfoList()

        return this
    }

    override fun setActionsTheSameTimerOnTickListener() {
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

            thread = Thread(object : Runnable {
                override fun run() {
                    val actionss = actions
                    for (action in actions) {
                        cancelAction = action
                        action.start()
                        //						synchronized (action.getAction()) {
//							try {
//								action.getAction().wait();
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
//						}
//						action.getAction().thread.join();
                    }

                    for (action in actions) {
                        try {
                            action.getAction().thread!!.join()
                        } catch (e: InterruptedException) {
                            // TODO Auto-generated catch block
                            e.printStackTrace()
                        }
                    }


//					synchronized (action.getAction()) {
//						try {
//							action.getAction().wait();
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
                    synchronized(this@SimultaneouslyMovementActionSet) {
                        (this@SimultaneouslyMovementActionSet as Object).notifyAll()
                    }
                    isActionFinish = true
                }
            })

            thread!!.start()
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

    override fun getCurrentActionList(): MutableList<MovementAction> {
        // TODO Auto-generated method stub

//		movementItemList.clear();
//		for(MovementAction action : actions){
//			for(MovementAction actionItem : action.getCurrentActionList()){
//				movementItemList.add(actionItem);
//			}
//		}

//		return movementItemList;

        return actions
    }

    override fun getCurrentInfoList(): MutableList<MovementActionInfo?> {
        // TODO Auto-generated method stub

        currentInfoList.clear()
        for (action in actions) {
            for (actionItem in action.getCurrentInfoList()) {
                currentInfoList.add(actionItem)
            }
        }

        return currentInfoList
    }

    override fun isFinish(): Boolean {
        return isActionFinish
    }

    override fun pause() {
        // TODO Auto-generated method stub
        for (action in actions) {
            action.getAction().pause()
        }
    }

    override fun accept(movementActionVisitor: IMovementActionVisitor) {
        movementActionVisitor.visitComposite(this)
        for (movementAction in actions) {
            movementAction.accept(movementActionVisitor)
        }
    }

    override fun trigger() {
        // TODO Auto-generated method stub
        for (action in this.actions) {
            action.trigger()
        }
    }
}
