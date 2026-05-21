package com.example.try_gameengine.action

import com.example.try_gameengine.framework.Sprite

class MovementActionFPSInfo  //	private long total;
//	private long delay;
//	private float dx, dy;
//	private String description;
//	private IRotationController rotationController;
//	private IGravityController gravityController;
//	private boolean enableGravity;
//	private Sprite sprite;
//	private String spriteActionName;
//	private boolean isLoop = false;
//	private boolean isSettingTargetXY = false;
//	private float targetX, targetY;
@JvmOverloads constructor(
    count: Long,
    durationFPSFream: Long,
    dx: Float,
    dy: Float,
    description: String? = null,
    sprite: Sprite? = null,
    spriteActionName: String? = null
) : MovementActionInfo(count, durationFPSFream, dx, dy, description, sprite, spriteActionName) {
    override fun modifyInfoWithSpriteXY(spriteX: Float, spriteY: Float) {
        if (isSettingTargetXY) {
            val distanceX = targetX - spriteX
            val distanceY = targetY - spriteY
            val perMoveX = distanceX / (total / delay)
            val perMoveY = distanceY / (total / delay)
            dx = perMoveX
            dy = perMoveY
        }
    }

    public override fun equals(obj: Any?): Boolean {
        if (obj == null) return false
        if (obj !is MovementActionFPSInfo) return false
        val info = obj
        return (this.total == info.getTotal() && this.delay == info.getDelay() && this.dx == info.getDx() && this.dy == info.getDy())
    }

    public override fun clone(): MovementActionFPSInfo {
        val info =
            MovementActionFPSInfo(total, delay, dx, dy, description, sprite, spriteActionName)
        //		return new MovementActionItem(info);
        return info
    }

    //	private interface IMovementActionInfoMemento{
    //		
    //	}
    override var movementActionInfoMemento: IMovementActionInfoMemento? = null //
    //	public IMovementActionInfoMemento createIMovementActionInfoMemento(){
    //		movementActionInfoMemento = new MovementActionInfoMementoMementoImpl(total, delay, dx, dy, description, rotationController, gravityController, enableGravity, sprite, spriteActionName, isLoop, isSettingTargetXY, targetX, targetY);
    //		return movementActionInfoMemento;
    //	}
    //	
    //	public void restoreMovementActionMemento(IMovementActionInfoMemento movementActionInfoMemento){
    // /**/        MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento; */ //
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
    //	}
    //	
    //	protected static class MovementActionInfoMementoMementoImpl implements IMovementActionInfoMemento {
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
    //		
    //	}
}
