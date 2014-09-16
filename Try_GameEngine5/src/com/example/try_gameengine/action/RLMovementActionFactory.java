package com.example.try_gameengine.action;

import android.util.Log;

public class RLMovementActionFactory extends MovementActionFactory{

	@Override
	public MovementAction createMovementAction() {
		// TODO Auto-generated method stub
		MovementAction newAction;
		
		if(action==null){
			newAction = new DoubleDecorator(new MovementActionSet());
		}else
			newAction = new DoubleDecorator(new MovementActionSet());
			newAction.addMovementAction(new DoubleDecorator(new MovementActionItem(1000, 200, 10, 0, "R")) );
			newAction.addMovementAction(new DoubleDecorator(new MovementActionItem(1000, 200, -10, 0, "L")) );
		
			if(action!=null){
				action.addMovementAction(newAction);
				newAction =	action;
			}
		
		Log.i("MovementDescription", newAction.getDescription());
		
		return newAction;
	}

}
