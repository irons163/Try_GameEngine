package com.example.try_gameengine.stage;

import com.example.try_gameengine.scene.SceneManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * {@code Stage} is first thing of whole system, it entends Activity and it like a window.
 * It is also a place to show scenes display. The architecture is :
 * Stage -> Scene -> Layer. 
 * @author irons
 *
 */
public abstract class Stage extends Activity{
	private String id;
	
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
	
	protected int level;
	private SceneManager sceneManager;
	
	/**
	 * Construct and init for addSatge to StageManager.
	 */
	public Stage() {
		StageManager.addStage(this);
	}
	
	/**
	 * get stage id
	 * @return String
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * init own SceneManager
	 * @return
	 */
	public abstract SceneManager initSceneManager();
	
	/**
	 * start stage like startActivity.
	 */
	public void start(){
		startActivity(new Intent(getApplicationContext(), getClass()));
	}
	
	/**
	 * stop all scenes in this stage.
	 */
	public void stop(){
		sceneManager.stopAllScenes();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	/**
	 * init stage.
	 */
	protected void initStage(){
		StageManager.init(this); //init self in StageManger 
		sceneManager = initSceneManager(); // init scene manager.
	}
	
	/**
	 * get own scene manager.
	 * @return SceneManager
	 */
	public SceneManager getSceneManager(){
		return sceneManager;
	}
	
	@Override
	public void onBackPressed() {
		if(!sceneManager.previous())
			finish();
	}
	
	@Override
	public void finish() {
		super.finish();
		sceneManager.removeAllScenes();
		Log.d("Stage", "Finish.");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		sceneManager.removeAllScenes();
		Log.d("Stage", "Destroy.");
	}
}
