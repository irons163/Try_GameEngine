package com.example.try_gameengine.action.info;

import com.example.try_gameengine.action.IGravityController;
import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionInfo;
import com.example.try_gameengine.action.IGravityController.PathType;
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener;

public class MovementActionMoveByGravityInfo extends MovementActionInfo{
	IGravityController gravityController;
	float dx, dy;
	private float newDx;
	private float newDy;
	
	public MovementActionMoveByGravityInfo(long millisTotal,  IGravityController gravityController) {
		this(millisTotal, 1, gravityController);
		
	}
	
	/**
	 * @param triggerTotal
	 * @param triggerInterval
	 * @param alpha
	 */
	public MovementActionMoveByGravityInfo(long triggerTotal, long triggerInterval, IGravityController gravityController){
		this(triggerTotal, triggerTotal, gravityController, "MovementActionItemAlpha");
	}
	
	/**
	 * @param millisTotal
	 * @param millisDelay
	 * @param originalAlpha
	 * @param alpha
	 * @param description
	 */
	public MovementActionMoveByGravityInfo(long millisTotal, long millisDelay, IGravityController gravityController, String description){
		super(millisTotal, millisDelay, 0f, 0f, description);
		
		this.description = description + ",";
		this.gravityController = gravityController;
	}
	
	@Override
	public void update(TimerOnTickListener timerOnTickListener) {
//		doRotation();
//		doGravity();
		gravityController.execute(this);
		dx = this.getDx();
		dy = this.getDy();
		
		if(timerOnTickListener!=null){
			timerOnTickListener.onTick(dx, dy);
		}else{
			getSprite().move(dx, dy);
		}
	}
	
	@Override
	public void update(float t, TimerOnTickListener timerOnTickListener) {
//		doRotation();
//		doGravity();
		gravityController.execute(this, t);
		dx = this.getDx();
		dy = this.getDy();
//		float newDx = (float) (dx*t);
//		float newDy = (float) (dy*t);
		newDx = (float) (dx);
		newDy = (float) (dy);
		
		if(timerOnTickListener!=null){
			timerOnTickListener.onTick(newDx, newDy);
		}else{
			getSprite().move(newDx, newDy);
		}
	}

	public float getNewDx() {
		return newDx;
	}

	public float getNewDy() {
		return newDy;
	}
	
	@Override
	public void ggg() {
		gravityController.start(this);
	}
	
	@Override
	public void didCycleFinish() {
		// TODO Auto-generated method stub
//		super.didCycleFinish();
	}
	
	public void setPathType(PathType pathType) {
		gravityController.setPathType(pathType);
	}
	
	@Override
	public MovementActionMoveByGravityInfo clone(){
		MovementActionMoveByGravityInfo info = new MovementActionMoveByGravityInfo(getTotal(), getDelay(), gravityController.copyNewGravityController(), description);
		return info;
	}
}
