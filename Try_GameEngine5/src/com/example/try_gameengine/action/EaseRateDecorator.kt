package com.example.try_gameengine.action

import com.example.try_gameengine.action.MovementActionItemTrigger.DataDelegate
import kotlin.math.pow

//import com.rits.cloning.Cloner;
/**
 * @author irons
 // */
open class EaseRateDecorator(action: MovementAction, rate: Float) : MovementDecorator() {
    var rate: Float

    init {
        this.action = action
        this.rate = rate
        //		this.copyMovementActionList = action.copyMovementActionList;
    }

    /**
     * @param action
     * @return
     // */
    public override fun coreCalculationMovementActionInfo(
        action: MovementAction
    ): MovementAction {
        doinin(action.getInfo())
        return action
    }

    override fun start() {
        action.getAction().start()
    }

    override fun getAction(): MovementAction {
        return action.getAction()
    }

    override fun getDescription(): String? {
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

    open fun doinin(info: MovementActionInfo) {
        info.getData().setMovementActionItemUpdateTimeDataDelegate(object : DataDelegate() {
            public override fun update(t: Float) {
                // TODO Auto-generated method stub

                val percent = ((t.toDouble()) / info.getData()
                    .getShouldActiveTotalValue()).pow(rate.toDouble())
                super.update((t * percent).toLong().toFloat())
            }
        })
    }

    public override fun cancelMove() {
        action.getAction().cancelMove()
    }

    override fun pause() {
        action.getAction().pause()
    }
}
