package com.example.try_gameengine.action;

import com.example.try_gameengine.action.visitor.IMovementActionVisitor;

/**
 * {@code MovementDecorator} is a decorator
 * @author irons
 *
 */
public abstract class MovementDecorator extends MovementAction{
	
	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementAction#getDescription()
	 */
	public abstract String getDescription();  
	
	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementAction#accept(com.example.try_gameengine.action.visitor.IMovementActionVisitor)
	 */
	public void accept(IMovementActionVisitor movementActionVisitor){
//		movementActionVisitor.visitComposite(this);
//		for(MovementAction movementAction : actions){
//			movementAction.accept(movementActionVisitor);
//		}
		movementActionVisitor.visitComposite(getAction());
		for(MovementAction movementAction : getAction().getActions()){
			movementAction.accept(movementActionVisitor);
		}
	}
}
