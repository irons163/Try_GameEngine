package com.example.try_gameengine.action

interface MovementActionItemTrigger {
    interface MovementActionItemUpdateTimeDataDelegate {
        fun update()
        fun update(t: Float)
    }

    interface MovementActionItemTriggerInitDelegate {
        fun initForUpdateTime()
        fun initForFrameTrigger()
    }

    open class DataDelegate : MovementActionItemUpdateTimeDataDelegate {
        private var other: MovementActionItemUpdateTimeDataDelegate? = null

        fun addMovementActionItemUpdateTimeDataDelegate(dataDelegate: MovementActionItemUpdateTimeDataDelegate?) {
            if (other != null && other is DataDelegate) (other as DataDelegate).addMovementActionItemUpdateTimeDataDelegate(
                dataDelegate
            )
            else if (other != null) {
                throw RuntimeException("double setting")
            } else {
                other = dataDelegate
            }


//			DataDelegate lastDataDelegate = getDataDelegate();
//			lastDataDelegate.addMovementActionItemUpdateTimeDataDelegate(dataDelegate);
        }

        private val dataDelegate: MovementActionItemUpdateTimeDataDelegate?
            get() {
                if (other != null && other is DataDelegate) return (other as DataDelegate).dataDelegate
                else if (other != null) {
                    return other
                }
                return this
            }

        override fun update() {
            // TODO Auto-generated method stub
            if (other != null) other!!.update()
        }

        override fun update(t: Float) {
            // TODO Auto-generated method stub
            if (other != null) other!!.update(t)
        }
    }

    fun initWithInitDelegate(initDelegate: MovementActionItemTriggerInitDelegate?)

    fun isCycleFinish(): Boolean
    fun setCycleFinish(isCycleFinish: Boolean)

    fun getShouldActiveTotalValue(): Long
    fun setShouldActiveTotalValue(shouldActiveTotalValue: Long)

    fun getShouldActiveIntervalValue(): Long
    fun setShouldActiveIntervalValue(shouldActiveIntervalValue: Long)

    fun getShouldPauseValue(): Long
    fun setShouldPauseValue(shouldPauseValue: Long)

    fun getValueOfPausedCounter(): Long
    fun setValueOfPausedCounter(valueOfPausedCounter: Long)

    fun getValueOfActivedCounter(): Long
    fun setValueOfActivedCounter(valueOfActivedCounter: Long)

    fun getActivedValueForLatestUpdated(): Long
    fun setActivedValueForLatestUpdated(activedValueForLatestUpdated: Long)

    fun isEnableSetSpriteAction(): Boolean
    fun setEnableSetSpriteAction(isEnableSetSpriteAction: Boolean)

    fun getValueOfFactorByUpdate(): Double

    fun getMovementActionItemUpdateTimeDataDelegate(): MovementActionItemUpdateTimeDataDelegate?
    fun setMovementActionItemUpdateTimeDataDelegate(movementActionItemUpdateTimeDataDelegate: MovementActionItemUpdateTimeDataDelegate?)

    fun dodo()
}
