package com.example.try_gameengine.action;

public class GravityCyclePathMovementInfoAppendDecorator extends
		CopyMoveDecorator {

	public GravityCyclePathMovementInfoAppendDecorator(MovementActionItemMoveByGravity action) {
		super(action);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected MovementAction coreCalculationMovementActionInfo(
			MovementAction action) {
		MovementActionItemMoveByGravity copy = (MovementActionItemMoveByGravity) super.coreCalculationMovementActionInfo(action);
		copy.isCyclePath();
		return copy;
	}
}
