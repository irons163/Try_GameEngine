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

public class MAction2 {
	public static MovementAction sequence(MovementAction[] movementActions){
		MovementAction movementActionSetWithOutThread = new MovementActionSetWithOutThread();

		for(int i = 0; i < movementActions.length; i++){
			movementActionSetWithOutThread.addMovementAction(movementActions[i]);
		}
		return movementActionSetWithOutThread;
	}
}
