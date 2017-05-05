package com.example.try_gameengine.action.info;

import com.example.try_gameengine.action.IRotationController;
import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionInfo;
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener;

public class MovementActionMoveByCurveInfo extends MovementActionInfo{
	IRotationController rotationController;
	float dx, dy;
	private float newDx;
	private float newDy;
	
	public MovementActionMoveByCurveInfo(long millisTotal, IRotationController rotationController) {
		this(millisTotal, 1, rotationController);
		
	}
	
	/**
	 * @param triggerTotal
	 * @param triggerInterval
	 * @param alpha
	 */
	public MovementActionMoveByCurveInfo(long triggerTotal, long triggerInterval, IRotationController rotationController){
		this(triggerTotal, triggerTotal, rotationController, "MovementActionItemAlpha");
	}
	
	/**
	 * @param millisTotal
	 * @param millisDelay
	 * @param originalAlpha
	 * @param alpha
	 * @param description
	 */
	public MovementActionMoveByCurveInfo(long millisTotal, long millisDelay, IRotationController rotationController, String description){
		super(millisTotal, millisDelay, 0f, 0f, description);
		
		this.description = description + ",";
		this.rotationController = rotationController;
	}
	
	@Override
	public void update(TimerOnTickListener timerOnTickListener) {
//		doRotation();
//		doGravity();
		rotationController.execute(this);
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
		rotationController.execute(this, t);
		dx = this.getDx();
		dy = this.getDy();
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
		rotationController.start(this);
	}
	
	@Override
	public void didCycleFinish() {
		// TODO Auto-generated method stub
//		super.didCycleFinish();
	}
}
