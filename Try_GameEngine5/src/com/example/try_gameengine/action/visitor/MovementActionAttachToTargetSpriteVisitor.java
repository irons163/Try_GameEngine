package com.example.try_gameengine.action.visitor;

import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.framework.Sprite;

public class MovementActionAttachToTargetSpriteVisitor implements IMovementActionVisitor{
	private Sprite sprite;
	
	public MovementActionAttachToTargetSpriteVisitor(Sprite sprite) {
		// TODO Auto-generated constructor stub
		this.sprite = sprite;
	}
	
	@Override
	public void visitComposite(MovementAction movementAction) {
		// TODO Auto-generated method stub
		if(movementAction.getInfo()!=null){
			movementAction.getInfo().setSprite(sprite);
		}	
	}

	@Override
	public void visitLeaf(MovementAction movementAction) {
		// TODO Auto-generated method stub
		if(movementAction.getInfo()!=null){
			movementAction.getInfo().setSprite(sprite);
		}
	}

}
