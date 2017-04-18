package com.example.try_gameengine.action;

public class GravityInversePathMovementInfoAppendDecorator extends CopyMoveDecorator {

	public GravityInversePathMovementInfoAppendDecorator(MovementActionItemMoveByGravity action) {
		super(action);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected MovementAction coreCalculationMovementActionInfo(
			MovementAction action) {
		MovementActionItemMoveByGravity copy = (MovementActionItemMoveByGravity) super.coreCalculationMovementActionInfo(action);
		copy.isInversePath();
		return copy;
	}
}
