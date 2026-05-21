package com.example.try_gameengine.stage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.try_gameengine.scene.SceneManager

/**
 * `Stage` is first thing of whole system, it entends Activity and it like a window.
 * It is also a place to show scenes display. The architecture is :
 * Stage -> Scene -> Layer.
 * @author irons
 // */
abstract class Stage : Activity() {
    /**
     * get stage id
     * @return String
     // */
    val id: String? = null

    protected var level: Int = 0
    private var sceneManager: SceneManager? = null

    /**
     * Construct and init for addSatge to StageManager.
     // */
    init {
        StageManager.addStage(this)
    }

    /**
     * init own SceneManager
     * @return
     // */
    abstract fun initSceneManager(): SceneManager

    /**
     * start stage like startActivity.
     // */
    fun start() {
        startActivity(Intent(getApplicationContext(), javaClass))
    }

    /**
     * stop all scenes in this stage.
     // */
    fun stop() {
        sceneManager!!.stopAllScenes()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * init stage.
     // */
    protected fun initStage() {
        StageManager.init(currentActiveStage = this) //init self in StageManger 
        sceneManager = initSceneManager() // init scene manager.
    }

    /**
     * get own scene manager.
     * @return SceneManager
     // */
    fun getSceneManager(): SceneManager {
        return sceneManager!!
    }

    override fun onBackPressed() {
        if (!sceneManager!!.previousWithExistedScenes()) finish()
    }

    override fun finish() {
        super.finish()
        sceneManager!!.removeAllScenes()
        Log.d("Stage", "Finish.")
    }

    override fun onDestroy() {
        super.onDestroy()
        sceneManager!!.removeAllScenes()
        Log.d("Stage", "Destroy.")
    }

    companion object {
        val CLEAR_TASK: Int = Intent.FLAG_ACTIVITY_CLEAR_TASK
        val CLEAR_TOP: Int = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val CLEAR_WHEN_TASK_RESET: Int = Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
        val XCLUDE_FROM_RECENTS: Int = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
        val FORWARD_RESULT: Int = Intent.FLAG_ACTIVITY_FORWARD_RESULT
        val NEW_TASK: Int = Intent.FLAG_ACTIVITY_NEW_TASK
        val NO_ANIMATION: Int = Intent.FLAG_ACTIVITY_NO_ANIMATION
        val NO_HISTORY: Int = Intent.FLAG_ACTIVITY_NO_HISTORY
        val PREVIOUS_IS_TOP: Int = Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
        val REORDER_TO_FRONT: Int = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        val SINGLE_TOP: Int = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val TASK_ON_HOME: Int = Intent.FLAG_ACTIVITY_TASK_ON_HOME
    }
}
