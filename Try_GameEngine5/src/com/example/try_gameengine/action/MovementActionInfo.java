package com.example.try_gameengine.action;

import com.example.try_gameengine.framework.Config;
import com.example.try_gameengine.framework.Sprite;

/**
 * @author irons
 *
 */
/**
 * @author irons
 *
 */
public class MovementActionInfo {
	protected long total;
	protected long delay;
	protected float dx, dy;
	protected String description;
	protected IRotationController rotationController;
	protected IGravityController gravityController;
	protected boolean enableGravity;
	protected Sprite sprite;
	protected String spriteActionName;
	protected boolean isLoop = false;
	protected boolean isSettingTargetXY = false;
	protected float targetX, targetY;

	/**
	 * @param total
	 * @param delay
	 * @param dx
	 * @param dy
	 */
	public MovementActionInfo(long total, long delay, float dx, float dy) {
		this(total, delay, dx, dy, null);
	}

	/**
	 * @param total
	 * @param delay
	 * @param dx
	 * @param dy
	 * @param description
	 */
	public MovementActionInfo(long total, long delay, float dx, float dy,
			String description) {
		this(total, delay, dx, dy, description, null);
	}

	/**
	 * @param total
	 * @param delay
	 * @param dx
	 * @param dy
	 * @param description
	 * @param rotationController
	 */
	public MovementActionInfo(long total, long delay, float dx, float dy,
			String description, IRotationController rotationController) {
		this(total, delay, dx, dy, description, rotationController, false);
	}

	/**
	 * @param total
	 * @param delay
	 * @param dx
	 * @param dy
	 * @param description
	 * @param enableGravity
	 */
	public MovementActionInfo(long total, long delay, float dx, float dy,
			String description, boolean enableGravity) {
		this(total, delay, dx, dy, description, null, enableGravity);
	}

	/**
	 * @param total
	 * @param delay
	 * @param dx
	 * @param dy
	 * @param description
	 * @param rotationController
	 * @param enableGravity
	 */
	public MovementActionInfo(long total, long delay, float dx, float dy,
			String description, IRotationController rotationController,
			boolean enableGravity) {
		this.total = total;
		this.delay = delay;
		this.dx = dx;
		this.dy = dy;
		this.description = description;
		this.rotationController = rotationController;
		this.enableGravity = enableGravity;
		if (enableGravity)
			this.gravityController = new GravityController();
	}

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
	 */
	public MovementActionInfo(long total, long delay, float dx, float dy,
			String description, IRotationController rotationController,
			boolean enableGravity, Sprite sprite, String spriteActionName) {
		this.total = total;
		this.delay = delay;
		this.dx = dx;
		this.dy = dy;
		this.description = description;
		this.rotationController = rotationController;
		this.enableGravity = enableGravity;
		if (enableGravity)
			this.gravityController = new GravityController();
		this.sprite = sprite;
		this.spriteActionName = spriteActionName;
	}

	/**
	 * @return
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * @param total
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * @return
	 */
	public long getDelay() {
		return delay;
	}

	/**
	 * @param delay
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}

	/**
	 * @return
	 */
	public float getDx() {
		return dx;
	}

	/**
	 * @param dx
	 */
	public void setDx(float dx) {
		this.dx = dx;
	}

	/**
	 * @return
	 */
	public float getDy() {
		return dy;
	}

	/**
	 * @param dy
	 */
	public void setDy(float dy) {
		this.dy = dy;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return
	 */
	public IRotationController getRotationController() {
		return rotationController;
	}

	/**
	 * @param rotationController
	 */
	public void setRotationController(IRotationController rotationController) {
		this.rotationController = rotationController;
	}

	/**
	 * @return
	 */
	public IGravityController getGravityController() {
		return gravityController;
	}

	/**
	 * @return
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * @param sprite
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	/**
	 * @return
	 */
	public String getSpriteActionName() {
		return spriteActionName;
	}

	/**
	 * @param spriteActionName
	 */
	public void setSpriteActionName(String spriteActionName) {
		this.spriteActionName = spriteActionName;
	}

	/**
	 * @return
	 */
	public boolean isEnableGravity() {
		return enableGravity;
	}

	/**
	 * @param enableGravity
	 */
	public void isEnableGravity(boolean enableGravity) {
		if (enableGravity) {
			if (!this.enableGravity) {
				this.enableGravity = enableGravity;
				gravityController = new GravityController();
			}
		} else {
			this.enableGravity = enableGravity;
			gravityController = null;
		}
	}

	/**
	 * @return
	 */
	public boolean isLoop() {
		return isLoop;
	}

	/**
	 * @param isLoop
	 */
	public void isLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}

	/**
	 * @param targetX
	 * @param targetY
	 */
	public void setTargetXY(float targetX, float targetY) {
		this.targetX = targetX;
		this.targetY = targetY;
		isSettingTargetXY = true;
	}

	/**
	 * @param spriteX
	 * @param spriteY
	 */
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
		if (obj == null)
			return false;
		if (!(obj instanceof MovementActionInfo))
			return false;
		MovementActionInfo info = (MovementActionInfo) obj;
		return (this.total == info.getTotal() && this.delay == info.getDelay()
				&& this.dx == info.getDx() && this.dy == info.getDy());
	}

//	@Override
//	public MovementActionItem clone() {
//		MovementActionInfo info = new MovementActionInfo(total, delay, dx, dy,
//				description, rotationController, enableGravity, sprite,
//				spriteActionName);
//		return new MovementActionItem(info);
//	}

	@Override
	public MovementActionInfo clone() {
		MovementActionInfo info = new MovementActionInfo(total, delay, dx, dy,
				description, rotationController, enableGravity, sprite,
				spriteActionName);
		return info;
	}
	
	/**
	 * interface of MovementActionInfoMemento.
	 * @author irons
	 *
	 */
	protected interface IMovementActionInfoMemento {

	}

	/**
	 * MovementActionInfoMemento of this movement action info.
	 */
	IMovementActionInfoMemento movementActionInfoMemento;

	/**
	 * create MovementActionInfoMemento.
	 * @return a MovementActionInfoMemento.
	 */
	public IMovementActionInfoMemento createIMovementActionInfoMemento() {
		movementActionInfoMemento = new MovementActionInfoMementoMementoImpl(
				total, delay, dx, dy, description, rotationController,
				gravityController, enableGravity, sprite, spriteActionName,
				isLoop, isSettingTargetXY, targetX, targetY);
		return movementActionInfoMemento;
	}

	/**
	 * restore MovementActionInfoMemento.
	 * @param movementActionInfoMemento
	 */
	public void restoreMovementActionMemento(
			IMovementActionInfoMemento movementActionInfoMemento) {
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
		
		if(this.rotationController!=null)
			this.rotationController.rMovementActionItem.javaeset(info);
	}

	/**
	 * MovementActionInfoMementoMementoImpl implements IMovementActionInfoMemento.
	 * @author irons
	 *
	 */
	protected static class MovementActionInfoMementoMementoImpl implements
			IMovementActionInfoMemento {
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
