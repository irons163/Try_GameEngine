package com.example.try_gameengine.stage;

import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.try_gameengine.framework.BitmapUtil;
import com.example.try_gameengine.framework.CommonUtil;
import com.example.try_gameengine.framework.IGameController;
import com.example.try_gameengine.framework.IGameModel;
import com.example.try_gameengine.framework.LayerManager;
import com.example.try_gameengine.scene.SceneManager;

public abstract class BaseStage extends Stage{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		CommonUtil.screenHeight = dm.heightPixels;
		CommonUtil.screenWidth = dm.widthPixels;
		
		BitmapUtil.initBitmap(this);
		
//		LayerManager.initLayerManager();
		
		initGame();
		
		initStage();
		
		initGameModel();
		initGameController();
	}
	
	protected abstract void initGame();
	
	protected abstract void initGameModel();
	
	protected abstract void initGameController();
	
	@Override
	public SceneManager initSceneManager() {
		// TODO Auto-generated method stub
		LayerManager.setNoSceneLayer();
		return new SceneManager();
	}

}
