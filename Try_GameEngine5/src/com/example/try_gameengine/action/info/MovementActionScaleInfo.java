package com.example.try_gameengine.action.info;

import android.util.Log;

import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionInfo;
import com.example.try_gameengine.action.MovementActionItemAlpha2Data;
import com.example.try_gameengine.action.MovementActionItemTrigger;
import com.example.try_gameengine.action.MovementActionItemUpdateTimeData;
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener;
import com.example.try_gameengine.action.MovementActionItemUpdateTimeData.UpdateType;
import com.example.try_gameengine.framework.Config;
import com.example.try_gameengine.framework.Sprite;

/**
 * @author irons
 *
 */

public class MovementActionScaleInfo extends MovementActionInfo{
	private float scaleX, scaleY;
	private float offsetScaleXByOnceTrigger, offsetScaleYByOnceTrigger;
	public static final float NO_SCALE = Float.MIN_VALUE;
	private ScaleType scaleType = ScaleType.ScaleTo;
	
	/**
	 * These are scale types. Like. 
	 * @author irons
	 *
	 */
	public enum ScaleType{
		ScaleTo, ScaleBy, ScaleToWith
	}
	
	public void setScaleType(ScaleType scaleType){
		this.scaleType = scaleType;
	}
	
	/**
	 * @param total
	 * @param delay
	 * @param dx
	 * @param dy
	 */
	public MovementActionScaleInfo(long total, long delay, float scaleX, float scaleY) {
		this(total, delay, scaleX, scaleY, null);
	}

