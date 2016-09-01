package com.example.try_gameengine.action.visitor;

import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener;
import com.example.try_gameengine.framework.Sprite;

public class MovementActionCreateMementoVisitor implements IMovementActionVisitor{
	public MovementActionCreateMementoVisitor() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void visitComposite(MovementAction movementAction) {
		// TODO Auto-generated method stub
		movementAction.createMovementActionMemento();
	}

	@Override
	public void visitLeaf(MovementAction movementAction) {
		// TODO Auto-generated method stub
		movementAction.createMovementActionMemento();
	}

}
