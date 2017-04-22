package com.example.try_gameengine.test;

import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionFactory;
import com.example.try_gameengine.action.MovementActionItemCountDownTimer;
import com.example.try_gameengine.action.MovementActionSetWithThread;

public class LRMovementActionFactory extends MovementActionFactory{

	@Override
	public MovementAction createMovementAction() {
		// TODO Auto-generated method stub
		if(action==null)
			action = new MovementActionSetWithThread();	
		
		action.addMovementAction(new MovementActionItemCountDownTimer(5000, 1000, -10, 0));
		action.addMovementAction(new MovementActionItemCountDownTimer(5000, 1000, 10, 0));
		return action;
	}

}
