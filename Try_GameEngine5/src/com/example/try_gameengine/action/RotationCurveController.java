package com.example.try_gameengine.action;


public class RotationCurveController implements IRotationController{
	float rotation;
	float origineDx;
	float origineDy;
	boolean firstExecute = true;
	float initspeedX;
	
	public RotationCurveController(float rotation) {
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
			
			initspeedX = (float) Math.sqrt(origineDx*origineDx + origineDy*origineDy);

			firstExecute = false;
		}
		
		float dx = info.getDx();
		float dy = info.getDy();
		MathUtil mathUtil = new MathUtil(dx, dy);
		mathUtil.setInitSpeed(initspeedX);
//		mathUtil.initAngle();
		mathUtil.genAngle();
		mathUtil.genSpeedByRotate(rotation);
//		mathUtil.genSpeed();
		dx = mathUtil.getSpeedX();
		dy = mathUtil.getSpeedY();
		
		info.setDx(dx);
		info.setDy(dy);
	}

	@Override
	public void reset(MovementActionInfo info) {
		info.setDx(origineDx);
		info.setDy(origineDy);
		firstExecute = true;
	}

	@Override
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	@Override
	public float getRotation() {
		return rotation;
	}

	@Override
	public IRotationController copyNewRotationController() {
		return new RotationCurveController(rotation);
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
