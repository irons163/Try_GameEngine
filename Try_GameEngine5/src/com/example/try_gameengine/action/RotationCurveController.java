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
	public void execute(MovementActionInfo info) {
		// TODO Auto-generated method stub
		if(firstExecute){
			long millisTotal = info.getTotal();
			long millisDelay = info.getDelay();
			origineDx = info.getDx();
			origineDy = info.getDy();
			
			float x = millisDelay / millisTotal;
			
			float tx = origineDx * x;
			float ty = origineDy * x;
			
			initspeedX = (float) Math.sqrt(origineDx*origineDx + origineDy*origineDy);

			firstExecute = false;
		}
		
		float dx = info.getDx();
		float dy = info.getDy();
		MathUtil mathUtil = new MathUtil(dx, dy);
		mathUtil.setINITSPEEDX(initspeedX);
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
		return new RotationCurveController(rotation);
	}

}
