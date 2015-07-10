package com.example.try_gameengine.action.visitor;

import com.example.try_gameengine.action.MovementAction;

public class MovementActionObjectStructure {
	private MovementAction root = null;
	
	public void handleRequest(IMovementActionVisitor movementActionVisitor){
		if(root != null){
			root.accept(movementActionVisitor);
		}
	}
	
	public void setRoot(MovementAction movementAction){
		this.root = movementAction;
	}
}
