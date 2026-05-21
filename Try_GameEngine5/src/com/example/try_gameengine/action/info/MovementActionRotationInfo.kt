package com.example.try_gameengine.action.info

import android.util.Log
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener
import com.example.try_gameengine.action.MovementActionInfo
import com.example.try_gameengine.action.MovementActionItemAlpha2Data
import com.example.try_gameengine.action.MovementActionItemTrigger
import com.example.try_gameengine.action.MovementActionItemUpdateTimeData
import com.example.try_gameengine.action.MovementActionItemUpdateTimeData.UpdateType
import com.example.try_gameengine.framework.Sprite

class MovementActionRotationInfo @JvmOverloads constructor(
    total: Long,
    delay: Long,
    dx: Float,
    dy: Float,
    description: String? = null,
    sprite: Sprite? = null,
    spriteActionName: String? = null
) : MovementActionInfo(total, delay, dx, dy, description, sprite, spriteActionName) {
    private var originalAlpha = NO_ORGINAL_ALPHA
    private var alpha = 0
    private var offsetAlphaByOnceTrigger = 0f

    override fun createUpdateByIntervalTimeData() {
        data = MovementActionItemUpdateTimeData().also { it.setUpdateType(UpdateType.UpdateByInterval) }
    }

    override fun createUpdateByTriggerData() {
        data = MovementActionItemAlpha2Data()
    }

    override fun createUpdateByEverytimeData() {
        data = MovementActionItemUpdateTimeData().also { it.setUpdateType(UpdateType.UpdateEverytime) }
    }

    override fun getData(): MovementActionItemTrigger = data

    override fun setData(data: MovementActionItemTrigger?) {
        this.data = data ?: return
    }

    fun getAlpha(): Int = alpha

    fun setAlpha(alpha: Int) {
        this.alpha = alpha
    }

    override fun ggg() {
        originalAlpha = if (originalAlpha == NO_ORGINAL_ALPHA) getSprite().getAlpha() else originalAlpha
        if (originalAlpha != NO_ORGINAL_ALPHA) getSprite().setAlpha(originalAlpha)
        val offsetAlpha = alpha - originalAlpha
        offsetAlphaByOnceTrigger = (offsetAlpha / (getTotal().toDouble() / getDelay())).toFloat()
    }

    override fun didCycleFinish() {
        getSprite().setAlpha(alpha)
    }

    public override fun clone(): MovementActionRotationInfo {
        val info = MovementActionRotationInfo(total, delay, dx, dy, description, sprite, spriteActionName)
        info.originalAlpha = originalAlpha
        info.alpha = alpha
        return info
    }

    override var movementActionInfoMemento: MovementActionInfo.IMovementActionInfoMemento? = null

    override fun update(timerOnTickListener: TimerOnTickListener?) {
        getSprite().setAlpha(originalAlpha + offsetAlphaByOnceTrigger.toInt())
    }

    override fun update(t: Float, timerOnTickListener: TimerOnTickListener?) {
        val offsetAlpha = alpha - originalAlpha
        offsetAlphaByOnceTrigger = offsetAlpha * t
        Log.e("offsetAlpha", "$offsetAlpha $t")
        getSprite().setAlpha(originalAlpha + offsetAlphaByOnceTrigger.toInt())
    }

    fun getOffsetAlpha(): Float = offsetAlphaByOnceTrigger

    companion object {
        const val NO_ORGINAL_ALPHA: Int = -1
    }
}
