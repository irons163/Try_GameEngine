package com.example.try_gameengine.action.visitor;

import com.example.try_gameengine.action.MovementAction;

public interface IMovementActionVisitor {

	public void visitComposite(MovementAction movementAction);
	
	public void visitLeaf(MovementAction movementAction);
}
