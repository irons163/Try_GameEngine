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

public class MovementActionAlphaInfo extends MovementActionInfo{
	static final int NO_ORGINAL_ALPHA = -1;
	private int originalAlpha;
	private int alpha;
	private float offsetAlphaByOnceTrigger;
	
	/**
	 * @param total
	 * @param delay
	 * @param dx
	 * @param dy
	 */
	public MovementActionAlphaInfo(long total, long delay, float dx, float dy) {
		this(total, delay, dx, dy, null);
	}

	/**
	 * @param total
	 * @param delay
	 * @param dx
	 * @param dy
	 * @param description
	 */
	public MovementActionAlphaInfo(long total, long delay, float dx, float dy,
			String description) {
		this(total, delay, dx, dy, description, null, null);
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
	public MovementActionAlphaInfo(long total, long delay, float dx, float dy,
			String description, Sprite sprite, String spriteActionName) {
		super(total, delay, dx, dy, description, sprite, spriteActionName);
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
	
	/**
	 * @param millisTotal
	 * @param alpha
	 */
	public MovementActionAlphaInfo(long millisTotal, int alpha){
		this(millisTotal, 1, NO_ORGINAL_ALPHA, alpha, "MovementActionItemAlpha");
	}
	
	/**
	 * @param millisTotal
	 * @param originalAlpha
	 * @param alpha
	 */
	public MovementActionAlphaInfo(long millisTotal, int originalAlpha, int alpha){
		this(millisTotal, 1, originalAlpha, alpha, "MovementActionItemAlpha");
	}
	
	/**
	 * @param triggerTotal
	 * @param triggerInterval
	 * @param alpha
	 */
	public MovementActionAlphaInfo(long triggerTotal, long triggerInterval, int alpha){
		this(triggerTotal, triggerTotal, NO_ORGINAL_ALPHA, alpha, "MovementActionItemAlpha");
	}
	
	/**
	 * @param triggerTotal
	 * @param triggerInterval
	 * @param originalAlpha
	 * @param alpha
	 */
	public MovementActionAlphaInfo(long triggerTotal, long triggerInterval, int originalAlpha, int alpha){
		this(triggerTotal, triggerInterval, originalAlpha, alpha, "MovementActionItemAlpha");
	}

	/**
	 * @param millisTotal
	 * @param millisDelay
	 * @param originalAlpha
	 * @param alpha
	 * @param description
	 */
	public MovementActionAlphaInfo(long millisTotal, long millisDelay, int originalAlpha, int alpha, String description){
		this(millisTotal, millisDelay, 0f, 0f);
		
		this.description = description + ",";
//		this.originalAlpha = originalAlpha;
//		this.alpha = alpha;
		setOriginalAlpha(originalAlpha);
		setAlpha(alpha);
	}
	
	public int getOriginalAlpha() {
		return originalAlpha;
	}

	public void setOriginalAlpha(int originalAlpha) {
		this.originalAlpha = originalAlpha;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public float getOffsetAlphaByOnceTrigger() {
		return offsetAlphaByOnceTrigger;
	}

	public void setOffsetAlphaByOnceTrigger(float offsetAlphaByOnceTrigger) {
		this.offsetAlphaByOnceTrigger = offsetAlphaByOnceTrigger;
	}

	public void ggg() {
		if(originalAlpha==NO_ORGINAL_ALPHA)
			originalAlpha = getSprite().getAlpha();
		else
			getSprite().setAlpha(originalAlpha);
		
		int offsetAlpha= alpha - originalAlpha;
		offsetAlphaByOnceTrigger = (float) (offsetAlpha/((double)getTotal()/getDelay()));
	}
	
	public void didCycleFinish(){
		getSprite().setAlpha(alpha);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof MovementActionAlphaInfo))
			return false;
		MovementActionAlphaInfo info = (MovementActionAlphaInfo) obj;
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
	public MovementActionAlphaInfo clone() {
		MovementActionAlphaInfo info = new MovementActionAlphaInfo(total, delay, dx, dy,
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
		// TODO Auto-generated method stub
//		if(timerOnTickListener!=null){
//			timerOnTickListener.onTick(dx, dy);
//		}else{
			getSprite().setAlpha(originalAlpha + (int)offsetAlphaByOnceTrigger);
//		}
	}

	@Override
	public void update(float t, TimerOnTickListener timerOnTickListener) {
		// TODO Auto-generated method stub
//		double percent = ((double)t)/data.getShouldActiveTotalValue();
//		int offsetAlpha= alpha - originalAlpha;
//		offsetAlphaByOnceTrigger += (float) (offsetAlpha*percent);
		
		int offsetAlpha= alpha - originalAlpha;
		offsetAlphaByOnceTrigger = (float) (offsetAlpha*t);
		Log.e("offsetAlpha", offsetAlpha+" "+ t);
		
//		if(timerOnTickListener!=null){
//			timerOnTickListener.onTick(dx, dy);
//		}else{
			getSprite().setAlpha(originalAlpha + (int)offsetAlphaByOnceTrigger);
//		}

	}
	
	public float getOffsetAlpha(){
		return offsetAlphaByOnceTrigger;
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
