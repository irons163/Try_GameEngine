package com.example.try_gameengine.remotecontroller;

import android.view.MotionEvent;
import com.example.try_gameengine.remotecontroller.RemoteController.CommandType;

/**
 * @author irons
 *
 */
public class RemoteControl {
	Command slot;
	Command[] onCommands;
	Command[] offCommands;

	public RemoteControl() {
		onCommands = new Command[7];
		offCommands = new Command[7];
		Command noCommand = new NoCommand();
		for (int i = 0; i < 7; i++) {
			onCommands[i] = noCommand;
			offCommands[i] = noCommand;
		}
	}

	public void setCommand(Command command) {
		slot = command;
	}

	public void setCommand(int slot, Command onCommand, Command offCommand) {
		onCommands[slot] = onCommand;
		offCommands[slot] = offCommand;
	}

	public CommandType executePressDown(float x, float y,
			int motionEventPointerId, MotionEvent event) {
		CommandType commandType = CommandType.None;
		for (Command command : onCommands) {
			if (command.checkExecute(x, y, event)) {
				commandType = command.execute();
				command.setMotionEventPointerId(motionEventPointerId);
				break;
			}
		}
		return commandType;
	}

	public CommandType executePressUp(float x, float y,
			int motionEventPointerId, MotionEvent event) {
		CommandType commandType = CommandType.None;

		for (Command command : offCommands) {
			if (command.checkExecute(x, y, event)) {
				commandType = command.execute();
				break;
			}
		}

		return commandType;
	}
}
