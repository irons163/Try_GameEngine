package com.example.try_gameengine.remotecontroller;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

public class RemoteControl {
	Command slot;
	
	Command[] onCommands;
	Command[] offCommands;
	
	
	
	
	public RemoteControl() {
		onCommands = new Command[7];
		offCommands = new Command[7];
		Command noCommand = new NoCommand();
		for(int i=0; i<7; i++){
			onCommands[i] = noCommand;
			offCommands[i] = noCommand;
		}
	}
	
	public void setCommand(Command command){
		slot = command;
	}
	
//	public String buttonWasPressed(){
//		return slot.execute();
//	}
//	
	public void setCommand(int slot, Command onCommand, Command offCommand){
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
	
	public CommandType executePressDown(float x, float y){
		CommandType commandType = CommandType.None;
		for(Command command : onCommands){
			if(command.checkExecute(x, y)){
				commandType = command.execute();
				break;
			}
		}
		return commandType;
	}
	
	public CommandType executePressUp(float x, float y){
		CommandType commandType = CommandType.None;
		for(Command command : offCommands){
			if(command.checkExecute(x, y)){
				commandType = command.execute();
				break;
			}
		}
		return commandType;
	}
}
