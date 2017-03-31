package com.example.try_gameengine.action;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import com.example.try_gameengine.framework.BitmapUtil;

public class CircleController implements IRotationController {
	float rotation;
	float offsetRotationPerUpdate;
//	float origineDx;
//	float origineDy;
	boolean firstExecute = true;
	float initspeedX;
	private float x, y, mx, my;
	private MathUtil mathUtil;
	float angle;

	public CircleController(float rotation, float centerX, float centerY, float targetX, float targetY) {
		this.rotation = rotation;
		this.x = centerX;
		this.y = centerY;
		this.mx = targetX;
		this.my = targetY;
		mathUtil = new MathUtil(mx - x, my - y);
	}
	
	public CircleController(float rotation, float centerX, float centerY) {
		this.rotation = rotation;
		this.x = centerX;
		this.y = centerY;
	}
	
	public void execute(MovementActionInfo info, float t) {
		float offsetRotation = offsetRotationPerUpdate*t;
		
		float originalSpeedx = mathUtil.getSpeedX();
		float originalSpeedy = mathUtil.getSpeedY();
		exe(info, offsetRotation);
		mathUtil.setXY(originalSpeedx, originalSpeedy);
	}

	@Override
	public void execute(MovementActionInfo info) {
		// TODO Auto-generated method stub
//		execute(info, 1);
		float offsetRotation = offsetRotationPerUpdate;
		
		mathUtil.setXY(mx - x, my - y);//need modify
		PointF newMxMy = exe(info, offsetRotation);
		
//		mx = newMxMy.x;
//		my = newMxMy.y;
	}
	
	private PointF exe(MovementActionInfo info, float offsetRotation) {
		
		
		mathUtil.genAngle();
		mathUtil.genSpeedByRotate(offsetRotation);
		
		float speedx = mathUtil.getSpeedX();
		float speedy = mathUtil.getSpeedY();
		float newMx = x + speedx;
		float newMy = y + speedy;
		speedx = newMx - mx;
		speedy = newMy - my;
		
		info.setDx(speedx);
		info.setDy(speedy);
		
		mx = newMx;
		my = newMy;
		
		
		
		return new PointF(newMx, newMy);
	}

	@Override
	public void reset(MovementActionInfo info) {
		// TODO Auto-generated method stub
//		info.setDx(origineDx);
//		info.setDy(origineDy);
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
		return new CircleController(rotation, x, y, mx, my);
	}

	public float getX() {
		return mx;
	}

	public float getY() {
		return my;
	}

	public void setX(float mx) {
		this.x = mx;
	}

	public void setY(float my) {
		this.y = my;
	}

	public void setAngle(float angle) {
		mathUtil.setAngle(mathUtil.getAngle() + angle);
	}

	public void genSpeed() {
		mathUtil.genSpeedXY();
		mx = x + mathUtil.getSpeedX();
		my = y + mathUtil.getSpeedY();
	}

	public void setmX(float mx) {
		this.mx = mx;
	}

	public void setmY(float my) {
		this.my = my;
	}

	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(20);
		paint.setStrokeWidth(20);
		canvas.drawPoint(x + BitmapUtil.redPoint.getWidth() / 2, y
				+ BitmapUtil.redPoint.getHeight() / 2, paint);
		canvas.drawPoint(mx + BitmapUtil.redPoint.getWidth() / 2, my
				+ BitmapUtil.redPoint.getHeight() / 2, paint);
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
		offsetRotationPerUpdate = (float) (rotation*info.data.getValueOfFactorByUpdate());
//		origineDx = info.getDx();
//		origineDy = info.getDy();
		
		if(mathUtil==null){
			this.mx = info.getSprite().getCenterX();
			this.my = info.getSprite().getCenterY();
			mathUtil = new MathUtil();
		}
		
		mathUtil.setXY(mx - x, my - y);
		initspeedX = (float) Math.sqrt((mx - x) * (mx - x) + (my - y)
				* (my - y));
		mathUtil.setInitSpeed(initspeedX);

		firstExecute = false;
	}
}
