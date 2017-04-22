package com.example.try_gameengine.action;

import com.example.try_gameengine.action.IGravityController.PathType;


public class GravityReflectionPathByVerticalMirrorAppendDecorator extends
		CopyMoveDecorator {
	
	public GravityReflectionPathByVerticalMirrorAppendDecorator(MovementActionItemMoveByGravity action) {
		super(action);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected MovementAction coreCalculationMovementActionInfo(
			MovementAction action) {
		MovementActionItemMoveByGravity copy = (MovementActionItemMoveByGravity) super.coreCalculationMovementActionInfo(action);
		copy.setPathType(PathType.REFLECTION_PATH_BY_VERTICAL_MIRROR);
		return copy;
	}
}
