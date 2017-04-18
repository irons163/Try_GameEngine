package com.example.try_gameengine.action;

import com.example.try_gameengine.action.IGravityController.PathType;


public class GravityWaveSlopePathAppendDecorator extends
		CopyMoveDecorator {
	
	public GravityWaveSlopePathAppendDecorator(MovementActionItemMoveByGravity action) {
		super(action);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected MovementAction coreCalculationMovementActionInfo(
			MovementAction action) {
		MovementActionItemMoveByGravity copy = (MovementActionItemMoveByGravity) super.coreCalculationMovementActionInfo(action);
		copy.setPathType(PathType.WAVE_SLOPE_PATH);
		return copy;
	}
}
