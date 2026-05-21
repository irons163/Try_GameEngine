package com.example.try_gameengine.action

/**
 * MovementActionSet is a set of MovementAcion.
 * @author irons
 // */
class MovementActionSetWithThread : MovementActionSet() {
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
                    // TODO Auto-generated method stub
                    val actionss = actions
                    actionListener.actionStart()
                    do {
                        for (action in actions) {
                            cancelAction = action
                            action.start()
                            synchronized(action.getAction()) {
                                try {
                                    (action.getAction() as Object).wait()
                                } catch (e: InterruptedException) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace()
                                    //									throw new RuntimeException();
                                    Thread.currentThread().interrupt()
                                }
                            }
                        }
                        actionListener.actionCycleFinish()
                    } while (isLoop)

                    synchronized(this@MovementActionSetWithThread) {
                        (this@MovementActionSetWithThread as Object).notifyAll()
                    }
                    isActionFinish = true
                    actionListener.actionFinish()
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
        return movementInfoList.firstOrNull() ?: MovementActionInfo(0, 0, 0f, 0f)
    }

    override fun setInfo(info: MovementActionInfo?) {
//		this.info = info ?: return;
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

    override fun trigger() {
        // TODO Auto-generated method stub
        for (action in this.actions) {
            action.trigger()
        }
    }

    @Throws(CloneNotSupportedException::class)
    public override fun clone(): MovementActionSetWithThread {
        val copy = MovementActionSetWithThread()
        copy.actionListener = this.actionListener
        copy.timerOnTickListener = this.timerOnTickListener
        copy.controller = this.controller
        copy.timerOnTickListener = this.timerOnTickListener
        for (action in this.actions) {
            val subCopy = action.clone() as MovementAction
            copy.addMovementAction(subCopy)
        }
        return copy
    }
}
