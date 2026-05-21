package com.example.try_gameengine.action

import com.example.try_gameengine.action.listener.IActionListener

//import com.rits.cloning.Cloner;
class PauseInEndingDecorator(action: MovementAction) : MovementDecorator() {
    init {
        this.action = action
        //		this.copyMovementActionList = action.copyMovementActionList;
    }

    public override fun coreCalculationMovementActionInfo(
        action: MovementAction
    ): MovementAction? {
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
        } else {
            this.getAction()!!.initTimer()
            doIn(null)
        }

        setPauseInEnding()
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

    override fun setActionListener(actionListener: IActionListener?) {
        // TODO Auto-generated method stub
        super.setActionListener(actionListener)
    }

    private fun setPauseInEnding() {
        val actionListener = action.getAction().getActionListener()
        if (actionListener != null) {
            val newActionListener: IActionListener = object : IActionListener {
                override fun beforeChangeFrame(nextFrameId: Int) {
                    // TODO Auto-generated method stub
                    actionListener.beforeChangeFrame(nextFrameId)
                }

                override fun afterChangeFrame(periousFrameId: Int) {
                    // TODO Auto-generated method stub
                    actionListener.afterChangeFrame(periousFrameId)
                }

                override fun actionStart() {
                    // TODO Auto-generated method stub
                    actionListener.actionStart()
                }

                override fun actionFinish() {
                    // TODO Auto-generated method stub
                    actionListener.actionFinish()
                }

                override fun actionCycleFinish() {
                    // TODO Auto-generated method stub
                    actionListener.actionCycleFinish()
                    action.getAction().pause()
                }
            }

            action.getAction().setActionListener(newActionListener)
        } else {
            val newActionListener: IActionListener = object : IActionListener {
                override fun beforeChangeFrame(nextFrameId: Int) {
                    // TODO Auto-generated method stub
                }

                override fun afterChangeFrame(periousFrameId: Int) {
                    // TODO Auto-generated method stub
                }

                override fun actionStart() {
                    // TODO Auto-generated method stub
                }

                override fun actionFinish() {
                    // TODO Auto-generated method stub
                }

                override fun actionCycleFinish() {
                    // TODO Auto-generated method stub
                    action.getAction().pause()
                }
            }

            action.getAction().setActionListener(newActionListener)
        }
    }
}
