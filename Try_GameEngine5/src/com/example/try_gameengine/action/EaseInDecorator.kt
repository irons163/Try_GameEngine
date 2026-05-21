package com.example.try_gameengine.action

import com.example.try_gameengine.action.MovementActionItemTrigger.DataDelegate
import kotlin.math.pow

//import com.rits.cloning.Cloner;
/**
 * @author irons
 // */
class EaseInDecorator(action: MovementAction, rate: Float) : EaseRateDecorator(action, rate) {
    public override fun getDescription(): String {
        return "Double " + action.getDescription()
    }

    override fun doinin(info: MovementActionInfo) {
        // TODO Auto-generated method stub
        info.getData().setMovementActionItemUpdateTimeDataDelegate(object : DataDelegate() {
            public override fun update(t: Float) {
                // TODO Auto-generated method stub

                super.update(t.toDouble().pow(rate.toDouble()).toFloat())
            }
        })
    }
}
