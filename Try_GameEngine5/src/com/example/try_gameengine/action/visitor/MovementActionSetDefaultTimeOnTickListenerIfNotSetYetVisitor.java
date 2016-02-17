package com.example.try_gameengine.action.visitor;

import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener;
import com.example.try_gameengine.framework.Sprite;

public class MovementActionSetDefaultTimeOnTickListenerIfNotSetYetVisitor implements IMovementActionVisitor{
	private Sprite sprite;
	private TimerOnTickListener defaultTimerOnTickListener = new MovementAction.TimerOnTickListener() {
		
		@Override
		public void onTick(float dx, float dy) {
			// TODO Auto-generated method stub
			sprite.move(dx, dy);
		}
	};
	
	public MovementActionSetDefaultTimeOnTickListenerIfNotSetYetVisitor(Sprite sprite) {
		// TODO Auto-generated constructor stub
		this.sprite = sprite;
	}
	
	@Override
	public void visitComposite(MovementAction movementAction) {
		// TODO Auto-generated method stub
		if(movementAction.getTimerOnTickListener()==null){
			movementAction.setTimerOnTickListener(defaultTimerOnTickListener);
		}
	}

	@Override
	public void visitLeaf(MovementAction movementAction) {
		// TODO Auto-generated method stub
		if(movementAction.getTimerOnTickListener()==null){
			movementAction.setTimerOnTickListener(defaultTimerOnTickListener);
		}
	}

}
