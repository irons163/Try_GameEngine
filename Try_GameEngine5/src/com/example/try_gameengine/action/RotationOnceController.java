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
	public void execute(MovementActionInfo info, float t) {
		// TODO Auto-generated method stub
		
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


	@Override
	public MathUtil getMathUtil() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setMathUtil(MathUtil mathUtil) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void isInverseAngel() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void isCyclePath() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void isInversePath() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void isWavePath() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void isSlopeWavePath() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void start(MovementActionInfo info) {
		// TODO Auto-generated method stub
		
	}

}
