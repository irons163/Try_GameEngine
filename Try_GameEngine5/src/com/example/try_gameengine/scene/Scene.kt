package com.example.try_gameengine.scene

import android.content.Context
import com.example.try_gameengine.Camera.Camera
import com.example.try_gameengine.framework.ALayer
import com.example.try_gameengine.framework.Data
import com.example.try_gameengine.framework.IGameController
import com.example.try_gameengine.framework.IGameModel
import com.example.try_gameengine.framework.LayerManager
import com.example.try_gameengine.framework.ProcessBlock
import com.example.try_gameengine.framework.Sprite
import com.example.try_gameengine.remotecontroller.IRemoteController
import com.example.try_gameengine.remotecontroller.RemoteController

abstract class Scene @JvmOverloads constructor(
    protected var context: Context?, val id: String?, //	public Scene(){
    //		
    //	}
    //	protected int level;
    var sceneLayerLevel: Int = -1, mode: Int = RESTART
) : Sprite() {
    protected var gameModel: IGameModel? = null
    protected var gameController: IGameController? = null
    var remoteController: IRemoteController? = null
    protected var isEnableRemoteController: Boolean = true

    @JvmField
    protected var mode: Int = RESTART

    //	public Time getTime(){
    //		return gameModel.getTime();
    //	}
    init {
        this.sceneLayerLevel = sceneLayerLevel
        this.mode = mode

        if (sceneLayerLevel >= 0) LayerManager.Companion.getInstance().setLayerBySenceIndex(
            sceneLayerLevel
        )

        initGameModel()
        initGameController()


//		if(sceneLayerLevel>=0)
//			gameModel.setTime(new Time);
    }

    abstract fun initGameModel()

    abstract fun initGameController()

    fun sceneWillStart(obj: Any?) {
    }

    fun startWithObj(obj: Any?) {
        sceneWillStart(obj)
        start()
    }

    fun start() {
        gameController!!.start()
    }

    open fun stop() {
        gameController!!.stop()
    }

    fun getMode(): Int {
        return mode
    }

    fun setMode(mode: Int) {
        this.mode = mode
        gameController!!.setFlag(this.mode)
    }

    fun addMode(mode: Int) {
        this.mode = this.mode or mode
        gameController!!.setFlag(this.mode)
    }

    fun removeMode(mode: Int) {
        this.mode = this.mode and mode.inv()
        gameController!!.setFlag(this.mode)
    }

    fun createDefaultRemoteController(): RemoteController? {
        return RemoteController.Companion.createRemoteController()
    }

    fun isEnableRemoteController(isEnableRemoteController: Boolean) {
        this.isEnableRemoteController = isEnableRemoteController
    }

    /*
	 * be care for setLayerLevel in Scene, because it relative with LayerManager and SceneManager.
	// */
    public override fun setLayerLevel(sceneLayerLevel: Int) {
        this.sceneLayerLevel = sceneLayerLevel
    }

    public override fun getLayerLevel(): Int {
        return this.sceneLayerLevel
    }

    fun addAutoDraw(layer: ALayer) {
        if (!layer.isAutoAdd()) {
            layer.setAutoAdd(true, sceneLayerLevel)
            //			LayerManager.getInstance().addSceneLayerByLayerLevel(layer, sceneLayerLevel);
        }


//		layer.setAutoAdd(true);
    }

    var viewBackgroundColor: Int
        //Maybe change the gameModel to gameController is better.
        get() = gameModel!!.getBackgroundColor()
        //Maybe change the gameModel to gameController is better.
        set(backgroundColor) {
            gameModel!!.setBackgroundColor(backgroundColor)
        }

    @get:kotlin.jvm.JvmName("getSceneCameraProperty")
    @set:kotlin.jvm.JvmName("setSceneCameraProperty")
    var camera: Camera?
        //Maybe change the gameModel to gameController is better.
        get() = gameModel!!.getCamera()
        //Maybe change the gameModel to gameController is better.
        set(camera) {
            gameModel!!.setCamera(camera)
        }

    //Maybe change the gameModel to gameController is better.
    fun addPreProcessBlock(processBlock: ProcessBlock?) {
        gameModel!!.addPreProcessBlock(processBlock)
    }

    //	@Override
    open fun finish() {
        // TODO Auto-generated method stub
//		super.finish();
//		((Activity)context).finish();
        setMode(FINISHED)
        gameModel!!.setData(DestoryData())
        LayerManager.Companion.getInstance().deleteSceneLayersBySceneLayerLevel(sceneLayerLevel)
    }

    inner class DestoryData : Data() {
        override fun getAllExistPoints(): Any? {
            // TODO Auto-generated method stub
            return null
        }

        override fun setAllExistPoints(allExistPoints: Any?) {
            // TODO Auto-generated method stub
        }

        override fun getAllExistPointsIterator(): MutableIterator<*>? {
            // TODO Auto-generated method stub
            return null
        }
    }

    companion object {
        const val RESTART: Int = 1
        const val RESUME: Int = 2
        const val RESUME_WITHOUT_SET_VIEW: Int = 4
        const val BLOCK: Int = 8
        const val FINISHED: Int = 16
        const val NOT_AUTO_START: Int = 32
    }
}
