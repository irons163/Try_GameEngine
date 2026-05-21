package com.example.try_gameengine.action

class HalfDecorator(action: MovementAction) : MovementDecorator() {
    init {
        this.action = action
    }

    public override fun coreCalculationMovementActionInfo(action: MovementAction): MovementAction {
        val info = action.getInfo()
        info.setTotal(info.getTotal())
        info.setDelay(info.getDelay())
        info.setDx(0.5f * info.getDx())
        info.setDy(0.5f * info.getDy())
        return action
    }

    override fun start() {
        action.getAction().start()
    }

    override fun getAction(): MovementAction {
        return action.getAction()
    }

    override fun getDescription(): String {
        return "Half " + action.getDescription()
    }

    public override fun initTimer(): MovementAction {
        super.initTimer()
        for (action in this.getAction()!!.getActions()) {
            this.getAction()!!.setInfo(action.getInfo())
            action.getAction().setInfo(getInfo())
            action.getAction().initTimer()
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
}
