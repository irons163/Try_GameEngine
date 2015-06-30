package com.example.try_gameengine.action;

import com.example.try_gameengine.BitmapUtil;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Circle2Controller implements IRotationController {
	float rotation;
	float origineDx;
	float origineDy;
	boolean firstExecute = true;
	float initspeedX;
	private float x, y, mx, my;
	private MathUtil mathUtil;
	Circle2Controller rotationController;
	float angle;

	public Circle2Controller(float rotation, float x, float y, float mx,
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
	}

	public Circle2Controller(float rotation, float x, float y, float mx,
			float my, Circle2Controller rotationController) {
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

	public void setCircleController(Circle2Controller rotationController) {
		this.rotationController = rotationController;
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

				float oldmx = rotationController.mx;
				float oldmxy = rotationController.my;

				rotationController.setmX(oldmx + speedx);
				rotationController.setmY(oldmxy + speedy);

				info.setDx(rotationController.mx - oldmx);
				info.setDy(rotationController.my - oldmxy);

			}
		} else {
			synchronized (Circle2Controller.this) {

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
				info.setDx(speedx);
				info.setDy(speedy);
			}
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
		return new Circle2Controller(rotation, x, y, mx, my);
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
		mathUtil.genSpeed();
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
}
