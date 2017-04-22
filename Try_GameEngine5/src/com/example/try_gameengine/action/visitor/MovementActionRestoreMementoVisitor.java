package com.example.try_gameengine.action.visitor;

import com.example.try_gameengine.action.MovementAction;

/**
 * @author irons
 *
 */
public class MovementActionRestoreMementoVisitor implements IMovementActionVisitor{
	public MovementActionRestoreMementoVisitor() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void visitComposite(MovementAction movementAction) {
		// TODO Auto-generated method stub
		movementAction.restoreMovementActionMemento(null);
	}

	@Override
	public void visitLeaf(MovementAction movementAction) {
		// TODO Auto-generated method stub
		movementAction.restoreMovementActionMemento(null);
	}

}
