package com.example.try_gameengine.remotecontroller.custome;

import android.view.MotionEvent;

public class Custom4D2FRemoteControl {
	Custom4D2FCommand slot;
	
	Custom4D2FCommand[] onCommands;
	Custom4D2FCommand[] offCommands;

	public Custom4D2FRemoteControl() {
		onCommands = new Custom4D2FCommand[7];
		offCommands = new Custom4D2FCommand[7];
		Custom4D2FCommand noCommand = new Custom4D2FNoCommand();
		for(int i=0; i<7; i++){
			onCommands[i] = noCommand;
			offCommands[i] = noCommand;
		}
	}
	
	public void setCommand(Custom4D2FCommand command){
		slot = command;
	}
	
	public void setCommand(int slot, Custom4D2FCommand onCommand, Custom4D2FCommand offCommand){
		onCommands[slot] = onCommand;
		offCommands[slot] = offCommand;
	}
	
	public Custom4D2FCommandType executePressDown(float x, float y, int motionEventPointerId, MotionEvent event){
		Custom4D2FCommandType commandType = Custom4D2FCommandType.None;
		for(Custom4D2FCommand command : onCommands){
			if(command.checkExecute(x, y, event)){
				commandType = command.execute();
				command.setMotionEventPointerId(motionEventPointerId);
				break;
			}
		}
		return commandType;
	}
	
	public Custom4D2FCommandType executePressUp(float x, float y, int motionEventPointerId, MotionEvent event){
		Custom4D2FCommandType commandType = Custom4D2FCommandType.None;
		
		for(Custom4D2FCommand command : offCommands){
			if(command.checkExecute(x, y, event)){
				commandType = command.execute();
				break;
			}
		}

		return commandType;
	}
}
