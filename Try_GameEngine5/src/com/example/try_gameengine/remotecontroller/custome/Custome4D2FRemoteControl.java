package com.example.try_gameengine.remotecontroller.custome;

import android.view.MotionEvent;

public class Custome4D2FRemoteControl {
	Custome4D2FCommand slot;
	
	Custome4D2FCommand[] onCommands;
	Custome4D2FCommand[] offCommands;

	public Custome4D2FRemoteControl() {
		onCommands = new Custome4D2FCommand[7];
		offCommands = new Custome4D2FCommand[7];
		Custome4D2FCommand noCommand = new Custome4D2FNoCommand();
		for(int i=0; i<7; i++){
			onCommands[i] = noCommand;
			offCommands[i] = noCommand;
		}
	}
	
	public void setCommand(Custome4D2FCommand command){
		slot = command;
	}
	
//	public String buttonWasPressed(){
//		return slot.execute();
//	}
//	
	public void setCommand(int slot, Custome4D2FCommand onCommand, Custome4D2FCommand offCommand){
		onCommands[slot] = onCommand;
		offCommands[slot] = offCommand;
	}
//	
//	public String onButtonWasPushed(int slot){
//		commandTypes.add(object);
//		return onCommands[slot].execute();
//	}
//	
//	public String offButtonWasPushed(int slot){
//		return offCommands[slot].execute();
//	}
	
	public Custome4D2FCommandType executePressDown(float x, float y, int motionEventPointerId, MotionEvent event){
		Custome4D2FCommandType commandType = Custome4D2FCommandType.None;
		for(Custome4D2FCommand command : onCommands){
			if(command.checkExecute(x, y, event)){
				commandType = command.execute();
				command.setMotionEventPointerId(motionEventPointerId);
				break;
			}
		}
		return commandType;
	}
	
	public Custome4D2FCommandType executePressUp(float x, float y, int motionEventPointerId, MotionEvent event){
		Custome4D2FCommandType commandType = Custome4D2FCommandType.None;
//		if(motionEventPointerId!=-1){
//			for(int i = 0; i < onCommands.length; i++){
//				Command onCommand = onCommands[i];
//				if(onCommand.getMotionEventPointerId()==motionEventPointerId){
//					commandType = offCommands[i].execute();
//					onCommand.setMotionEventPointerId(-1);
//					break;
//				}
//			}
//		}else{
//			for(Command command : offCommands){
//				if(command.checkExecute(x, y, event)){
//					commandType = command.execute();
//					break;
//				}
//			}
//		}
		
		for(Custome4D2FCommand command : offCommands){
			if(command.checkExecute(x, y, event)){
				commandType = command.execute();
				break;
			}
		}

		return commandType;
	}
}
