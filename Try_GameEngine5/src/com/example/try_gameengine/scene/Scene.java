package com.example.try_gameengine.scene;

import java.util.Iterator;
import android.content.Context;
import com.example.try_gameengine.Camera.Camera;
import com.example.try_gameengine.action.Time;
import com.example.try_gameengine.framework.ALayer;
import com.example.try_gameengine.framework.Data;
import com.example.try_gameengine.framework.IGameController;
import com.example.try_gameengine.framework.IGameModel;
import com.example.try_gameengine.framework.LayerManager;
import com.example.try_gameengine.framework.ProcessBlock;
import com.example.try_gameengine.framework.Sprite;
import com.example.try_gameengine.remotecontroller.IRemoteController;
import com.example.try_gameengine.remotecontroller.RemoteController;

public abstract class Scene extends Sprite{
	protected IGameModel gameModel;
	protected IGameController gameController;
	private String id;
	protected Context context;
	protected IRemoteController remoteController;
	protected boolean isEnableRemoteController = true;
	
	public static final int RESTART = 1;
	public static final int RESUME = 2;
	public static final int RESUME_WITHOUT_SET_VIEW = 4;
	public static final int BLOCK = 8;
	public static final int FINISHED = 16;
	public static final int NOT_AUTO_START = 32;
	
	protected int mode = RESTART;
	
//	public Scene(){
//		
//	}
//	protected int level;
	
	protected int sceneLayerLevel;
	
//	public Time getTime(){
//		return gameModel.getTime();
//	}
	
	public Scene(Context context, String id){
		this(context, id, -1);
	}
	
	public Scene(Context context, String id, int sceneLayerLevel){
		this(context, id, sceneLayerLevel, RESTART);
	}
	
	public Scene(Context context, String id, int sceneLayerLevel, int mode){
		this.context = context;
		this.id = id;
		this.sceneLayerLevel = sceneLayerLevel;
		this.mode = mode;
		
		if(sceneLayerLevel>=0)
			LayerManager.getInstance().setLayerBySenceIndex(sceneLayerLevel);
		
		initGameModel();
		initGameController();
		
//		if(sceneLayerLevel>=0)
//			gameModel.setTime(new Time);
	}
	
	public String getId(){
		return id;
	}
	
	public abstract void initGameModel();
	
	public abstract void initGameController();
	
	public void sceneWillStart(Object obj){
		
	}
	
	public void startWithObj(Object obj){
		sceneWillStart(obj);
		start();
	}
	
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
	
	public RemoteController createDefaultRemoteController(){
		return RemoteController.createRemoteController();
	}
	
	public void isEnableRemoteController(boolean isEnableRemoteController){
		this.isEnableRemoteController = isEnableRemoteController;
	}
	
	public IRemoteController getRemoteController(){
		return remoteController;
	}
	
	public void setRemoteController(IRemoteController remoteController){
		this.remoteController = remoteController;
	}
	
	/*
	 * be care for setLayerLevel in Scene, because it relative with LayerManager and SceneManager.
	*/
	@Override
	public void setLayerLevel(int sceneLayerLevel){
		this.sceneLayerLevel = sceneLayerLevel;
	}
	
	@Override
	public int getLayerLevel(){
		return this.sceneLayerLevel;
	}
	
	public void addAutoDraw(ALayer layer){
		if(!layer.isAutoAdd()){
			layer.setAutoAdd(true, sceneLayerLevel);
//			LayerManager.getInstance().addSceneLayerByLayerLevel(layer, sceneLayerLevel);
		}
		
//		layer.setAutoAdd(true);
	}
	
	public int getViewBackgroundColor() {
		return gameModel.getBackgroundColor();
	}

	public void setViewBackgroundColor(int backgroundColor) {
		gameModel.setBackgroundColor(backgroundColor);
	}
	
	public Camera getCamera(){
		return gameModel.getCamera();
	}
	
	public void setCamera(Camera camera){
		gameModel.setCamera(camera);
	}
	
	public void addPreProcessBlock(ProcessBlock processBlock){
		gameModel.addPreProcessBlock(processBlock);
	}
	
//	@Override
	public void finish() {
		// TODO Auto-generated method stub
//		super.finish();
//		((Activity)context).finish();
		setMode(FINISHED);
		gameModel.setData(new DestoryData());
		LayerManager.getInstance().deleteSceneLayersBySceneLayerLevel(sceneLayerLevel);
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
