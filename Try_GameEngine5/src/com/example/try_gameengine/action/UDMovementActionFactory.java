package com.example.try_gameengine.action;

public class UDMovementActionFactory extends MovementActionFactory{

	@Override
	public MovementAction createMovementAction() {
		// TODO Auto-generated method stub
		if(action==null)
			action = new MovementActionSet();
		action.addMovementAction(new MovementActionItem(30000, 1000, 0, -10));
		action.addMovementAction(new MovementActionItem(30000, 1000, 0, 10));
		return action;
	}

}
