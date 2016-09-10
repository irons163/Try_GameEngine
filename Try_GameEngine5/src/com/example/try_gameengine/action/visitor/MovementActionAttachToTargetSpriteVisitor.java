package com.example.try_gameengine.action.visitor;

import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.framework.Sprite;

/**
 * @author irons
 *
 */
public class MovementActionAttachToTargetSpriteVisitor implements IMovementActionVisitor{
	private Sprite sprite;
	
	/**
	 * MovementActionAttachToTargetSpriteVisitor
	 * @param sprite
	 */
	public MovementActionAttachToTargetSpriteVisitor(Sprite sprite) {
		this.sprite = sprite;
	}
	
	@Override
	public void visitComposite(MovementAction movementAction) {
		if(movementAction.getInfo()!=null){
			movementAction.getInfo().setSprite(sprite);
		}	
	}

	@Override
	public void visitLeaf(MovementAction movementAction) {
		if(movementAction.getInfo()!=null){
			movementAction.getInfo().setSprite(sprite);
		}
	}

}
