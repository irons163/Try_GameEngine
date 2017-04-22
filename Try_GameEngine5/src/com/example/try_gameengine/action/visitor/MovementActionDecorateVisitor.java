package com.example.try_gameengine.action.visitor;

import com.example.try_gameengine.action.MovementAction;

public class MovementActionDecorateVisitor implements IMovementActionVisitor{
	public MovementActionDecorateVisitor() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void visitComposite(MovementAction movementAction) {
		// TODO Auto-generated method stub
//		movementAction.createMovementActionMemento();
	}

	@Override
	public void visitLeaf(MovementAction movementAction) {
		// TODO Auto-generated method stub
//		movementAction.doIn();
	}

}
