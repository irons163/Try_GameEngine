package com.example.try_gameengine.scene;


import android.app.Activity;
import android.content.Context;

import com.example.try_gameengine.framework.IGameController;
import com.example.try_gameengine.framework.IGameModel;

public abstract class Scene extends Activity{
	protected IGameModel gameModel;
	protected IGameController gameController;
	private String id;
	protected Context context;
	
	
	public static final int RESTART = 1;
	public static final int RESUME = 2;
	
	protected int mode = RESTART;
	
//	public Scene(){
//		
//	}
	protected int level;
	
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
	}	
	
}
