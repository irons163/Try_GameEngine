package com.example.try_gameengine.action;

import java.util.List;
import java.util.concurrent.Future;

import javax.xml.datatype.Duration;

import com.example.try_gameengine.action.MovementAction.MovementActionMementoImpl;
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener;
import com.example.try_gameengine.action.MovementActionSetWithThreadPool.MovementActionSetWithThreadPoolMementoImpl;
import com.example.try_gameengine.framework.Config;
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
	private boolean isLoop = false;
	private boolean isSettingTargetXY = false;
	private float targetX, targetY;
	
	public MovementActionFPSInfo(long count, long durationFPSFream, float dx, float dy){
		this(count, durationFPSFream, dx, dy, null);
	}
	
	public MovementActionFPSInfo(long count, long durationFPSFream, float dx, float dy, String description){
		this(count, durationFPSFream, dx, dy, description, null);
	}
	
	public MovementActionFPSInfo(long count, long durationFPSFream, float dx, float dy, String description, IRotationController rotationController){
		this(count, durationFPSFream, dx, dy, description, rotationController, false);
	}
	
	public MovementActionFPSInfo(long count, long durationFPSFream, float dx, float dy, String description, boolean enableGravity){
		this(count, durationFPSFream, dx, dy, description, null, enableGravity);
	}
	
	public MovementActionFPSInfo(long count, long durationFPSFream, float dx, float dy, String description, IRotationController rotationController, boolean enableGravity){
		super(count, durationFPSFream, dx, dy, description, rotationController, enableGravity);
	}
	
	public MovementActionFPSInfo(long count, long durationFPSFream, float dx, float dy, String description, IRotationController rotationController, boolean enableGravity, Sprite sprite, String spriteActionName){
		super(count, durationFPSFream, dx, dy, description, rotationController, enableGravity, sprite, spriteActionName);
	}

	@Override
	public void setTargetXY(float targetX, float targetY){
		this.targetX = targetX;
		this.targetY = targetY;
		isSettingTargetXY = true;
	}
	
	@Override
	public void modifyInfoWithSpriteXY(float spriteX, float spriteY) {
		if(isSettingTargetXY){
			float distanceX = targetX - spriteX;
			float distanceY = targetY - spriteY;
			dx = distanceX / delay;
			float fps = Config.fps;
			float perFrame = 1000.0f/total/fps;
			float perMoveX = distanceX * perFrame;
			float perMoveY = distanceY * perFrame;
//			delay = (long)(perFrame*1000);
			total = (long) (total/1000.0f*fps);
			delay = 1;
			dx = perMoveX;
			dy = perMoveY;
			
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
	
	@Override
	public boolean equals(Object obj) {  
        if (obj == null) return false;  
        if (!(obj instanceof MovementActionFPSInfo)) return false;
        MovementActionFPSInfo info = (MovementActionFPSInfo) obj;
        return (this.total == info.getTotal() && this.delay == info.getDelay() && this.dx == info.getDx() && this.dy == info.getDy());  
    }
	
	public MovementActionItem clone(){
		MovementActionFPSInfo info = new MovementActionFPSInfo(total, delay, dx, dy, description, rotationController, enableGravity, sprite, spriteActionName);
		return new MovementActionItem(info);
	}
	
//	private interface IMovementActionInfoMemento{
//		
//	}
	
	IMovementActionInfoMemento movementActionInfoMemento;
	
	public IMovementActionInfoMemento createIMovementActionInfoMemento(){
		movementActionInfoMemento = new MovementActionInfoMementoMementoImpl(total, delay, dx, dy, description, rotationController, gravityController, enableGravity, sprite, spriteActionName, isLoop, isSettingTargetXY, targetX, targetY);
		return movementActionInfoMemento;
	}
	
	public void restoreMovementActionMemento(IMovementActionInfoMemento movementActionInfoMemento){
//		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento;

		MovementActionInfoMementoMementoImpl mementoImpl = (MovementActionInfoMementoMementoImpl) this.movementActionInfoMemento;
		this.total = mementoImpl.total;
		this.delay = mementoImpl.delay;
		this.dx = mementoImpl.dx;
		this.dy = mementoImpl.dy;
		this.description = mementoImpl.description;
		this.rotationController = mementoImpl.rotationController;
		this.gravityController = mementoImpl.gravityController;
		this.enableGravity = mementoImpl.enableGravity;
		this.sprite = mementoImpl.sprite;
		this.spriteActionName = mementoImpl.spriteActionName;
		this.isLoop = mementoImpl.isLoop;
		this.isSettingTargetXY = mementoImpl.isSettingTargetXY;
		this.targetX = mementoImpl.targetX;
		this.targetY = mementoImpl.targetY;
	}
	
	protected static class MovementActionInfoMementoMementoImpl implements IMovementActionInfoMemento {
		private long total;
		private long delay;
		private float dx, dy;
		private String description;
		private IRotationController rotationController;
		private IGravityController gravityController;
		private boolean enableGravity;
		private Sprite sprite;
		private String spriteActionName;
		private boolean isLoop = false;
		private boolean isSettingTargetXY = false;
		private float targetX, targetY;
		public MovementActionInfoMementoMementoImpl(long total, long delay,
				float dx, float dy, String description,
				IRotationController rotationController,
				IGravityController gravityController, boolean enableGravity,
				Sprite sprite, String spriteActionName, boolean isLoop,
				boolean isSettingTargetXY, float targetX, float targetY) {
			super();
			this.total = total;
			this.delay = delay;
			this.dx = dx;
			this.dy = dy;
			this.description = description;
			this.rotationController = rotationController;
			this.gravityController = gravityController;
			this.enableGravity = enableGravity;
			this.sprite = sprite;
			this.spriteActionName = spriteActionName;
			this.isLoop = isLoop;
			this.isSettingTargetXY = isSettingTargetXY;
			this.targetX = targetX;
			this.targetY = targetY;
		}
		
		
	}
}
