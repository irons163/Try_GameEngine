package com.example.try_gameengine.remotecontroller.custome;

import com.example.try_gameengine.framework.BitmapUtil;
import com.example.try_gameengine.framework.Config;

public class Custome4D2FRemoteLoader {
	
	private Key upKey;
	private Key downKey;
	private Key rightKey;
	private Key leftKey;
	private Key enterKey;
	private Key cancelKey;
	private Custome4D2FRemoteControl remoteControl;
	
	public Custome4D2FRemoteLoader() {
		// TODO Auto-generated constructor stub
		load();
	}
	
	private void load(){
		remoteControl = new Custome4D2FRemoteControl();
		
		leftKey = new Key(BitmapUtil.leftKey, 0, Config.currentScreenHeight-BitmapUtil.leftKey.getHeight()*2, 1, false);
		leftKey.setEnableMultiTouch(true);
		Custome4D2FKeyCommandPressDown leftKeyCommandPressDown = new Custome4D2FKeyCommandPressDown(leftKey, Custome4D2FCommandType.LeftKeyDownCommand);
		Custome4D2FKeyCommandPressDown leftKeyCommandPressUp = new Custome4D2FKeyCommandPressDown(leftKey, Custome4D2FCommandType.LeftKeyUpCommand);
		rightKey = new Key(BitmapUtil.rightKey, leftKey.getX() + leftKey.getWidth()*2, Config.currentScreenHeight-BitmapUtil.rightKey.getHeight()*2, 1, false);
		rightKey.setEnableMultiTouch(true);
		Custome4D2FKeyCommandPressDown rightKeyCommandPressDown = new Custome4D2FKeyCommandPressDown(rightKey, Custome4D2FCommandType.RightKeyDownCommand);
		Custome4D2FKeyCommandPressUp rightKeyCommandPressUp = new Custome4D2FKeyCommandPressUp(rightKey, Custome4D2FCommandType.RightKeyUpCommand);
		
		upKey = new Key(BitmapUtil.upKey, leftKey.getX() + leftKey.getWidth() + leftKey.getWidth()/2 - BitmapUtil.upKey.getWidth()/2, Config.currentScreenHeight-BitmapUtil.leftKey.getHeight()*2 - BitmapUtil.leftKey.getHeight()/2 - BitmapUtil.upKey.getHeight()/2, 1, false);
		upKey.setEnableMultiTouch(true);
		Custome4D2FKeyCommandPressDown upKeyCommandPressDown = new Custome4D2FKeyCommandPressDown(upKey, Custome4D2FCommandType.UPKeyDownCommand);
		Custome4D2FKeyCommandPressUp upKeyCommandPressUp = new Custome4D2FKeyCommandPressUp(upKey, Custome4D2FCommandType.UPKeyDownCommand);
		downKey = new Key(BitmapUtil.downKey, leftKey.getX() + leftKey.getWidth() + leftKey.getWidth()/2 - BitmapUtil.downKey.getWidth()/2, Config.currentScreenHeight-BitmapUtil.leftKey.getHeight() + BitmapUtil.leftKey.getHeight()/2 - BitmapUtil.downKey.getHeight()/2, 1, false);
		downKey.setEnableMultiTouch(true);
		Custome4D2FKeyCommandPressDown downKeyCommandPressDown = new Custome4D2FKeyCommandPressDown(downKey, Custome4D2FCommandType.DownKeyDownCommand);
		Custome4D2FKeyCommandPressDown downKeyCommandPressUp = new Custome4D2FKeyCommandPressDown(downKey, Custome4D2FCommandType.DownKeyUpCommand);
		
		enterKey = new Key(BitmapUtil.enterKey, Config.currentScreenWidth-BitmapUtil.enterKey.getWidth(), Config.currentScreenHeight-BitmapUtil.enterKey.getHeight(), 1, false);
		enterKey.setEnableMultiTouch(true);
		Custome4D2FKeyCommandPressDown enterKeyCommandPressDown = new Custome4D2FKeyCommandPressDown(enterKey, Custome4D2FCommandType.EnterKeyDownCommand);
		Custome4D2FKeyCommandPressUp enterKeyCommandPressUp = new Custome4D2FKeyCommandPressUp(enterKey, Custome4D2FCommandType.EnterKeyUpCommand);
		cancelKey = new Key(BitmapUtil.cancelKey, 0, Config.currentScreenHeight-BitmapUtil.cancelKey.getHeight(), 1, false);
		cancelKey.setEnableMultiTouch(true);
		Custome4D2FKeyCommandPressDown cancelKeyCommandPressDown = new Custome4D2FKeyCommandPressDown(cancelKey, Custome4D2FCommandType.CancelKeyDownCommand);
		Custome4D2FKeyCommandPressDown cancelKeyCommandPressUp = new Custome4D2FKeyCommandPressDown(cancelKey, Custome4D2FCommandType.CancelKeyUpCommand);
		
		remoteControl.setCommand(0, rightKeyCommandPressDown, rightKeyCommandPressUp);
		remoteControl.setCommand(1, leftKeyCommandPressDown, leftKeyCommandPressUp);
		remoteControl.setCommand(2, upKeyCommandPressDown, upKeyCommandPressUp);
		remoteControl.setCommand(3, downKeyCommandPressDown, downKeyCommandPressUp);
		remoteControl.setCommand(4, enterKeyCommandPressDown, enterKeyCommandPressUp);
		remoteControl.setCommand(5, cancelKeyCommandPressDown, cancelKeyCommandPressUp);
	}
	
	public Key getUpKey(){
		return upKey;
	}
	
	public Key getDownKey(){
		return downKey;
	}
	
	public Key getLeftKey(){
		return leftKey;
	}
	
	public Key getRightKey(){
		return rightKey;
	}
	
	public Key getEnterKey(){
		return enterKey;
	}
	
	public Key getCancelKey(){
		return cancelKey;
	}
	
	public Custome4D2FRemoteControl getRemoteControl(){
		return remoteControl;
	}
}
