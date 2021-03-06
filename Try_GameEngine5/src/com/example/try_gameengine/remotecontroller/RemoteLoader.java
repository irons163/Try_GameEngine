package com.example.try_gameengine.remotecontroller;

import com.example.try_gameengine.framework.BitmapUtil;
import com.example.try_gameengine.framework.Config;

/**
 * @author irons
 *
 */
public class RemoteLoader {
	
	private UpKey upKey;
	private DownKey downKey;
	private RightKey rightKey;
	private LeftKey leftKey;
	private RemoteControl remoteControl;
	
	public RemoteLoader() {
		load();
	}
	
	private void load(){
		remoteControl = new RemoteControl();
		
		rightKey = new RightKey(BitmapUtil.rightKey, Config.currentScreenWidth-BitmapUtil.rightKey.getWidth(), Config.currentScreenHeight-BitmapUtil.rightKey.getHeight(), 1, false);
		rightKey.setEnableMultiTouch(true);
		RightKeyCommandPressDown upKeyCommandPressDown = new RightKeyCommandPressDown(rightKey);
		RightKeyCommandPressUp upKeyCommandPressUp = new RightKeyCommandPressUp(rightKey);
		leftKey = new LeftKey(BitmapUtil.leftKey, 0, Config.currentScreenHeight-BitmapUtil.leftKey.getHeight(), 1, false);
		leftKey.setEnableMultiTouch(true);
		LeftKeyCommandPressDown leftKeyCommandPressDown = new LeftKeyCommandPressDown(leftKey);
		LeftKeyCommandPressUp leftKeyCommandPressUp = new LeftKeyCommandPressUp(leftKey);
		
		remoteControl.setCommand(0, upKeyCommandPressDown, upKeyCommandPressUp);
		remoteControl.setCommand(1, leftKeyCommandPressDown, leftKeyCommandPressUp);
	}
	
	public UpKey getUpKey(){
		return upKey;
	}
	
	public DownKey getDownKey(){
		return downKey;
	}
	
	public LeftKey getLeftKey(){
		return leftKey;
	}
	
	public RightKey getRightKey(){
		return rightKey;
	}
	
	public RemoteControl getRemoteControl(){
		return remoteControl;
	}
}
