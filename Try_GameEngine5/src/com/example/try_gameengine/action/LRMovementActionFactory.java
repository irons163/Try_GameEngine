package com.example.try_gameengine.action;

public class LRMovementActionFactory extends MovementActionFactory{

	@Override
	public MovementAction createMovementAction() {
		// TODO Auto-generated method stub
		if(action==null)
			action = new MovementActionSet();	
		
		action.addMovementAction(new MovementActionItem(5000, 1000, -10, 0));
		action.addMovementAction(new MovementActionItem(5000, 1000, 10, 0));
		return action;
	}

}
