package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

import com.example.try_gameengine.action.visitor.IMovementActionVisitor;
import com.example.try_gameengine.action.visitor.MovementActionAttachToTargetSpriteVisitor;
import com.example.try_gameengine.action.visitor.MovementActionNoRepeatSpriteActionVisitor;
import com.example.try_gameengine.action.visitor.MovementActionObjectStructure;
import com.example.try_gameengine.action.visitor.MovementActionSetDefaultTimeOnTickListenerIfNotSetYetVisitor;
import com.example.try_gameengine.framework.Config;
import com.example.try_gameengine.framework.Sprite;
import com.rits.cloning.Cloner;

/**
 * {@code MAction2} has a set of methods to create many useful MovementActions with no thread.
 * @author irons
 *
 */
public class MAction2 {
	/**
	 * sequence create lots of action with no thread.
	 * @param movementActions 
	 * 			the array of movementaciotns.
//	 * @return {@code MovementAction}.
	 */
	public static MovementAction sequence(MovementAction[] movementActions){
		MovementAction movementActionSetWithOutThread = new MovementActionSetWithOutThread();

		for(int i = 0; i < movementActions.length; i++){
			movementActionSetWithOutThread.addMovementAction(movementActions[i]);
		}
		return movementActionSetWithOutThread;
	}
}
