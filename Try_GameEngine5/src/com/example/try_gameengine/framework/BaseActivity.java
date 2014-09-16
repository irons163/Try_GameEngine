package com.example.try_gameengine.framework;

import java.util.Iterator;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Menu;

public abstract class BaseActivity extends Activity {
	private IGameModel gameModel;
	private IGameController gameController;
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
		LayerManager.setNoSceneLayer();
		
		initGameModel();
		initGameController();
	}
	
	protected abstract void initGameModel();
	
	protected abstract void initGameController();

}

