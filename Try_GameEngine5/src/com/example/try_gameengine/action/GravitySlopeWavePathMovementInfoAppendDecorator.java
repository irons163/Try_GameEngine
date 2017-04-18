package com.example.try_gameengine.action;


public class GravitySlopeWavePathMovementInfoAppendDecorator extends
		CopyMoveDecorator {
	
	public GravitySlopeWavePathMovementInfoAppendDecorator(MovementActionItemMoveByGravity action) {
		super(action);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected MovementAction coreCalculationMovementActionInfo(
			MovementAction action) {
		MovementActionItemMoveByGravity copy = (MovementActionItemMoveByGravity) super.coreCalculationMovementActionInfo(action);
		copy.isSlopeWavePath();
		return copy;
	}
}
