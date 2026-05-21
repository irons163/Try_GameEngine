package com.example.try_gameengine.action.info

import android.util.Log
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener
import com.example.try_gameengine.action.MovementActionInfo
import com.example.try_gameengine.action.MovementActionItemAlpha2Data
import com.example.try_gameengine.action.MovementActionItemTrigger
import com.example.try_gameengine.action.MovementActionItemUpdateTimeData
import com.example.try_gameengine.action.MovementActionItemUpdateTimeData.UpdateType
import com.example.try_gameengine.framework.Sprite

class MovementActionScaleInfo @JvmOverloads constructor(
    total: Long,
    delay: Long,
    private var scaleX: Float,
    private var scaleY: Float,
    description: String? = null,
    sprite: Sprite? = null,
    spriteActionName: String? = null
) : MovementActionInfo(total, delay, 0f, 0f, description, sprite, spriteActionName) {
    private var offsetScaleXByOnceTrigger = 0f
    private var offsetScaleYByOnceTrigger = 0f
    private var scaleType = ScaleType.ScaleTo
    private var offsetScaleX = 0f
    private var offsetScaleY = 0f

    enum class ScaleType {
        ScaleTo, ScaleBy, ScaleToWith
    }

    fun setScaleType(scaleType: ScaleType) {
        this.scaleType = scaleType
    }

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

    override fun ggg() {
        val sprite = getSprite()
        val originalScaleX = sprite.getXscale()
        val originalScaleY = sprite.getYscale()
        when (scaleType) {
            ScaleType.ScaleTo -> {
                if (scaleX != NO_SCALE) {
                    offsetScaleX = scaleX - originalScaleX
                    offsetScaleXByOnceTrigger = offsetScaleX / (getTotal() / getDelay())
                }
                if (scaleY != NO_SCALE) {
                    offsetScaleY = scaleY - originalScaleY
                    offsetScaleYByOnceTrigger = offsetScaleY / (getTotal() / getDelay())
                }
            }
            ScaleType.ScaleBy -> {
                if (scaleX != NO_SCALE) {
                    offsetScaleX = scaleX
                    offsetScaleXByOnceTrigger = offsetScaleX / (getTotal() / getDelay())
                }
                if (scaleY != NO_SCALE) {
                    offsetScaleY = scaleY
                    offsetScaleYByOnceTrigger = offsetScaleY / (getTotal() / getDelay())
                }
            }
            ScaleType.ScaleToWith -> {
                if (scaleX != NO_SCALE) {
                    offsetScaleX = if (originalScaleX < 0) -scaleX - originalScaleX else scaleX - originalScaleX
                    offsetScaleXByOnceTrigger = offsetScaleX / (getTotal() / getDelay())
                }
                if (scaleY != NO_SCALE) {
                    offsetScaleY = if (originalScaleY < 0) -scaleY - originalScaleY else scaleY - originalScaleY
                    offsetScaleYByOnceTrigger = offsetScaleY / (getTotal() / getDelay())
                }
            }
        }
    }

    override fun didCycleFinish() {
        val sprite = getSprite()
        when (scaleType) {
            ScaleType.ScaleTo -> {
                if (scaleX != NO_SCALE) sprite.setXscale(scaleX)
                if (scaleY != NO_SCALE) sprite.setYscale(scaleY)
            }
            ScaleType.ScaleBy -> Unit
            ScaleType.ScaleToWith -> {
                if (scaleX != NO_SCALE) sprite.setXscale(scaleX)
                if (scaleY != NO_SCALE) sprite.setYscale(scaleY)
            }
        }
    }

    public override fun clone(): MovementActionScaleInfo {
        return MovementActionScaleInfo(total, delay, scaleX, scaleY, description, sprite, spriteActionName)
    }

    override var movementActionInfoMemento: MovementActionInfo.IMovementActionInfoMemento? = null

    override fun update(timerOnTickListener: TimerOnTickListener?) {
        val sprite = getSprite()
        if (offsetScaleXByOnceTrigger != 0f) sprite.setXscale(sprite.getXscale() + offsetScaleXByOnceTrigger)
        if (offsetScaleYByOnceTrigger != 0f) sprite.setYscale(sprite.getYscale() + offsetScaleYByOnceTrigger)
        Log.e("scale by scale action", "xScale:${sprite.getXscale()}yScale:${sprite.getYscale()}")
    }

    override fun update(t: Float, timerOnTickListener: TimerOnTickListener?) {
        offsetScaleXByOnceTrigger = offsetScaleX * t
        offsetScaleYByOnceTrigger = offsetScaleY * t
        update(timerOnTickListener)
    }

    companion object {
        val NO_SCALE: Float = Float.MIN_VALUE
    }
}
