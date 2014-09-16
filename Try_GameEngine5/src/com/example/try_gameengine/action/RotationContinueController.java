package com.example.try_gameengine.action;

public class RotationContinueController implements IRotationController{
	float origineDx;
	float origineDy;
	boolean firstExecute = true;
	
	@Override
	public void execute(MovementActionInfo info) {
		// TODO Auto-generated method stub
		if(firstExecute){
			origineDx = info.getDx();
			origineDy = info.getDy();
		}
		float dx = info.getDx() *1.1f;
		info.setDx(dx);
	}

	@Override
	public void reset(MovementActionInfo info) {
		// TODO Auto-generated method stub
		info.setDx(origineDx);
		info.setDy(origineDy);
		firstExecute = true;
	}

	@Override
	public float getRotation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRotation(float rotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IRotationController copyNewRotationController() {
		// TODO Auto-generated method stub
		return new RotationContinueController();
	}

}
