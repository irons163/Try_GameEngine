package com.example.try_gameengine.action;

import android.graphics.Canvas;
import android.graphics.PointF;

public interface ICircleController extends IRotationController{
	public void setMediator(Mediator mediator);
	public void setCircleController(ICircleController rotationController);
	public PointF action(float mx, float my, float angle);
	public float getX();
	public float getY();
	public void setX(float mx);
	public void setY(float my);
	public void setAngle(float angle);
	public void genSpeed();
	public void setmX(float mx);
	public void setmY(float my);
	public float getmX();
	public float getmY();
	public void draw(Canvas canvas);
}
