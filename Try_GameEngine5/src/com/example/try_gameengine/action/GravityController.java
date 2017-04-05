package com.example.try_gameengine.action;

import java.util.Vector;

import android.graphics.PointF;
import android.util.Log;

public class GravityController implements IGravityController {
	boolean firstExecute = true;
	MathUtil mathUtil;
	private float mx, my, distanceX, height, distanceY;
	private PointF vectorXY = new PointF();
	GravityType gravityType;
	
	private enum GravityType{
		KNOWN_VECTOR,
		KNOWN_DISTANCE_X,
		KNOWN_HEIGHT
	}

	public GravityController(PointF vectorXY) {
		mathUtil = new MathUtil();
		this.vectorXY.set(vectorXY.x, vectorXY.y);
		gravityType = GravityType.KNOWN_VECTOR;
	}
	
	public GravityController(float vy, float distanceX) {
		mathUtil = new MathUtil();
		this.vectorXY.y = vy;
		this.distanceX = distanceX;
		gravityType = GravityType.KNOWN_DISTANCE_X;
	}
	
	public GravityController(float height, float distanceX, float distanceY) {
		mathUtil = new MathUtil();
		this.height = height;
		this.distanceX = distanceX;
		this.distanceY = distanceY;
		gravityType = GravityType.KNOWN_HEIGHT;
	}
	
//	public GravityController(float height, float distanceX) {
//		mathUtil = new MathUtil();
//	}
	
	public void setAy(float ay){
		mathUtil.setAy(ay);
	}
	
	public float getAy(){
		return mathUtil.getAy();
	}
	
	public float getVx() {
		return vectorXY.x;
	}

	public float getVy() {
		return vectorXY.y;
	}

	public float getDistanceX() {
		return distanceX;
	}

	public void setVx(float vx) {
		vectorXY.x = vx;
	}

	public void setVy(float vy) {
		vectorXY.y = vy;
	}

	public void setDistanceX(float distanceX) {
		this.distanceX = distanceX;
	}

	@Override
	public void start(MovementActionInfo info) {
		// TODO Auto-generated method stub
//		offsetRotationPerUpdate = (float) (rotation*info.data.getValueOfFactorByUpdate());
		
//		float dx = info.getDx();
//		float dy = info.getDy();
		
		mx = 0;
		my = 0;
		
		float dx = vectorXY.x;
		float dy = vectorXY.y;
		
		if (firstExecute) {
//			long millisTotal = info.getTotal();
//			long millisDelay = info.getDelay();
//			float x = millisDelay / millisTotal;
//			float tx = origineDx * x;
//			float ty = origineDy * x;

			if (isInverseAngel) {
				mathUtil.inverseAngel();
			}

			else if (isCyclePath) {
				mathUtil.setXY(dx, dy);
				mathUtil.genAngle();
				mathUtil.cyclePath();
			}

			else if (isInversePath) {
				mathUtil.inversePath();
				dx = mathUtil.getSpeedX();
			}

			else if (isWavePath) {
				mathUtil.setXY(dx, dy);
				mathUtil.setInitSpeed(mathUtil.genTotalSpeed());
				mathUtil.genAngle();
				mathUtil.wavePath();
				mathUtil.genSpeedXY();
			}

			else if (isSlopeWavePath) {
				mathUtil.slopeWavePath();
			}

			else {
				mathUtil.setXY(dx, dy);
				mathUtil.genAngle();

			}

			mathUtil.initGravity();

			if(distanceX!=0){
				getMathUtil().genJumpVx(distanceX);
	//			getMathUtil().genJumpVx(0);
				float newVx = getMathUtil().vx;
//				info.setDx(newVx);
				vectorXY.x = newVx;
	//			getMathUtil().vx = newVx;
				mathUtil.setXY(vectorXY.x, dy);
				mathUtil.initGravity();
			}
			
			firstExecute = false;
		}

		firstExecute = false;
	}

	@Override
	public void execute(MovementActionInfo info, float t) {
//		float dx = info.getDx();
//		float dy = info.getDy();

//		mathUtil.setDeltaTime(info.getDelay()/1000f*t);
		mathUtil.setXY(vectorXY.x, vectorXY.y);
		mathUtil.initGravity();
		mathUtil.setDeltaTime(info.getTotal()/1000f*t);
//		mathUtil.genGravity();
		PointF deltaXY = mathUtil.genDeltaXY();
		float dx = deltaXY.x;
		float dy = deltaXY.y;
		
		float newDx = dx - mx;
		float newDy = dy - my;
		
		mx = dx;
		my = dy;
		
		Log.e("dy", dy+"");
		
		info.setDx(newDx);
		info.setDy(newDy);
	}
	
	@Override
	public void execute(MovementActionInfo info) {
		// TODO Auto-generated method stub
//		execute(info, 1f);
		execute(info, (float) ((info.data.getActivedValueForLatestUpdated()
				+ info.data.getShouldActiveIntervalValue()) / (double)info.data.getShouldActiveTotalValue()));
	}

	@Override
	public void reset(MovementActionInfo info) {
		// TODO Auto-generated method stub
		mathUtil.reset();
		firstExecute = true;
	}

	boolean isInverseAngel = false;
	boolean isCyclePath = false;
	boolean isInversePath = false;
	boolean isWavePath = false;
	boolean isSlopeWavePath = false;

	@Override
	public void isInverseAngel() {
		// TODO Auto-generated method stub
		isInverseAngel = true;
	}

	@Override
	public void isCyclePath() {
		// TODO Auto-generated method stub
		this.isCyclePath = true;
	}

	@Override
	public void isInversePath() {
		// TODO Auto-generated method stub
		isInversePath = true;
	}

	@Override
	public void isWavePath() {
		// TODO Auto-generated method stub
		isWavePath = true;
	}

	@Override
	public void isSlopeWavePath() {
		// TODO Auto-generated method stub
		isSlopeWavePath = true;
	}

	@Override
	public MathUtil getMathUtil() {
		// TODO Auto-generated method stub
		return mathUtil;
	}

	@Override
	public void setMathUtil(MathUtil mathUtil) {
		// TODO Auto-generated method stub
		this.mathUtil = mathUtil;
	}
	
	@Override
	public IGravityController copyNewGravityController() {
		// TODO Auto-generated method stub
		IGravityController gravityController = null;
		
		if(gravityType == GravityType.KNOWN_VECTOR){
			gravityController = new GravityController(vectorXY);
		}else if(gravityType == GravityType.KNOWN_VECTOR){
			gravityController = new GravityController(getVy(), getDistanceX());
		}else if(gravityType == GravityType.KNOWN_VECTOR){
			gravityController = new GravityController(height, distanceX, distanceY);
		}
		return gravityController;
	}
}
