package com.example.try_gameengine.framework;

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
	
	protected void initStart(){
		initStart(sceneMode);
	}
	
	protected void initStart(int sceneMode){
		if(sceneMode==Scene.RESTART){
			createGameview();
			setActivityContentView(activity);
		}else if(sceneMode==Scene.RESUME){
			if(!isGameViewCreated){
				createGameview();
				setActivityContentView(activity);
				isGameViewCreated = true;
			}else{
				setActivityContentView(activity);
//				runStart();
			}
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
		gameModel.stop();
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

	public void runStart(){
		beforeGameStart();
		gameModel.restart();
		afterGameStart();
	}

	protected abstract void beforeGameStart();
	
	protected abstract void afterGameStart();
	
	interface OnViewCreateListener{
		
	}
}
