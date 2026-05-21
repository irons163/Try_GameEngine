package com.example.try_gameengine.action

import com.example.try_gameengine.action.MovementActionItemTrigger.MovementActionItemTriggerInitDelegate
import com.example.try_gameengine.action.MovementActionItemTrigger.MovementActionItemUpdateTimeDataDelegate

class MovementActionItemAlpha2Data : MovementActionItemTrigger {
    private var numberOfFramesTotal: Long = 0
    private var numberOfFramesInterval: Long = 0
    private var numberOfPauseFrames: Long = 0
    private var pauseFrameCounter: Long = 0
    private var resumeFrameCount: Long = 0
    private var isCycleFinish = false
    private var numberOfFramesAfterLastTrigger: Long = 0
    private var isEnableSetSpriteAction = false
    private var movementActionItemUpdateTimeDataDelegate: MovementActionItemUpdateTimeDataDelegate? =
        null

    override fun dodo() {
        if (this.isCycleFinish()) this.setCycleFinish(false)

        this.setValueOfActivedCounter(this.getValueOfActivedCounter() + 1)

        if (this.getValueOfActivedCounter() == this.getActivedValueForLatestUpdated() + getShouldActiveIntervalValue()) {
            movementActionItemUpdateTimeDataDelegate!!.update()

            this.setActivedValueForLatestUpdated(
                this
                    .getActivedValueForLatestUpdated() + getShouldActiveIntervalValue()
            )


            // add by 150228. if the delay change by main app, the function: else if(resumeFrameCount==lastTriggerFrameNum+info.getDelay() maybe make problem.
        } else if (this.getValueOfActivedCounter() > this.getActivedValueForLatestUpdated() + getShouldActiveIntervalValue()) {
//			resumeFrameCount--;
//			lastTriggerFrameNum++;
            this.setActivedValueForLatestUpdated(this.getValueOfActivedCounter() + 1 - getShouldActiveIntervalValue())
        }

        if (this.getValueOfActivedCounter() >= getShouldActiveIntervalValue()) {
            if (this.getValueOfActivedCounter() == getShouldActiveTotalValue()) this.setCycleFinish(
                true
            )
        }

        if (this.isCycleFinish()) {
            this.setValueOfActivedCounter(0)
            this.setActivedValueForLatestUpdated(0)
        }
    }

    override fun getShouldActiveTotalValue(): Long {
        // TODO Auto-generated method stub
        return numberOfFramesTotal
    }

    override fun getShouldActiveIntervalValue(): Long {
        // TODO Auto-generated method stub
        return numberOfFramesInterval
    }

    override fun setShouldActiveTotalValue(numberOfFramesTotal: Long) {
        this.numberOfFramesTotal = numberOfFramesTotal
    }

    override fun setShouldActiveIntervalValue(numberOfFramesInterval: Long) {
        // TODO Auto-generated method stub
        this.numberOfFramesInterval = numberOfFramesInterval
    }

    override fun getShouldPauseValue(): Long {
        // TODO Auto-generated method stub
        return numberOfPauseFrames
    }

    override fun setShouldPauseValue(numberOfPauseFrames: Long) {
        // TODO Auto-generated method stub
        this.numberOfPauseFrames = numberOfPauseFrames
    }

    override fun getValueOfPausedCounter(): Long {
        // TODO Auto-generated method stub
        return pauseFrameCounter
    }

    override fun setValueOfPausedCounter(pauseFrameCounter: Long) {
        // TODO Auto-generated method stub
        this.pauseFrameCounter = pauseFrameCounter
    }

    override fun getValueOfActivedCounter(): Long {
        // TODO Auto-generated method stub
        return resumeFrameCount
    }

    override fun setValueOfActivedCounter(resumeFrameCount: Long) {
        // TODO Auto-generated method stub
        this.resumeFrameCount = resumeFrameCount
    }

    override fun getActivedValueForLatestUpdated(): Long {
        // TODO Auto-generated method stub
        return numberOfFramesAfterLastTrigger
    }

    override fun setActivedValueForLatestUpdated(numberOfFramesAfterLastTrigger: Long) {
        // TODO Auto-generated method stub
        this.numberOfFramesAfterLastTrigger = numberOfFramesAfterLastTrigger
    }

    override fun getMovementActionItemUpdateTimeDataDelegate(): MovementActionItemUpdateTimeDataDelegate? {
        return movementActionItemUpdateTimeDataDelegate
    }

    override fun setMovementActionItemUpdateTimeDataDelegate(
        movementActionItemUpdateTimeDataDelegate: MovementActionItemUpdateTimeDataDelegate?
    ) {
        this.movementActionItemUpdateTimeDataDelegate = movementActionItemUpdateTimeDataDelegate
    }

    override fun isCycleFinish(): Boolean {
        // TODO Auto-generated method stub
        return isCycleFinish
    }

    override fun setCycleFinish(isCycleFinish: Boolean) {
        // TODO Auto-generated method stub
        this.isCycleFinish = isCycleFinish
    }

    override fun isEnableSetSpriteAction(): Boolean {
        // TODO Auto-generated method stub
        return isEnableSetSpriteAction
    }

    override fun setEnableSetSpriteAction(isEnableSetSpriteAction: Boolean) {
        // TODO Auto-generated method stub
        this.isEnableSetSpriteAction = isEnableSetSpriteAction
    }

    override fun getValueOfFactorByUpdate(): Double {
        // TODO Auto-generated method stub
        return getShouldActiveIntervalValue().toDouble() / getShouldActiveTotalValue()
    }

    override fun initWithInitDelegate(
        initDelegate: MovementActionItemTriggerInitDelegate?
    ) {
        if (initDelegate != null) {
            initDelegate.initForFrameTrigger()
        }
    }
}
