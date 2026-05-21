package com.example.try_gameengine.action

import java.util.Collections

class InverseMoveOrderDecorator(action: MovementAction) : MovementDecorator() {
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
        val actions = super.doIn(actionSet)

        //		this.getAction().getCurrentInfoList();
//		int i = 0;
//		for (MovementActionInfo info : this.getAction().currentInfoList) {
//			Log.e("count", ++i + "");
//			Log.e("info", info.getDx() + "");
//			this.getAction().setInfo(info);
        // /**/            coreCalculationMovementActionInfo(this.getAction()!!.getInfo()); */
//			coreCalculationMovementActionInfo(this.getAction());
//		}
        inverseOrder(this)

        return actions

        //		for (MovementAction movementItem : this.getAction().movementItemList) {
//			movementItem.initTimer();
//		}
    }

    private fun inverseOrder(targetAction: MovementAction) {
        Collections.reverse(targetAction.getAction().getActions())
        for (action in targetAction.getAction().getActions()) {
            inverseOrder(action)
        }
    }
}
