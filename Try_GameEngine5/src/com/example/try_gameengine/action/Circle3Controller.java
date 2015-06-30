package com.example.try_gameengine.action;

import com.example.try_gameengine.BitmapUtil;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

public class Circle3Controller implements ICircleController {
	float rotation;
	float origineDx;
	float origineDy;
	boolean firstExecute = true;
	float initspeedX;
	float x, y, mx, my;
	private MathUtil mathUtil;
	ICircleController rotationController;
	float angle;
	Mediator mediator;

	public Circle3Controller(float rotation, float x, float y, float mx,
			float my) {
		this.rotation = rotation;
		this.x = x;
		this.y = y;
		this.mx = mx;
		this.my = my;
		mathUtil = new MathUtil(mx - x, my - y);
		initspeedX = (float) Math.sqrt((mx - x) * (mx - x) + (my - y)
				* (my - y));
		mathUtil.setINITSPEEDX(initspeedX);
		// this.mediator = mediator;
	}

	public Circle3Controller(float rotation, float x, float y, float mx,
			float my, Circle3Controller rotationController) {
		this.rotation = rotation;
		this.x = x;
		this.y = y;
		this.mx = mx;
		this.my = my;
		mathUtil = new MathUtil(mx - x, my - y);
		initspeedX = (float) Math.sqrt((mx - x) * (mx - x) + (my - y)
				* (my - y));
		mathUtil.setINITSPEEDX(initspeedX);
		this.rotationController = rotationController;
	}

	@Override
	public void setCircleController(ICircleController rotationController) {
		this.rotationController = rotationController;
	}

	@Override
	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}

	@Override
	public void execute(MovementActionInfo info) {
		// TODO Auto-generated method stub
		if (firstExecute) {
			long millisTotal = info.getTotal();
			long millisDelay = info.getDelay();
			origineDx = info.getDx();
			origineDy = info.getDy();

			float x = millisDelay / millisTotal;

			float tx = origineDx * x;
			float ty = origineDy * x;

			firstExecute = false;
		}

		if (rotationController != null) {
			synchronized (rotationController) {
				mathUtil.genAngle();
				mathUtil.genSpeedByRotate(-10);
				float speedx = mathUtil.getSpeedX();
				float speedy = mathUtil.getSpeedY();
				float newMx = x + speedx;
				float newMy = y + speedy;
				speedx = newMx - mx;
				speedy = newMy - my;
				mx = newMx;
				my = newMy;
				rotationController.setX(mx);
				rotationController.setY(my);
				float oldmx = rotationController.getmX();
				float oldmxy = rotationController.getmY();

				rotationController.setmX(oldmx + speedx);
				rotationController.setmY(oldmxy + speedy);

				info.setDx(rotationController.getmX() - oldmx);
				info.setDy(rotationController.getmY() - oldmxy);
			}
		} else {
			synchronized (Circle3Controller.this) {
				mathUtil.genAngle();
				mathUtil.genSpeedByRotate(rotation);
				float speedx = mathUtil.getSpeedX();
				float speedy = mathUtil.getSpeedY();
				float newMx = x + speedx;
				float newMy = y + speedy;
				speedx = newMx - mx;
				speedy = newMy - my;
				mx = newMx;
				my = newMy;

				info.setDx(speedx);
				info.setDy(speedy);

				mediator.noty(this, mx, my, 0);
			}
		}
	}

	float dx, dy;

	@Override
	public PointF action(float mx, float my, float angle) {
		synchronized (this) {

			float oldx = x;
			float oldy = y;
			x = mx;
			y = my;
			this.mx = this.mx + (x - oldx);
			this.my = this.my + (y - oldy);

			return null;
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
		return new Circle3Controller(rotation, x, y, mx, my);
	}

	@Override
	public float getX() {
		return mx;
	}

	@Override
	public float getY() {
		return my;
	}

	@Override
	public void setX(float mx) {
		this.x = mx;
	}

	@Override
	public void setY(float my) {
		this.y = my;
	}

	@Override
	public void setAngle(float angle) {
		mathUtil.setAngle(mathUtil.getAngle() + angle);
	}

	@Override
	public void genSpeed() {
		mathUtil.genSpeed();
		mx = x + mathUtil.getSpeedX();
		my = y + mathUtil.getSpeedY();
	}

	@Override
	public void setmX(float mx) {
		this.mx = mx;
	}

	@Override
	public void setmY(float my) {
		this.my = my;
	}

	@Override
	public float getmX() {
		return mx;
	}

	@Override
	public float getmY() {
		return my;
	}

	@Override
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
}
