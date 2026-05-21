package com.example.try_gameengine.action.info

import com.example.try_gameengine.action.IGravityController
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener
import com.example.try_gameengine.action.MovementActionInfo

class MovementActionMoveByGravityInfo(
    millisTotal: Long,
    millisDelay: Long,
    gravityController: IGravityController,
    description: String?
    ) : MovementActionInfo(millisTotal, millisDelay, 0f, 0f, description) {
    var gravityController: IGravityController
    var newDx: Float = 0f
        private set
    var newDy: Float = 0f
        private set

    constructor(millisTotal: Long, gravityController: IGravityController) : this(
        millisTotal,
        1,
        gravityController
    )

    /**
     * @param triggerTotal
     * @param triggerInterval
     * @param alpha
     // */
    constructor(
        triggerTotal: Long,
        triggerInterval: Long,
        gravityController: IGravityController
    ) : this(triggerTotal, triggerTotal, gravityController, "MovementActionItemAlpha")

    /**
     * @param millisTotal
     * @param millisDelay
     * @param originalAlpha
     * @param alpha
     * @param description
     // */
    init {
        this.description = description + ","
        this.gravityController = gravityController
    }

    public override fun update(timerOnTickListener: TimerOnTickListener?) {
//		doRotation();
//		doGravity();
        gravityController.execute(this)
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
        gravityController.execute(this, t)
        dx = this.getDx()
        dy = this.getDy()
        //		float newDx = (float) (dx*t);
//		float newDy = (float) (dy*t);
        newDx = (dx)
        newDy = (dy)

        if (timerOnTickListener != null) {
            timerOnTickListener.onTick(newDx, newDy)
        } else {
            getSprite().move(newDx, newDy)
        }
    }

    override fun ggg() {
        gravityController.start(this)
    }

    override fun didCycleFinish() {
        // TODO Auto-generated method stub
//		super.didCycleFinish();
    }

    fun setPathType(pathType: IGravityController.PathType?) {
        gravityController!!.setPathType(pathType)
    }

    public override fun clone(): MovementActionMoveByGravityInfo {
        val info = MovementActionMoveByGravityInfo(
            getTotal(),
            getDelay(),
            gravityController!!.copyNewGravityController()!!,
            description
        )
        return info
    }
}
