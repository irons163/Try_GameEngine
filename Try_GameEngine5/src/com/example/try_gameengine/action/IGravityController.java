package com.example.try_gameengine.action;

public interface IGravityController {
	public enum PathType{
		NORMAL,
		INVERSE_PATH,
		CYCLE_PATH,
		WAVE_PATH,
		WAVE_SLOPE_PATH,
		REFLECTION_PATH_BY_HORIZONTAL_MIRROR,
		REFLECTION_PATH_BY_VERTICAL_MIRROR
	}
	public void start(MovementActionInfo info);
	public void execute(MovementActionInfo info);
	public void execute(MovementActionInfo info, float t);
	public void reset(MovementActionInfo info);
	public void setPathType(PathType pathType);
	public MathUtil getMathUtil();
	public void setMathUtil(MathUtil mathUtil);
	public IGravityController copyNewGravityController();
}
