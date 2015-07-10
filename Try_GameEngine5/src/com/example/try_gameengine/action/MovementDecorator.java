package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.visitor.IMovementActionVisitor;

public abstract class MovementDecorator extends MovementAction{
	protected MovementAction action;
	
	public abstract String getDescription();  
	
	public void accept(IMovementActionVisitor movementActionVisitor){
//		movementActionVisitor.visitComposite(this);
//		for(MovementAction movementAction : actions){
//			movementAction.accept(movementActionVisitor);
//		}
		movementActionVisitor.visitComposite(action);
		for(MovementAction movementAction : action.getAction().getActions()){
			movementAction.accept(movementActionVisitor);
		}
	}
}
