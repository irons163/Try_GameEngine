package com.example.try_gameengine.action;

public class RotationOnceController implements IRotationController{
	float rotation;
	float origineDx;
	float origineDy;
	boolean firstExecute = true;
	
	public RotationOnceController(float rotation) {
		this.rotation = rotation;
	}
	
	@Override
	public void execute(MovementActionInfo info) {
		// TODO Auto-generated method stub
		if(firstExecute){
			origineDx = info.getDx();
			origineDy = info.getDy();
			
			MathUtil mathUtil = new MathUtil(origineDx, origineDy);
			float totalSpeed = mathUtil.genTotalSpeed();
			mathUtil.setInitSpeed(totalSpeed);
			mathUtil.genAngle();
			mathUtil.genSpeedByRotate(rotation);
			
			float dx = mathUtil.getSpeedX();
			float dy = mathUtil.getSpeedY();
			info.setDx(dx);
			info.setDy(dy);
			firstExecute = false;
		}

	}
	@Override
	public void reset(MovementActionInfo info) {
		// TODO Auto-generated method stub
		info.setDx(origineDx);
		info.setDy(origineDy);
		firstExecute = true;
	}

	@Override
	public void setRotation(float rotation) {
		// TODO Auto-generated method stub
		this.rotation = rotation;
	}

	@Override
	public float getRotation() {
		// TODO Auto-generated method stub
		return rotation;
	}

	@Override
	public IRotationController copyNewRotationController() {
		// TODO Auto-generated method stub
		return new RotationOnceController(rotation);
	}

}