	/**
	 * @param total
	 * @param delay
	 * @param dx
	 * @param dy
	 * @param description
	 */
	public MovementActionScaleInfo(long total, long delay, float scaleX, float scaleY,
			String description) {
		this(total, delay, 0, 0, description, null, null);
		
		this.scaleX = scaleX;
		this.scaleY = scaleY;
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
	public MovementActionScaleInfo(long total, long delay, float scaleX, float scaleY,
			String description, Sprite sprite, String spriteActionName) {
		super(total, delay, 0, 0, description, sprite, spriteActionName);
	}
	
	public void createUpdateByIntervalTimeData(){
		MovementActionItemUpdateTimeData updateTimeData = new MovementActionItemUpdateTimeData();
		updateTimeData.setUpdateType(UpdateType.UpdateByInterval);
		this.data = updateTimeData;
	}
	
	public void createUpdateByTriggerData(){
		this.data = new MovementActionItemAlpha2Data();
	}
	
	public void createUpdateByEverytimeData(){
		MovementActionItemUpdateTimeData updateTimeData = new MovementActionItemUpdateTimeData();
		updateTimeData.setUpdateType(UpdateType.UpdateEverytime);
		this.data = updateTimeData;
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

	MovementActionItemTrigger getData() {
		return data;
	}

	void setData(MovementActionItemTrigger data) {
		this.data = data;
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
	
//	public int getOriginalAlpha() {
//		return originalAlpha;
//	}
//
//	public void setOriginalAlpha(int originalAlpha) {
//		this.originalAlpha = originalAlpha;
//	}
//
//	public int getAlpha() {
//		return alpha;
//	}
//
//	public void setAlpha(int alpha) {
//		this.alpha = alpha;
//	}
//
//	public float getOffsetAlphaByOnceTrigger() {
//		return offsetAlphaByOnceTrigger;
//	}
//
//	public void setOffsetAlphaByOnceTrigger(float offsetAlphaByOnceTrigger) {
//		this.offsetAlphaByOnceTrigger = offsetAlphaByOnceTrigger;
//	}

	public void ggg() {
//		if(originalAlpha==NO_ORGINAL_ALPHA)
//			originalAlpha = info.getSprite().getAlpha();
//		else
//			info.getSprite().setAlpha(originalAlpha);
		
		switch (scaleType) {
		case ScaleTo:
			if(this.scaleX!=NO_SCALE){
				float originalScaleX = this.getSprite().getXscale();
				float offsetScaleX = 0;
				offsetScaleX = scaleX - originalScaleX;
				
				offsetScaleXByOnceTrigger = offsetScaleX/(this.getTotal()/this.getDelay());
			}
			if(this.scaleY!=NO_SCALE){
				float originalScaleY = this.getSprite().getYscale();
				float offsetScaleY = 0;
				offsetScaleY = scaleY - originalScaleY;

				offsetScaleYByOnceTrigger = offsetScaleY/(this.getTotal()/this.getDelay());
			}
			break;
		case ScaleBy:
			if(this.scaleX!=NO_SCALE){
				float offsetScaleX = 0;
				offsetScaleX = scaleX;
				
				offsetScaleXByOnceTrigger = offsetScaleX/(this.getTotal()/this.getDelay());
			}
			if(this.scaleY!=NO_SCALE){
				float offsetScaleY = 0;
				offsetScaleY = scaleY;

				offsetScaleYByOnceTrigger = offsetScaleY/(this.getTotal()/this.getDelay());
			}
			break;
		case ScaleToWith:
			if(this.scaleX!=NO_SCALE){
				float originalScaleX = this.getSprite().getXscale();
				float offsetScaleX = 0;
				if(originalScaleX<0){
					offsetScaleX = -1*scaleX - originalScaleX;
				}else{
					offsetScaleX = scaleX - originalScaleX;
				}
				
				offsetScaleXByOnceTrigger = offsetScaleX/(this.getTotal()/this.getDelay());
			}
			if(this.scaleY!=NO_SCALE){
				float originalScaleY = this.getSprite().getYscale();
				float offsetScaleY = 0;
				if(originalScaleY<0){
					offsetScaleY = -1*scaleY - originalScaleY;
				}else{
					offsetScaleY = scaleY - originalScaleY;
				}

				offsetScaleYByOnceTrigger = offsetScaleY/(this.getTotal()/this.getDelay());
			}
			break;
		}
	}
	
	public void didCycleFinish(){
		switch (scaleType) {
		case ScaleTo:
			if(scaleX!=NO_SCALE)
				this.getSprite().setXscale(scaleX);
			if(scaleY!=NO_SCALE)
				this.getSprite().setYscale(scaleY);
			break;
		case ScaleBy:
			break;
		case ScaleToWith:
			if(scaleX!=NO_SCALE)
				this.getSprite().setXscale(scaleX);
			if(scaleY!=NO_SCALE)
				this.getSprite().setYscale(scaleY);
			if(this.scaleX!=NO_SCALE){
				float currentScaleX = this.getSprite().getXscale();
				if(currentScaleX<0){
					this.getSprite().setXscale(scaleX<0?scaleX:-1*scaleX);
				}else{
					this.getSprite().setXscale(scaleX<0?-1*scaleX:scaleX);
				}
			}
			break;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof MovementActionScaleInfo))
			return false;
		MovementActionScaleInfo info = (MovementActionScaleInfo) obj;
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
	public MovementActionScaleInfo clone() {
		MovementActionScaleInfo info = new MovementActionScaleInfo(total, delay, dx, dy,
				description, sprite,
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

	@Override
	public void update(TimerOnTickListener timerOnTickListener) {
		if(offsetScaleXByOnceTrigger!=0)
			this.getSprite().setXscale(this.getSprite().getXscale()+offsetScaleXByOnceTrigger);
		if(offsetScaleYByOnceTrigger!=0)
			this.getSprite().setYscale(this.getSprite().getYscale()+offsetScaleYByOnceTrigger);
		Log.e("scale by scale action", "xScale:"+this.getSprite().getXscale() + "yScale:" +this.getSprite().getYscale());
	}

	@Override
	public void update(float t, TimerOnTickListener timerOnTickListener) {
//		int offsetAlpha= alpha - originalAlpha;
//		offsetAlphaByOnceTrigger = (float) (offsetAlpha*t);
//		Log.e("offsetAlpha", offsetAlpha+" "+ t);
//		getSprite().setAlpha(originalAlpha + (int)offsetAlphaByOnceTrigger);

		if (offsetScaleXByOnceTrigger != 0)
			this.getSprite().setXscale(
					this.getSprite().getXscale() + offsetScaleXByOnceTrigger);
		if (offsetScaleYByOnceTrigger != 0)
			this.getSprite().setYscale(
					this.getSprite().getYscale() + offsetScaleYByOnceTrigger);
		Log.e("scale by scale action", "xScale:"+this.getSprite().getXscale() + "yScale:" +this.getSprite().getYscale());	
	}
	
//	/**
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
////		if(this.rotationController!=null)
////			this.rotationController.reset(info);
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
