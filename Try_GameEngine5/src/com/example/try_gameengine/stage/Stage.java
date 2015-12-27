package com.example.try_gameengine.stage;

import com.example.try_gameengine.scene.SceneManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public abstract class Stage extends Activity{
	private String id;
//	protected Context context;
	
	public static final int CLEAR_TASK = Intent.FLAG_ACTIVITY_CLEAR_TASK;
	public static final int CLEAR_TOP = Intent.FLAG_ACTIVITY_CLEAR_TOP;
	public static final int CLEAR_WHEN_TASK_RESET = Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
	public static final int XCLUDE_FROM_RECENTS = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;
	public static final int FORWARD_RESULT = Intent.FLAG_ACTIVITY_FORWARD_RESULT;
	public static final int NEW_TASK = Intent.FLAG_ACTIVITY_NEW_TASK;
	public static final int NO_ANIMATION = Intent.FLAG_ACTIVITY_NO_ANIMATION;
	public static final int NO_HISTORY = Intent.FLAG_ACTIVITY_NO_HISTORY;
	public static final int PREVIOUS_IS_TOP = Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP;
	public static final int REORDER_TO_FRONT = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
	public static final int SINGLE_TOP = Intent.FLAG_ACTIVITY_SINGLE_TOP;
	public static final int TASK_ON_HOME = Intent.FLAG_ACTIVITY_TASK_ON_HOME;
	
//	protected int mode = RESTART;
	
//	public Scene(){
//		
//	}
	protected int level;
	private SceneManager sceneManager;
	
//	public Stage(Context context, String id){
////		this(context, id, 0);
//		this.context = context;
//		this.id = id;
//		
//		initSceneManager();
//	}
	
	public Stage() {
		// TODO Auto-generated constructor stub
		StageManager.addStage(this);
		
	}
	
//	public Stage(Context context, String id, int level, int mode){
//		this.context = context;
//		this.id = id;
//		this.level = level;
//		this.mode = mode;
//		initGameModel();
//		initGameController();
//	}
	
	public String getId(){
		return id;
	}
	
	public abstract SceneManager initSceneManager();
	
	public void start(){
		startActivity(new Intent(getApplicationContext(), getClass()));
	}
	
	public void stop(){
		sceneManager.stopAllScenes();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	protected void initStage(){
		sceneManager = initSceneManager();
	}
	
	public SceneManager getSceneManager(){
		return sceneManager;
	}
	
//	public int getMode() {
//		return mode;
//	}
//
//	public void setMode(int mode) {
//		this.mode = mode;
//	}

}
