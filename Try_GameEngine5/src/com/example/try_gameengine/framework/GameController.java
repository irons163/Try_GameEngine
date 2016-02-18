package com.example.try_gameengine.framework;

import java.util.Iterator;

import com.example.try_gameengine.scene.Scene;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View.OnCreateContextMenuListener;

public abstract class GameController implements IGameController{
	protected IGameModel gameModel;
	protected Activity activity;
	GameView gameView;
	protected int sceneMode = Scene.RESTART;
	private boolean isGameViewCreated = false;
	private boolean isBlocRunStart = false;
//	public GameController() {
//		// TODO Auto-generated constructor stub
//	}
	
	public GameController(Activity activity, IGameModel gameModel) {
		// TODO Auto-generated constructor stub
		this.gameModel = gameModel;
		this.activity = activity;
//		GameView gameView = new GameView(activity, this, gameModel);
//		activity.setContentView(gameView);
	}
	
	public GameController(Activity activity, IGameModel gameModel, int sceneMode) {
		// TODO Auto-generated constructor stub
		this.gameModel = gameModel;
		this.activity = activity;
		this.sceneMode = sceneMode;
	}
	
	@Override
	public void setFlag(int sceneMode) {
		// TODO Auto-generated method stub
		if((sceneMode&Scene.BLOCK)!=0){
			isBlocRunStart = true;
		}else{
			isBlocRunStart = false;
		}
	}
	
	protected void initStart(){
		initStart(sceneMode);
	}
	
	protected void initStart(int sceneMode){
		if((sceneMode&Scene.BLOCK)!=0){
			isBlocRunStart = true;
		}else{
			isBlocRunStart = false;
		}
		
		if((sceneMode&Scene.RESTART)!=0){
			isBlocRunStart = false;
			createGameview();
			setActivityContentView(activity);
		}else if((sceneMode&Scene.RESUME)!=0){
			isBlocRunStart = false;
			if(!isGameViewCreated){
				createGameview();
				setActivityContentView(activity);
				isGameViewCreated = true;
			}else{
				if(gameView!=null &&Utils.checkViewExist(activity.getWindow().getDecorView(), gameView)){
					setActivityContentView(activity);
				}else{
					runStart();
				}
			}
		}else if((sceneMode&Scene.RESUME_WITHOUT_SET_VIEW)!=0){
			isBlocRunStart = false;
			runStart();
		}else if((sceneMode&Scene.FINISHED)!=0){
			
		}else if((sceneMode&Scene.NOT_AUTO_START)!=0){
			
		}
	}
	
	private void createGameview(){
		initGameView(activity, gameModel);
		arrangeView();
	}
	
	protected abstract void initGameView(Activity activity, IGameModel gameModel);
	
	protected abstract void arrangeView(); 
	
	protected abstract void setActivityContentView(Activity activity);

	@Override
	public void start() {
		// TODO Auto-generated method stub
//		gameModel.start();
		gameModel.restart();
		
//		if(gameView==null){
//			gameView = new GameView(activity, this, gameModel);
//			activity.setContentView(gameView);
//		}else{
//			activity.setContentView(gameView);
//			gameModel.restart();
////			gameView = new GameView(activity, this, gameModel);
////			activity.setContentView(gameView);
//		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		beforeGameStop();
		gameModel.stop();
		afterGameStop();
	}

	@Override
	public void showWin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showLose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		gameModel.onTouchEvent(event);
	}

	@Override
	public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
		// TODO Auto-generated method stub
		gameModel.setSurfaceHolder(surfaceHolder);
	}
	
//	protected add
	
	class BlockRunData extends Data{
		private boolean isBlock = false;
		
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
		
		public boolean getIsBlock(){
			return isBlock;
		}
		
		public void setIsBlock(boolean isBlock){
			this.isBlock = isBlock;
		}
	}

	public void runStart(){
		if(!isBlocRunStart){
			beforeGameStart();
			gameModel.restart();
			afterGameStart();
		}else{
			BlockRunData data = new BlockRunData();
			data.setIsBlock(true);
			gameModel.setData(data);
			gameModel.restart();
		}
	}

	protected abstract void beforeGameStart();
	
	protected abstract void afterGameStart();
	
	protected void beforeGameStop(){
		//do something
	}
	
	protected void afterGameStop(){
		//do something
	}
	
	interface OnViewCreateListener{
		
	}
}
