package com.example.try_gameengine.action

import com.example.try_gameengine.action.MovementAction.TimerOnTickListener
import com.example.try_gameengine.action.MovementActionItemUpdateTimeData.UpdateType
import com.example.try_gameengine.framework.Config
import com.example.try_gameengine.framework.Sprite

/**
 * @author irons
 // */
/**
 * @author irons
 // */
internal interface MovementActionInfoUpdateDelegate {
    fun update(timerOnTickListener: TimerOnTickListener?)
    fun update(t: Float, timerOnTickListener: TimerOnTickListener?)
}

open class MovementActionInfo @JvmOverloads constructor(
    total: Long, delay: Long, dx: Float, dy: Float,
    description: String? = null, sprite: Sprite? = null, spriteActionName: String? = null
) : MovementActionInfoUpdateDelegate, Cloneable {
    /**
     * @return
     // */
    /**
     * @param total
     // */
    @JvmField
    var total: Long
    /**
     * @return
     // */
    /**
     * @param delay
     // */
    @JvmField
    var delay: Long
    /**
     * @return
     // */
    /**
     * @param dx
     // */
    @JvmField
    var dx: Float
    /**
     * @return
     // */
    /**
     * @param dy
     // */
    @JvmField
    var dy: Float
    /**
     * @return
     // */
    /**
     * @param description
     // */
    @JvmField
    var description: String?
    /**
     * @return
     // */
    /**
     * @param sprite
     // */
    @JvmField
    var sprite: Sprite?
    /**
     * @return
     // */
    /**
     * @param spriteActionName
     // */
    @JvmField
    var spriteActionName: String?

    /**
     * @return
     // */
    @JvmField
    var isLoop: Boolean = false
    protected var isSettingTargetXY: Boolean = false
    protected var targetX: Float = 0f
    protected var targetY: Float = 0f
    @JvmField
    var data: MovementActionItemTrigger = MovementActionItemUpdateTimeData()

    open fun getTotal(): Long {
        return total
    }

    open fun setTotal(total: Long) {
        this.total = total
    }

    open fun getDelay(): Long {
        return delay
    }

    open fun setDelay(delay: Long) {
        this.delay = delay
    }

    open fun getDx(): Float {
        return dx
    }

    open fun setDx(dx: Float) {
        this.dx = dx
    }

    open fun getDy(): Float {
        return dy
    }

    open fun setDy(dy: Float) {
        this.dy = dy
    }

    open fun getDescription(): String? {
        return description
    }

    open fun setDescription(description: String?) {
        this.description = description
    }

    open fun getSprite(): Sprite {
        return sprite!!
    }

    open fun setSprite(sprite: Sprite?) {
        this.sprite = sprite
    }

    open fun getSpriteActionName(): String? {
        return spriteActionName
    }

    open fun setSpriteActionName(spriteActionName: String?) {
        this.spriteActionName = spriteActionName
    }

    open fun getData(): MovementActionItemTrigger {
        return data
    }

    open fun setData(data: MovementActionItemTrigger?) {
        this.data = data ?: return
    }

    open fun isLoop(): Boolean {
        return isLoop
    }

    open fun createUpdateByIntervalTimeData() {
        val updateTimeData = MovementActionItemUpdateTimeData()
        updateTimeData.setUpdateType(UpdateType.UpdateByInterval)
        this.data = updateTimeData
    }

    open fun createUpdateByTriggerData() {
        this.data = MovementActionItemAlpha2Data()
    }

    open fun createUpdateByEverytimeData() {
        val updateTimeData = MovementActionItemUpdateTimeData()
        updateTimeData.setUpdateType(UpdateType.UpdateEverytime)
        this.data = updateTimeData
    }

    /**
     * @param isLoop
     // */
    open fun isLoop(isLoop: Boolean) {
        this.isLoop = isLoop
    }

    /**
     * @param targetX
     * @param targetY
     // */
    open fun setTargetXY(targetX: Float, targetY: Float) {
        this.targetX = targetX
        this.targetY = targetY
        isSettingTargetXY = true
    }

    /**
     * @param spriteX
     * @param spriteY
     // */
    open fun modifyInfoWithSpriteXY(spriteX: Float, spriteY: Float) {
        if (isSettingTargetXY) {
            val distanceX = targetX - spriteX
            val distanceY = targetY - spriteY
            dx = distanceX / delay
            val fps = Config.fps
            val perFrame = 1000.0f / total / fps
            val perMoveX = distanceX * perFrame
            val perMoveY = distanceY * perFrame
            //			delay = (long)(perFrame*1000);
            total = (total / 1000.0f * fps).toLong()
            delay = 1
            dx = perMoveX
            dy = perMoveY


//			float distanceX = targetX - spriteX;
//			float distanceY = targetY - spriteY;
//			float fps = Config.fps;
//			float perFrame = 1000.0f/total/fps;
//			float perMoveX = perFrame / distanceX;
//			float perMoveY = perFrame / distanceY;
//			total = (long) (total*fps);
//			delay = 1;
//			dx = perMoveX;
//			dy = perMoveY;
        }
    }

    open fun ggg() {
    }

    open fun didCycleFinish() {
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) return false
        if (obj !is MovementActionInfo) return false
        val info = obj
        return (this.total == info.total && this.delay == info.delay && this.dx == info.dx && this.dy == info.dy)
    }

    //	@Override
    //	public MovementActionItem clone() {
    //		MovementActionInfo info = new MovementActionInfo(total, delay, dx, dy,
    //				description, rotationController, enableGravity, sprite,
    //				spriteActionName);
    //		return new MovementActionItem(info);
    //	}
    public override fun clone(): MovementActionInfo {
        val info = MovementActionInfo(
            total, delay, dx, dy,
            description, sprite,
            spriteActionName
        )
        return info
    }

    /**
     * interface of MovementActionInfoMemento.
     * @author irons
     // */
    interface IMovementActionInfoMemento

    /**
     * MovementActionInfoMemento of this movement action info.
     // */
    open var movementActionInfoMemento: IMovementActionInfoMemento? = null

    /**
     * @param total
     * @param delay
     * @param dx
     * @param dy
     * @param description
     * @param rotationController
     * @param enableGravity
     * @param sprite
     * @param spriteActionName
     // */
    /**
     * @param total
     * @param delay
     * @param dx
     * @param dy
     * @param description
     // */
    /**
     * @param total
     * @param delay
     * @param dx
     * @param dy
     // */
    init {
        this.total = total
        this.delay = delay
        this.dx = dx
        this.dy = dy
        this.description = description
        this.sprite = sprite
        this.spriteActionName = spriteActionName
    }

    override fun update(timerOnTickListener: TimerOnTickListener?) {
    }

    override fun update(t: Float, timerOnTickListener: TimerOnTickListener?) {
    } //	/**
    //	 * create MovementActionInfoMemento.
    //	 * @return a MovementActionInfoMemento.
    //	 */
    //	public IMovementActionInfoMemento createIMovementActionInfoMemento() {
    //		movementActionInfoMemento = new MovementActionInfoMementoMementoImpl(
    //				total, delay, dx, dy, description, rotationController,
    //				gravityController, enableGravity, sprite, spriteActionName,
    //				isLoop, isSettingTargetXY, targetX, targetY);
    //		return movementActionInfoMemento;
    //	}
    //	/**
    //	 * restore MovementActionInfoMemento.
    //	 * @param movementActionInfoMemento
    //	 */
    //	public void restoreMovementActionMemento(
    //			IMovementActionInfoMemento movementActionInfoMemento) {
    //		MovementActionInfoMementoMementoImpl mementoImpl = (MovementActionInfoMementoMementoImpl) this.movementActionInfoMemento;
    //		this.total = mementoImpl.total;
    //		this.delay = mementoImpl.delay;
    //		this.dx = mementoImpl.dx;
    //		this.dy = mementoImpl.dy;
    //		this.description = mementoImpl.description;
    //		this.rotationController = mementoImpl.rotationController;
    //		this.gravityController = mementoImpl.gravityController;
    //		this.enableGravity = mementoImpl.enableGravity;
    //		this.sprite = mementoImpl.sprite;
    //		this.spriteActionName = mementoImpl.spriteActionName;
    //		this.isLoop = mementoImpl.isLoop;
    //		this.isSettingTargetXY = mementoImpl.isSettingTargetXY;
    //		this.targetX = mementoImpl.targetX;
    //		this.targetY = mementoImpl.targetY;
    //		
    // /**/        if(this.rotationController!=null)
    // * /            this.rotationController.reset(info); */
    //	}
    //
    //	/**
    //	 * MovementActionInfoMementoMementoImpl implements IMovementActionInfoMemento.
    //	 * @author irons
    //	 *
    //	 */
    //	protected static class MovementActionInfoMementoMementoImpl implements
    //			IMovementActionInfoMemento {
    //		private long total;
    //		private long delay;
    //		private float dx, dy;
    //		private String description;
    //		private IRotationController rotationController;
    //		private IGravityController gravityController;
    //		private boolean enableGravity;
    //		private Sprite sprite;
    //		private String spriteActionName;
    //		private boolean isLoop = false;
    //		private boolean isSettingTargetXY = false;
    //		private float targetX, targetY;
    //
    //		public MovementActionInfoMementoMementoImpl(long total, long delay,
    //				float dx, float dy, String description,
    //				IRotationController rotationController,
    //				IGravityController gravityController, boolean enableGravity,
    //				Sprite sprite, String spriteActionName, boolean isLoop,
    //				boolean isSettingTargetXY, float targetX, float targetY) {
    //			super();
    //			this.total = total;
    //			this.delay = delay;
    //			this.dx = dx;
    //			this.dy = dy;
    //			this.description = description;
    //			this.rotationController = rotationController;
    //			this.gravityController = gravityController;
    //			this.enableGravity = enableGravity;
    //			this.sprite = sprite;
    //			this.spriteActionName = spriteActionName;
    //			this.isLoop = isLoop;
    //			this.isSettingTargetXY = isSettingTargetXY;
    //			this.targetX = targetX;
    //			this.targetY = targetY;
    //		}
    //
    //	}
}
