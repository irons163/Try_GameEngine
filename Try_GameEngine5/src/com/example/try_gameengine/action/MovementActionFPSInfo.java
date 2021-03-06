package com.example.try_gameengine.action;

import com.example.try_gameengine.framework.Sprite;

public class MovementActionFPSInfo extends MovementActionInfo{
//	private long total;
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
	
	public MovementActionFPSInfo(long count, long durationFPSFream, float dx, float dy){
		this(count, durationFPSFream, dx, dy, null);
	}
	
	public MovementActionFPSInfo(long count, long durationFPSFream, float dx, float dy, String description){
		this(count, durationFPSFream, dx, dy, description, null, null);
	}
	
	public MovementActionFPSInfo(long count, long durationFPSFream, float dx, float dy, String description, Sprite sprite, String spriteActionName){
		super(count, durationFPSFream, dx, dy, description, sprite, spriteActionName);
	}
	
	@Override
	public void modifyInfoWithSpriteXY(float spriteX, float spriteY) {
		if (isSettingTargetXY) {
			float distanceX = targetX - spriteX;
			float distanceY = targetY - spriteY;
			float perMoveX = distanceX / (total/delay);
			float perMoveY = distanceY / (total/delay);
			dx = perMoveX;
			dy = perMoveY;
		}
	}
	
	@Override
	public boolean equals(Object obj) {  
        if (obj == null) return false;  
        if (!(obj instanceof MovementActionFPSInfo)) return false;
        MovementActionFPSInfo info = (MovementActionFPSInfo) obj;
        return (this.total == info.getTotal() && this.delay == info.getDelay() && this.dx == info.getDx() && this.dy == info.getDy());  
    }
	
	@Override
	public MovementActionFPSInfo clone(){
		MovementActionFPSInfo info = new MovementActionFPSInfo(total, delay, dx, dy, description, sprite, spriteActionName);
//		return new MovementActionItem(info);
		return info;
	}
	
//	private interface IMovementActionInfoMemento{
//		
//	}
	
	IMovementActionInfoMemento movementActionInfoMemento;
//	
//	public IMovementActionInfoMemento createIMovementActionInfoMemento(){
//		movementActionInfoMemento = new MovementActionInfoMementoMementoImpl(total, delay, dx, dy, description, rotationController, gravityController, enableGravity, sprite, spriteActionName, isLoop, isSettingTargetXY, targetX, targetY);
//		return movementActionInfoMemento;
//	}
//	
//	public void restoreMovementActionMemento(IMovementActionInfoMemento movementActionInfoMemento){
////		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento;
//
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
