package com.example.try_gameengine.action;


public class GravityInverseAngelMovementInfoAppendDecorator extends
		CopyMoveDecorator {
	
	public GravityInverseAngelMovementInfoAppendDecorator(MovementActionItemMoveByGravity action) {
		super(action);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected MovementAction coreCalculationMovementActionInfo(
			MovementAction action) {
		MovementActionItemMoveByGravity copy = (MovementActionItemMoveByGravity) super.coreCalculationMovementActionInfo(action);
		copy.isInverseAngel();
		return copy;
	}
}
