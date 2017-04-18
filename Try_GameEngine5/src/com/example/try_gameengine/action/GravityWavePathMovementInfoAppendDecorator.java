package com.example.try_gameengine.action;

public class GravityWavePathMovementInfoAppendDecorator extends
		CopyMoveDecorator {
	
	public GravityWavePathMovementInfoAppendDecorator(MovementActionItemMoveByGravity action) {
		super(action);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected MovementAction coreCalculationMovementActionInfo(
			MovementAction action) {
		MovementActionItemMoveByGravity copy = (MovementActionItemMoveByGravity) super.coreCalculationMovementActionInfo(action);
		copy.isWavePath();
		return copy;
	}
}
