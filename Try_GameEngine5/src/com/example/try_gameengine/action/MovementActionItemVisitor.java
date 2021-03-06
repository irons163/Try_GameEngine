package com.example.try_gameengine.action;

import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementDecorator;
import com.example.try_gameengine.action.visitor.IMovementActionVisitor;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.test.MovementActionDecoratorFactory;

/**
 * @author irons
 *
 */
public class MovementActionItemVisitor implements IMovementActionVisitor{
	private MovementDecorator movementDecorator;
	
	/**
	 * MovementActionItemVisitor
	 * @param movementDecorator
	 */
	public MovementActionItemVisitor(MovementDecorator movementDecorator) {
		this.movementDecorator = movementDecorator;
	}
	
	@Override
	public void visitComposite(MovementAction movementAction) {
//		if(movementAction.getInfo()!=null)
//			movementDecorator.coreCalculationMovementActionInfo(movementAction);	
	}

	@Override
	public void visitLeaf(MovementAction movementAction) {
		if(movementAction.getInfo()!=null){
			movementDecorator.coreCalculationMovementActionInfo(movementAction);
		}
	}

}
