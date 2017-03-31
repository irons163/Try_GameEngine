package com.example.try_gameengine.stage;

import android.os.Bundle;
import android.util.DisplayMetrics;
import com.example.try_gameengine.framework.BitmapUtil;
import com.example.try_gameengine.framework.CommonUtil;
import com.example.try_gameengine.framework.LayerManager;
import com.example.try_gameengine.scene.SceneManager;

/**
 * This BaseStage is a kind of stage by init. Default init the screenHeight and screenWidth. Then
 * initSceneManager , initGame , initStage , initGameModel, initGameController.
 * @author irons
 *
 */
public abstract class BaseStage extends Stage{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		CommonUtil.screenHeight = dm.heightPixels;
		CommonUtil.screenWidth = dm.widthPixels;
		
		BitmapUtil.initBitmap(this);
		
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
//		LayerManager.getInstance().setNoSceneLayer();
		return SceneManager.getInstance();
	}

}
