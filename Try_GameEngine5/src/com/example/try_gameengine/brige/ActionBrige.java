package com.example.try_gameengine.brige;

import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionItem;
import com.example.try_gameengine.framework.Sprite;

public class ActionBrige {
	
	public void spriteActionBindMovementAction(Sprite sprite){
		
//		Bindsprite.currentAction
		
		MovementAction movementAction = new MovementActionItem(2000, 500, 5, 0);
		
		sprite.getActionName();
//		movementAction.bindSpriteAction();
		
		
		
	}
	
}
