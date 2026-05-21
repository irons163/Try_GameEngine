package com.example.try_gameengine.framework

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics

abstract class BaseActivity : Activity() {
    private val gameModel: IGameModel? = null
    private val gameController: IGameController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //		setContentView(R.layout.activity_main);
        val dm = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(dm)

        CommonUtil.screenHeight = dm.heightPixels
        CommonUtil.screenWidth = dm.widthPixels

        BitmapUtil.initBitmap(this)


//		LayerManager.getInstance().initLayerManager.getInstance()();
//		LayerManager.getInstance().setNoSceneLayer();
        initGameModel()
        initGameController()
    }

    protected abstract fun initGameModel()

    protected abstract fun initGameController()
}

