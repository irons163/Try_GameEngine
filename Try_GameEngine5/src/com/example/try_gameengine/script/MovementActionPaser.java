package com.example.try_gameengine.script;

import com.example.try_gameengine.action.CopyMoveDecorator;
import com.example.try_gameengine.action.DoubleDecorator;
import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.MovementActionItem;
import com.example.try_gameengine.action.MovementActionItemBaseReugularFPS;
import com.example.try_gameengine.action.MovementActionSet;
import com.example.try_gameengine.action.MovementAtionController;
import com.example.try_gameengine.framework.Sprite;

public class MovementActionPaser {

	public void paser(String s, final Sprite sprite){
		
		MovementAction movementAction = null;
		
		while(!s.equals("\n")){
			if(s.equals("Set")){
				movementAction = new MovementActionSet();
			}else if(s.equals("RFPS")){
//				movementAction = new MovementActionItemBaseReugularFPS(millisTotal, millisDelay, dx, dy, description)
//				MovementAtionController actionController = new MovementAtionController();
//				movementAction.setTimerOnTickListener(new MovementAction.TimerOnTickListener() {
//					
//					@Override
//					public void onTick(float dx, float dy) {
//						// TODO Auto-generated method stub
//						sprite.move(dx, dy);
//					}
//				});
			}else if(s.equals("RFPS")){
//				movementAction = new MovementActionItem(millisTotal, millisDelay, dx, dy);
			}else if(s.equals("RFPS")){
				movementAction = new DoubleDecorator(movementAction);
			}else if(s.equals("RFPS")){
				movementAction = new CopyMoveDecorator(movementAction);
			}
		
		}
		
	}
	
}
