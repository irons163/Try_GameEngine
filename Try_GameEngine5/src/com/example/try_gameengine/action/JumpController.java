package com.example.try_gameengine.action;

public class JumpController implements IGravityController {
	boolean firstExecute = true;
	MathUtil mathUtil;
	private float height, distanceX, distanceY;
	private float mx, my;

	public JumpController(float height, float distanceX, float distanceY) {
		// TODO Auto-generated constructor stub
		mathUtil = new MathUtil();
		this.height = height;
		this.distanceX = distanceX;
		this.distanceY = distanceY;
	}
	
	@Override
	public void start(MovementActionInfo info) {
		// TODO Auto-generated method stub
//		offsetRotationPerUpdate = (float) (rotation*info.data.getValueOfFactorByUpdate());
		
//		offsetRotationPerUpdate = (float) (rotation*info.data.getValueOfFactorByUpdate());
		
		float dx = info.getDx();
		float dy = info.getDy();
		
		mx = 0;
		my = 0;
		
//		if (firstExecute) {
//			origineDx = info.getDx();
//			origineDy = info.getDy();
//
			

			
//
//			else if (isInversePath) {
//				mathUtil.inversePath();
//				dx = mathUtil.getSpeedX();
//			}
//
//			else if (isWavePath) {
//				mathUtil.setXY(dx, dy);
//				mathUtil.setInitSpeed(mathUtil.genTotalSpeed());
//				mathUtil.genAngle();
//				mathUtil.wavePath();
//				mathUtil.genSpeedXY();
//			}
//
//			else if (isSlopeWavePath) {
//				mathUtil.slopeWavePath();
//			}
//
//			else {
//				mathUtil.setXY(dx, dy);
//				mathUtil.genAngle();
//
//			}
//
//			mathUtil.initGravity();
//
//			getMathUtil().genJumpVx(dx);
////			getMathUtil().genJumpVx(0);
//			float newVx = getMathUtil().vx;
//			info.setDx(newVx);
////			getMathUtil().vx = newVx;
//
//			firstExecute = false;
//		}

		firstExecute = false;
	}

	@Override
	public void execute(MovementActionInfo info, float t) {
		float frac = t % 1.0f;
		float y = height * 4 * frac * (1 - frac);
		y += distanceY * t;
		float x = distanceX * t;
		
		float dx = x - mx;
		float dy = y - my;
		
		mx = x;
		my = y;
		
		info.setDx(dx);
		info.setDy(dy);
	}
	
	@Override
	public void execute(MovementActionInfo info) {
		// TODO Auto-generated method stub
		execute(info, (float) ((info.data.getActivedValueForLatestUpdated()
				+ info.data.getShouldActiveIntervalValue()) / (double)info.data.getShouldActiveTotalValue()));
	}

	@Override
	public void reset(MovementActionInfo info) {
		// TODO Auto-generated method stub
		mx = 0;
		my = 0;
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
		if (isInverseAngel) {
//			height = -distanceY + height;
			distanceY = -distanceY;
		}
	}

	@Override
	public void isCyclePath() {
		// TODO Auto-generated method stub
		this.isCyclePath = true;
		if (isCyclePath) {
			height = -height;
			distanceX = -distanceX;
			distanceY = -distanceY;
		}
	}

	@Override
	public void isInversePath() {
		// TODO Auto-generated method stub
		isInversePath = true;
		if (isInversePath) {
//			height = -distanceY + height;
			distanceX = -distanceX;
			distanceY = -distanceY;
		}
	}

	@Override
	public void isWavePath() {
		// TODO Auto-generated method stub
		isWavePath = true;
		if (isWavePath) {
			height = -height;
			distanceY = -distanceY;
		}
	}

	@Override
	public void isSlopeWavePath() {
		// TODO Auto-generated method stub
		isSlopeWavePath = true;
		if (isSlopeWavePath) {
//			height = -distanceY + height;
			height = -height;
//			distanceY = -distanceY;
		}
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
		return new JumpController(height, distanceX, distanceY);
	}
}
