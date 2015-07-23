package com.example.try_gameengine.remotecontroller;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.LayerManager;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.utils.GameTimeUtil;

public class RemoteController {
	
	private static RemoteController remoteController;
	private static RemoteControl remoteControl;
	private static RemoteLoader remoteLoader;
	
	RemoteContollerListener remoteContollerListener;
	RemoteContollerOnTouchEventListener remoteContollerOnTouchEventListener;
	List<CommandType> commandTypes = new ArrayList<CommandType>();
	GameTimeUtil remoteControllerTimeUtil;
	
	public enum CommandType{
		None,
		UPKeyUpCommand,
		UPKeyDownCommand,
		DownKeyUpCommand,
		DownKeyDownCommand,
		LeftKeyUpCommand,
		LeftKeyDownCommand
	}
	
	public interface RemoteContollerOnTouchEventListener{
		
		public void onTouchEvent(MotionEvent event);
				
	}
	
	private RemoteContollerOnTouchEventListener defaultRemoteContollerOnTouchEventListener = new RemoteContollerOnTouchEventListener() {
		
		@Override
		public void onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
//			if (((event.getAction() & event.ACTION_MASK) == event.ACTION_POINTER_DOWN)
//					|| ((event.getAction() & event.ACTION_MASK) == event.ACTION_POINTER_UP)) {
//				_x = event.getX(event.getActionIndex());
//				_y = event.getY(event.getActionIndex());
//			}
			
			float x = event.getX();
			float y = event.getY();
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				pressDown(x, y);
			}
		}
	};
	
	public interface RemoteContollerListener{
		
		public void pressDown(List<CommandType> commandTypes);
				
	}
	
	private RemoteContollerListener defaultRemoteContollerListener = new RemoteContollerListener() {
		
		@Override
		public void pressDown(List<CommandType> commandTypes) {
			// TODO Auto-generated method stub
			for(CommandType commandType : commandTypes){
				switch (commandType) {
				case UPKeyUpCommand:
					
					break;
				case UPKeyDownCommand:
					for(List<ALayer> layers : LayerManager.getLayerLevelList()){
						for(ALayer layer : layers){
							if(layer instanceof Sprite){
								((Sprite)layer).move(0, -10);
							}
						}
					}
					break;
				default:
					break;
				}
			}			
		}
		
	};

	private RemoteController(){
		remoteLoader = new RemoteLoader();
		remoteControl = remoteLoader.getRemoteControl();
		remoteControllerTimeUtil = new GameTimeUtil(0);
		remoteContollerListener = defaultRemoteContollerListener;
		remoteContollerOnTouchEventListener = defaultRemoteContollerOnTouchEventListener;
	};
	
	public static RemoteController createRemoteController(){
		if(remoteController==null){
			synchronized (RemoteController.class) {
				if(remoteController==null)
					remoteController = new RemoteController();
			}			
		}
		return remoteController;
	}
	
	public void pressDown(float x, float y){
		CommandType commandType = remoteControl.executePressDown(x, y);
		commandTypes.add(commandType);
		
		if(remoteControllerTimeUtil.isArriveExecuteTime()){
			remoteContollerListener.pressDown(commandTypes);
			commandTypes.clear();
		}
//		if(remoteControl.executePressDown(x, y)){
//			
//		}
//		remoteControl.onButtonWasPushed(0);
	}
	
	public void pressUp(float x, float y){
		CommandType commandType = remoteControl.executePressUp(x, y);
		commandTypes.add(commandType);
		
		if(remoteControllerTimeUtil.isArriveExecuteTime()){
			remoteContollerListener.pressDown(commandTypes);
			commandTypes.clear();
		}
	}
	
	public void execute(){
		this.remoteContollerListener.pressDown(commandTypes);
	}
	
	public void setRemoteContollerListener(RemoteContollerListener remoteContollerListener){
		this.remoteContollerListener = remoteContollerListener;
	}
	
	public void setRemoteContollerOnTouchEventListener(RemoteContollerOnTouchEventListener remoteContollerOnTouchEventListener){
		this.remoteContollerOnTouchEventListener = remoteContollerOnTouchEventListener;
	}
	
	public void onTouchEvent(MotionEvent event){
		this.remoteContollerOnTouchEventListener.onTouchEvent(event);
	}
	
	public UpKey getUpKey(){
		return remoteLoader.getUpKey();
	}
	
	public void drawRemoteController(Canvas canvas, Paint paint){
		remoteLoader.getUpKey().drawSelf(canvas, paint);
		remoteLoader.getLeftKey().drawSelf(canvas, paint);
	}
	
	public void setUpKyPosition(float x, float y){
		remoteLoader.getUpKey().setPosition(x, y);
	}
	
	public void setUpKyBitmap(Bitmap bitmap){
		remoteLoader.getUpKey().setBitmapAndAutoChangeWH(bitmap);
	}
	
	public void setLeftKyPosition(float x, float y){
		remoteLoader.getLeftKey().setPosition(x, y);
	}
	
	public void setLeftKyBitmap(Bitmap bitmap){
		remoteLoader.getLeftKey().setBitmapAndAutoChangeWH(bitmap);
	}
}
