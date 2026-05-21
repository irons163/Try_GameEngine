package com.example.try_gameengine.stage

import android.os.Bundle
import android.util.DisplayMetrics
import com.example.try_gameengine.framework.BitmapUtil
import com.example.try_gameengine.framework.CommonUtil
import com.example.try_gameengine.scene.SceneManager

/**
 * This BaseStage is a kind of stage by init. Default init the screenHeight and screenWidth. Then
 * initSceneManager , initGame , initStage , initGameModel, initGameController.
 * @author irons
 // */
abstract class BaseStage : Stage() {
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dm = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(dm)

        CommonUtil.screenHeight = dm.heightPixels
        CommonUtil.screenWidth = dm.widthPixels

        BitmapUtil.initBitmap(this)

        initGame()
        initStage()
        initGameModel()
        initGameController()
    }

    protected abstract fun initGame()

    protected abstract fun initGameModel()

    protected abstract fun initGameController()

    override fun initSceneManager(): SceneManager {
        // TODO Auto-generated method stub
//		LayerManager.getInstance().setNoSceneLayer();
        return SceneManager.Companion.getInstance()
    }
}
