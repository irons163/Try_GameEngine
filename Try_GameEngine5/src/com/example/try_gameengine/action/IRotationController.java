package com.example.try_gameengine.action;

public interface IRotationController {

	public float getRotation();
	public void setRotation(float rotation);
	public void execute(MovementActionInfo info);
	public void execute(MovementActionInfo info, float t);
	public void reset(MovementActionInfo info);
	public IRotationController copyNewRotationController();
}
