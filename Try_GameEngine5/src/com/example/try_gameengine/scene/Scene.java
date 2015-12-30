package com.example.try_gameengine.scene;


import java.util.Iterator;

import android.app.Activity;
import android.content.Context;

import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.Data;
import com.example.try_gameengine.framework.IGameController;
import com.example.try_gameengine.framework.IGameModel;
import com.example.try_gameengine.framework.LayerManager;
import com.example.try_gameengine.remotecontroller.RemoteController;

public abstract class Scene extends Activity{
	protected IGameModel gameModel;
	protected IGameController gameController;
	private String id;
	protected Context context;
	protected RemoteController remoteController;
	protected boolean isEnableRemoteController = true;
	
	public static final int RESTART = 1;
	public static final int RESUME = 2;
	public static final int RESUME_WITHOUT_SET_VIEW = 4;
	public static final int BLOCK = 8;
	public static final int FINISHED = 16;
	
	protected int mode = RESTART;
	
//	public Scene(){
//		
//	}
	protected int level;
	
	protected int sceneLayerLevel;
	
	public Scene(Context context, String id){
		this(context, id, 0);
	}
	
	public Scene(Context context, String id, int level){
		this(context, id, level, RESTART);
	}
	
	public Scene(Context context, String id, int level, int mode){
		this.context = context;
		this.id = id;
		this.level = level;
		this.mode = mode;
		initGameModel();
		initGameController();
	}
	
	public String getId(){
		return id;
	}
	
	public abstract void initGameModel();
	
	public abstract void initGameController();
	
	public void start(){
		gameController.start();
	}
	
	public void stop(){
		gameController.stop();
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
		gameController.setFlag(this.mode);
	}
	
	public void addMode(int mode){
		this.mode = this.mode|mode;
		gameController.setFlag(this.mode);
	}
	
	public void removeMode(int mode){
		this.mode &= ~mode;
		gameController.setFlag(this.mode);
	}
	
	public void isEnableRemoteController(boolean isEnableRemoteController){
		this.isEnableRemoteController = isEnableRemoteController;
	}
	
	public RemoteController getRemoteController(){
		return remoteController;
	}
	
	public void setLayerLevel(int sceneLayerLevel){
		this.sceneLayerLevel = sceneLayerLevel;
	}
	
	public int getLayerLevel(){
		return this.sceneLayerLevel;
	}
	
	public void addAutoDraw(ALayer layer){
//		LayerManager.addSceneLayerByLayerLevel(layer, sceneLayerLevel);
		layer.setAutoAdd(true);
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		((Activity)context).finish();
		setMode(FINISHED);
		gameModel.setData(new DestoryData());
		LayerManager.deleteSceneLayersByLayerLevel(sceneLayerLevel);
	}
	
	public class DestoryData extends Data{

		@Override
		public Object getAllExistPoints() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setAllExistPoints(Object allExistPoints) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Iterator getAllExistPointsIterator() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
