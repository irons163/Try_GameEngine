package com.example.try_gameengine.remotecontroller.custom;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.framework.ALayer;

import android.view.MotionEvent;

public class CustomRemoteControl {
	List<CustomCommand> onCommands;
	List<CustomCommand> offCommands;

	public CustomRemoteControl() {
		onCommands = new ArrayList<CustomCommand>();
		offCommands = new ArrayList<CustomCommand>();
//		CustomCommand noCommand = null;
//		for(int i=0; i<7; i++){
//			onCommands[i] = noCommand;
//			offCommands[i] = noCommand;
//		}
	}
	
	public void addCommand(CustomCommand onCommand, CustomCommand offCommand){
		onCommands.add(onCommand);
		offCommands.add(onCommand);
	}
	
	public CustomTouch executePressDown(float x, float y, int motionEventPointerId, MotionEvent event){
		ALayer commandType = null;
		for(CustomCommand command : onCommands){
			if(command.checkExecute(x, y, event)){
				commandType = command.execute();
				command.setMotionEventPointerId(motionEventPointerId);
//				break;
				return new CustomTouch(commandType, event);
			}
		}
		return null;
	}
	
	public CustomTouch executePressUp(float x, float y, int motionEventPointerId, MotionEvent event){
		ALayer commandType = null;
		
		for(CustomCommand command : offCommands){
			if(command.checkExecute(x, y, event)){
				commandType = command.execute();
				return new CustomTouch(commandType, event);
			}
		}

		return null;
	}
}
