package com.example.try_gameengine.remotecontroller.custome;

import com.example.try_gameengine.framework.BitmapUtil;
import com.example.try_gameengine.framework.Config;

public class Custom4D2FRemoteLoader {
	
	private Key upKey;
	private Key downKey;
	private Key rightKey;
	private Key leftKey;
	private Key enterKey;
	private Key cancelKey;
	private Custom4D2FRemoteControl remoteControl;
	
	public Custom4D2FRemoteLoader() {
		// TODO Auto-generated constructor stub
		load();
	}
	
	private void load(){
		remoteControl = new Custom4D2FRemoteControl();
		
		leftKey = new Key(BitmapUtil.leftKey, 0, Config.currentScreenHeight-BitmapUtil.leftKey.getHeight()*2, 1, false);
		leftKey.setEnableMultiTouch(true);
		Custom4D2FKeyCommandPressDown leftKeyCommandPressDown = new Custom4D2FKeyCommandPressDown(leftKey, Custom4D2FCommandType.LeftKeyDownCommand);
		Custom4D2FKeyCommandPressUp leftKeyCommandPressUp = new Custom4D2FKeyCommandPressUp(leftKey, Custom4D2FCommandType.LeftKeyUpCommand);
		rightKey = new Key(BitmapUtil.rightKey, leftKey.getX() + leftKey.getWidth()*2, Config.currentScreenHeight-BitmapUtil.rightKey.getHeight()*2, 1, false);
		rightKey.setEnableMultiTouch(true);
		Custom4D2FKeyCommandPressDown rightKeyCommandPressDown = new Custom4D2FKeyCommandPressDown(rightKey, Custom4D2FCommandType.RightKeyDownCommand);
		Custom4D2FKeyCommandPressUp rightKeyCommandPressUp = new Custom4D2FKeyCommandPressUp(rightKey, Custom4D2FCommandType.RightKeyUpCommand);
		
		upKey = new Key(BitmapUtil.upKey, leftKey.getX() + leftKey.getWidth() + leftKey.getWidth()/2 - BitmapUtil.upKey.getWidth()/2, Config.currentScreenHeight-BitmapUtil.leftKey.getHeight()*2 - BitmapUtil.leftKey.getHeight()/2 - BitmapUtil.upKey.getHeight()/2, 1, false);
		upKey.setEnableMultiTouch(true);
		Custom4D2FKeyCommandPressDown upKeyCommandPressDown = new Custom4D2FKeyCommandPressDown(upKey, Custom4D2FCommandType.UPKeyDownCommand);
		Custom4D2FKeyCommandPressUp upKeyCommandPressUp = new Custom4D2FKeyCommandPressUp(upKey, Custom4D2FCommandType.UPKeyUpCommand);
		downKey = new Key(BitmapUtil.downKey, leftKey.getX() + leftKey.getWidth() + leftKey.getWidth()/2 - BitmapUtil.downKey.getWidth()/2, Config.currentScreenHeight-BitmapUtil.leftKey.getHeight() + BitmapUtil.leftKey.getHeight()/2 - BitmapUtil.downKey.getHeight()/2, 1, false);
		downKey.setEnableMultiTouch(true);
		Custom4D2FKeyCommandPressDown downKeyCommandPressDown = new Custom4D2FKeyCommandPressDown(downKey, Custom4D2FCommandType.DownKeyDownCommand);
		Custom4D2FKeyCommandPressDown downKeyCommandPressUp = new Custom4D2FKeyCommandPressDown(downKey, Custom4D2FCommandType.DownKeyUpCommand);
		
		enterKey = new Key(BitmapUtil.enterKey, Config.currentScreenWidth-BitmapUtil.enterKey.getWidth(), Config.currentScreenHeight-BitmapUtil.enterKey.getHeight(), 1, false);
		enterKey.setEnableMultiTouch(true);
		Custom4D2FKeyCommandPressDown enterKeyCommandPressDown = new Custom4D2FKeyCommandPressDown(enterKey, Custom4D2FCommandType.EnterKeyDownCommand);
		Custom4D2FKeyCommandPressUp enterKeyCommandPressUp = new Custom4D2FKeyCommandPressUp(enterKey, Custom4D2FCommandType.EnterKeyUpCommand);
		cancelKey = new Key(BitmapUtil.cancelKey, 0, Config.currentScreenHeight-BitmapUtil.cancelKey.getHeight(), 1, false);
		cancelKey.setEnableMultiTouch(true);
		Custom4D2FKeyCommandPressDown cancelKeyCommandPressDown = new Custom4D2FKeyCommandPressDown(cancelKey, Custom4D2FCommandType.CancelKeyDownCommand);
		Custom4D2FKeyCommandPressDown cancelKeyCommandPressUp = new Custom4D2FKeyCommandPressDown(cancelKey, Custom4D2FCommandType.CancelKeyUpCommand);
		
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
	
	public Custom4D2FRemoteControl getRemoteControl(){
		return remoteControl;
	}
}
