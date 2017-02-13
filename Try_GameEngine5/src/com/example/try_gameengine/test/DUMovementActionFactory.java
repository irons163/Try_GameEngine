package com.example.try_gameengine.test;

import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionFactory;
import com.example.try_gameengine.action.MovementActionItemCountDownTimer;
import com.example.try_gameengine.action.MovementActionSet;

public class DUMovementActionFactory extends MovementActionFactory{

	@Override
	public MovementAction createMovementAction() {
		// TODO Auto-generated method stub
		if(action==null)
			action = new MovementActionSet();
		action.addMovementAction(new MovementActionItemCountDownTimer(30000, 1000, 0, 10));
		action.addMovementAction(new MovementActionItemCountDownTimer(30000, 1000, 0, -10));
		return action;
	}

}