package com.example.try_gameengine.action;

import android.graphics.PointF;
import android.util.Log;

public class GravityController implements IGravityController {
	boolean firstExecute = true;
	MathUtil mathUtil, savedMathUtil;
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
		
		firstExecute = true;
		
		if(savedMathUtil==null){
			try {
				savedMathUtil = (MathUtil) mathUtil.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			mathUtil.vx = savedMathUtil.vx;
			mathUtil.vy = savedMathUtil.vy;
			mathUtil.ax = savedMathUtil.ax;
			mathUtil.ay = savedMathUtil.ay;
			mathUtil.deltaTime = savedMathUtil.deltaTime;
		}
		
		mx = 0;
		my = 0;
		
		float dx = vectorXY.x;
		float dy = vectorXY.y;
		
		mathUtil.setXY(dx, dy);
		
		if(distanceX!=0){
			getMathUtil().genJumpSpeedX(distanceX);
//			float newVx0 = getMathUtil().vx;
//			vectorXY.x = newVx0;
		}
		
		if (firstExecute) {
//			long millisTotal = info.getTotal();
//			long millisDelay = info.getDelay();
//			float x = millisDelay / millisTotal;
//			float tx = origineDx * x;
//			float ty = origineDy * x;

			if (isInverseAngel) {
				mathUtil.setDeltaTime(info.getTotal()/1000f);
				PointF vxy = mathUtil.genVxVy();
				mathUtil.vx = vxy.x;
				mathUtil.vy = vxy.y;
				mathUtil.reflectionByHorizontalMirror();
			}

			else if (isCyclePath) {
//				mathUtil.setXY(dx, dy);
//				mathUtil.genAngle();
//				mathUtil.cyclePath();
				mathUtil.cyclePath();
			}

			else if (isInversePath) {
//				mathUtil.inversePath();
//				dx = mathUtil.getSpeedX();
				mathUtil.setDeltaTime(info.getTotal()/1000f);
				PointF vxy = mathUtil.genVxVy();
				mathUtil.vx = vxy.x;
				mathUtil.vy = vxy.y;
				mathUtil.inversePath();
//				dx = mathUtil.getSpeedX();
			}

			else if (isWavePath) {
//				mathUtil.setXY(dx, dy);
//				mathUtil.setInitSpeed(mathUtil.genTotalSpeed());
//				mathUtil.genAngle();
//				mathUtil.setXY(dx, dy);
				mathUtil.wavePath();
//				mathUtil.genSpeedXY();
			}

			else if (isSlopeWavePath) {
				mathUtil.setDeltaTime(info.getTotal()/1000f);
				PointF vxy = mathUtil.genVxVy();
				mathUtil.vx = vxy.x;
				mathUtil.vy = vxy.y;
				mathUtil.slopeWavePath();
			}

			else {
//				mathUtil.setXY(dx, dy);
				mathUtil.genAngle();
			}

			mathUtil.initGravity();
			firstExecute = false;
		}

		firstExecute = false;
	}

	@Override
	public void execute(MovementActionInfo info, float t) {
//		float dx = info.getDx();
//		float dy = info.getDy();

//		mathUtil.setDeltaTime(info.getDelay()/1000f*t);
//		mathUtil.setXY(vectorXY.x, vectorXY.y);
//		mathUtil.initGravity();
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
		}else if(gravityType == GravityType.KNOWN_DISTANCE_X){
			gravityController = new GravityController(getVy(), getDistanceX());
		}else if(gravityType == GravityType.KNOWN_VECTOR){
			gravityController = new GravityController(height, distanceX, distanceY);
		}
		
		try {
			MathUtil copyMathUtil = (MathUtil) mathUtil.clone();
			gravityController.setMathUtil(copyMathUtil);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return gravityController;
	}
}
