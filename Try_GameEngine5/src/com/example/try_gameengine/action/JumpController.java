package com.example.try_gameengine.action;

import android.graphics.PointF;

public class JumpController implements IGravityController {
	boolean firstExecute = true;
	MathUtil mathUtil;
	private float height, distanceX, distanceY;
	private float mx, my;
	PathType pathType;
	
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
		
		if (pathType == PathType.REFLECTION_PATH_BY_HORIZONTAL_MIRROR) {
			distanceY = -distanceY;
		}
		else if (pathType == PathType.REFLECTION_PATH_BY_VERTICAL_MIRROR) {
			mathUtil.setDeltaTime(info.getTotal()/1000f);
			PointF vxy = mathUtil.genVxVy();
			mathUtil.vx = vxy.x;
			mathUtil.vy = vxy.y;
			mathUtil.reflectionByVerticalMirror();
		}
		else if (pathType == PathType.CYCLE_PATH) {
			height = -height;
			distanceX = -distanceX;
			distanceY = -distanceY;
		}
		else if (pathType == PathType.INVERSE_PATH) {
			distanceX = -distanceX;
			distanceY = -distanceY;
		}
		else if (pathType == PathType.WAVE_PATH) {
			height = -height;
			distanceY = -distanceY;
		}
		else if (pathType == PathType.WAVE_SLOPE_PATH) {
			height = -height;
		}

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

	@Override
	public void setPathType(PathType pathType) {
		// TODO Auto-generated method stub
		this.pathType = pathType;
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
