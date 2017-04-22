package com.example.try_gameengine.remotecontroller.custom;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.remotecontroller.custome.Key;

public class CustomRemoteLoader {
	
	private Key upKey;
	private Key downKey;
	private Key rightKey;
	private Key leftKey;
	private Key enterKey;
	private Key cancelKey;
	private CustomRemoteControl remoteControl;
	List<Key> keys = new ArrayList<Key>();
	
	public CustomRemoteLoader() {
		// TODO Auto-generated constructor stub
		load();
	}
	
	private void load(){
		remoteControl = new CustomRemoteControl();
		/*
		leftKey = new Key(BitmapUtil.leftKey, 0, Config.currentScreenHeight-BitmapUtil.leftKey.getHeight()*2, 1, false);
		leftKey.setEnableMultiTouch(true);
		CustomCommandPressDown leftKeyCommandPressDown = new CustomCommandPressDown(leftKey);
		CustomCommandPressDown leftKeyCommandPressUp = new CustomCommandPressDown(leftKey);
		rightKey = new Key(BitmapUtil.rightKey, leftKey.getX() + leftKey.getWidth()*2, Config.currentScreenHeight-BitmapUtil.rightKey.getHeight()*2, 1, false);
		rightKey.setEnableMultiTouch(true);
		CustomCommandPressDown rightKeyCommandPressDown = new CustomCommandPressDown(rightKey);
		CustomCommandPressDown rightKeyCommandPressUp = new CustomCommandPressDown(rightKey);
		
		upKey = new Key(BitmapUtil.upKey, leftKey.getX() + leftKey.getWidth() + leftKey.getWidth()/2 - BitmapUtil.upKey.getWidth()/2, Config.currentScreenHeight-BitmapUtil.leftKey.getHeight()*2 - BitmapUtil.leftKey.getHeight()/2 - BitmapUtil.upKey.getHeight()/2, 1, false);
		upKey.setEnableMultiTouch(true);
		CustomCommandPressDown upKeyCommandPressDown = new CustomCommandPressDown(upKey);
		CustomCommandPressDown upKeyCommandPressUp = new CustomCommandPressDown(upKey);
		downKey = new Key(BitmapUtil.downKey, leftKey.getX() + leftKey.getWidth() + leftKey.getWidth()/2 - BitmapUtil.downKey.getWidth()/2, Config.currentScreenHeight-BitmapUtil.leftKey.getHeight() + BitmapUtil.leftKey.getHeight()/2 - BitmapUtil.downKey.getHeight()/2, 1, false);
		downKey.setEnableMultiTouch(true);
		CustomCommandPressDown downKeyCommandPressDown = new CustomCommandPressDown(downKey);
		CustomCommandPressDown downKeyCommandPressUp = new CustomCommandPressDown(downKey);
		
		enterKey = new Key(BitmapUtil.enterKey, Config.currentScreenWidth-BitmapUtil.enterKey.getWidth(), Config.currentScreenHeight-BitmapUtil.enterKey.getHeight(), 1, false);
		enterKey.setEnableMultiTouch(true);
		CustomCommandPressDown enterKeyCommandPressDown = new CustomCommandPressDown(enterKey);
		CustomCommandPressDown enterKeyCommandPressUp = new CustomCommandPressDown(enterKey);
		cancelKey = new Key(BitmapUtil.cancelKey, 0, Config.currentScreenHeight-BitmapUtil.cancelKey.getHeight(), 1, false);
		cancelKey.setEnableMultiTouch(true);
		CustomCommandPressDown cancelKeyCommandPressDown = new CustomCommandPressDown(cancelKey);
		CustomCommandPressDown cancelKeyCommandPressUp = new CustomCommandPressDown(cancelKey);
		
		remoteControl.addCommand(rightKeyCommandPressDown, rightKeyCommandPressUp);
		remoteControl.addCommand(leftKeyCommandPressDown, leftKeyCommandPressUp);
		remoteControl.addCommand(upKeyCommandPressDown, upKeyCommandPressUp);
		remoteControl.addCommand(downKeyCommandPressDown, downKeyCommandPressUp);
		remoteControl.addCommand(enterKeyCommandPressDown, enterKeyCommandPressUp);
		remoteControl.addCommand(cancelKeyCommandPressDown, cancelKeyCommandPressUp);
		*/
		
		for(Key key:keys){
			CustomCommandPressDown pressDown = new CustomCommandPressDown(key);
			CustomCommandPressDown pressUp = new CustomCommandPressDown(key);
			remoteControl.addCommand(pressDown, pressUp);
		}
		
	}
	
	public CustomRemoteControl getRemoteControl(){
		return remoteControl;
	}
}
