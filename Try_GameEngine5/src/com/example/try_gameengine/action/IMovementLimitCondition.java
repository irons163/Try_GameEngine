package com.example.try_gameengine.action;

public interface IMovementLimitCondition {
	public void initStartCondition(float x, float y, float dx, float dy);
	public void executeLimitCondition(float x, float y, float dx, float dy);
}
