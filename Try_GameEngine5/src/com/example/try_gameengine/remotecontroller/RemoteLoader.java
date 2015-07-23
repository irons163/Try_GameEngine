package com.example.try_gameengine.remotecontroller;

import java.util.List;

import com.example.try_gameengine.framework.BitmapUtil;

public class RemoteLoader {
	
	private UpKey upKey;
	private LeftKey leftKey;
	private RemoteControl remoteControl;
	
	public RemoteLoader() {
		// TODO Auto-generated constructor stub
		load();
	}
	
	private void load(){
		remoteControl = new RemoteControl();
		
		upKey = new UpKey(BitmapUtil.rightKey, 100, 100, 1, false);
		UpKeyCommandPressDown upKeyCommandPressDown = new UpKeyCommandPressDown(upKey);
		UpKeyCommandPressUp upKeyCommandPressUp = new UpKeyCommandPressUp(upKey);
		leftKey = new LeftKey(BitmapUtil.leftKey, 50, 100, 1, false);
		LeftKeyCommandPressDown leftKeyCommandPressDown = new LeftKeyCommandPressDown(leftKey);
		LeftKeyCommandPressUp leftKeyCommandPressUp = new LeftKeyCommandPressUp(leftKey);
		
		remoteControl.setCommand(0, upKeyCommandPressDown, upKeyCommandPressUp);
		remoteControl.setCommand(1, leftKeyCommandPressDown, leftKeyCommandPressUp);
	}
	
	public UpKey getUpKey(){
		return upKey;
	}
	
	public LeftKey getLeftKey(){
		return leftKey;
	}
	
	public RemoteControl getRemoteControl(){
		return remoteControl;
	}
}
