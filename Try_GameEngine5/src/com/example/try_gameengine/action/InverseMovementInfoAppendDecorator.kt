package com.example.try_gameengine.action

class InverseMovementInfoAppendDecorator(action: MovementAction) : MovementDecorator() {
    init {
        this.action = action
        //		this.copyMovementActionList = action.copyMovementActionList;
    }

    public override fun coreCalculationMovementActionInfo(
        action: MovementAction
    ): MovementAction {
        //		MovementActionInfo newInfo = new MovementActionInfo(action.getTotal(),
//				action.getDelay(), action.getDx(), action.getDy(),
//				action.getDescription());
//		if (this.getAction().getActions().size() != 0) {
//			MovementAction action = new MovementActionItemCountDownTimer(newInfo);
        // /**/            copyMovementActionList.add(action);
        // * /            this.getAction().totalCopyMovementActionList.add(action); */
//		}

        val info = action.getInfo()
        val newInfo = info.clone()
        newInfo.setTotal(info.getTotal())
        newInfo.setDelay(info.getDelay())
        newInfo.setDx(-info.getDx())
        newInfo.setDy(-info.getDy())
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
