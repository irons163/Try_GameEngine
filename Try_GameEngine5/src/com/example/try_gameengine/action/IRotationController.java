package com.example.try_gameengine.action;


public interface IRotationController {
	public void start(MovementActionInfo info);
	public float getRotation();
	public void setRotation(float rotation);
	public void execute(MovementActionInfo info);
	public void execute(MovementActionInfo info, float t);
	public void reset(MovementActionInfo info);
	public void isInverseAngel();
	public void isCyclePath();
	public void isInversePath();
	public void isWavePath();
	public void isSlopeWavePath();
	public MathUtil getMathUtil();
	public void setMathUtil(MathUtil mathUtil);
	public IRotationController copyNewRotationController();
}
