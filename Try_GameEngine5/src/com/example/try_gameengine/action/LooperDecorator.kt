package com.example.try_gameengine.action

//import com.rits.cloning.Cloner;
/**
 * @author irons
 // */
class LooperDecorator(action: MovementAction) : MovementDecorator() {
    init {
        this.action = action
        //		this.copyMovementActionList = action.copyMovementActionList;
    }

    /**
     * @param action
     * @return
     // */
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

        this.getAction()!!.isLoop = true

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
}
