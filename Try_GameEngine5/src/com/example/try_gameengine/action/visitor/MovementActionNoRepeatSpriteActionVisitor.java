package com.example.try_gameengine.action.visitor;

import com.example.try_gameengine.action.MovementAction;

/**
 * @author irons
 *
 */
public class MovementActionNoRepeatSpriteActionVisitor implements IMovementActionVisitor{

	@Override
	public void visitComposite(MovementAction movementAction) {
		// TODO Auto-generated method stub
		movementAction.isRepeatSpriteActionIfMovementActionRepeat = false;
	}

	@Override
	public void visitLeaf(MovementAction movementAction) {
		// TODO Auto-generated method stub
		movementAction.isRepeatSpriteActionIfMovementActionRepeat = false;
	}

}
