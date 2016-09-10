package com.example.try_gameengine.action.visitor;

import com.example.try_gameengine.action.MovementAction;

/**
 * {@code IMovementActionVisitor} is a interface for the movementAction visitor. Can easy access one of movements composites.
 * @author irons
 *
 */
public interface IMovementActionVisitor {

	/**
	 * {@code visitComposite} visit all the composite part of target movementAction.
	 * @param movementAction 
	 * 			target for visit.
	 */
	public void visitComposite(MovementAction movementAction);
	
	/**
	 * {@code visitLeaf} visit all the leaf part of target movementAction.
	 * @param movementAction 
	 * 			target for visit.
	 */
	public void visitLeaf(MovementAction movementAction);
}
