package com.example.try_gameengine.action.info

import com.example.try_gameengine.action.IRotationController
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener
import com.example.try_gameengine.action.MovementActionInfo

class MovementActionMoveByCurveInfo(
    millisTotal: Long,
    millisDelay: Long,
    rotationController: IRotationController,
    description: String?
    ) : MovementActionInfo(millisTotal, millisDelay, 0f, 0f, description) {
    var rotationController: IRotationController
    var newDx: Float = 0f
        private set
    var newDy: Float = 0f
        private set

    constructor(millisTotal: Long, rotationController: IRotationController) : this(
        millisTotal,
        1,
        rotationController
    )

    /**
     * @param triggerTotal
     * @param triggerInterval
     * @param alpha
     // */
    constructor(
        triggerTotal: Long,
        triggerInterval: Long,
        rotationController: IRotationController
    ) : this(triggerTotal, triggerTotal, rotationController, "MovementActionItemAlpha")

    /**
     * @param millisTotal
     * @param millisDelay
     * @param originalAlpha
     * @param alpha
     * @param description
     // */
    init {
        this.description = description + ","
        this.rotationController = rotationController
    }

    public override fun update(timerOnTickListener: TimerOnTickListener?) {
//		doRotation();
//		doGravity();
        rotationController.execute(this)
        dx = this.getDx()
        dy = this.getDy()

        if (timerOnTickListener != null) {
            timerOnTickListener.onTick(dx, dy)
        } else {
            getSprite().move(dx, dy)
        }
    }

    public override fun update(t: Float, timerOnTickListener: TimerOnTickListener?) {
//		doRotation();
//		doGravity();
        rotationController.execute(this, t)
        dx = this.getDx()
        dy = this.getDy()
        newDx = (dx)
        newDy = (dy)

        if (timerOnTickListener != null) {
            timerOnTickListener.onTick(newDx, newDy)
        } else {
            getSprite().move(newDx, newDy)
        }
    }

    override fun ggg() {
        rotationController.start(this)
    }

    override fun didCycleFinish() {
        // TODO Auto-generated method stub
//		super.didCycleFinish();
    }
}
