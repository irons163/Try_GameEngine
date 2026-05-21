package com.example.try_gameengine.stage

import android.content.Context
import android.content.Intent

/**
 * `StageManager` use to manage the stages work, include add, start , stop, change , next , previous etc.
 * `StageManager` is a static class, it means only one StageManager used.
 * @author irons
 // */
object StageManager {
    /**
     * @return
     // */
    var stages: MutableList<Stage> = ArrayList<Stage>()

    /**
     * get current active stage.
     * @return current stage.
     // */
    var currentStage: Stage? = null
        private set
    private var currentStageIndex = 0
    private var context: Context? = null

    fun init(context: Context) {
        StageManager.context = context
    }

    fun init(currentActiveStage: Stage?) {
        currentStage = currentActiveStage
        addStage(currentActiveStage)
    }

    /**
     * add Stage into stageManager.
     * @param stage
     // */
    fun addStage(stage: Stage?) {
        stages.add(stage!!)
    }

    /**
     * @param id
     * @return
     // */
    fun getStage(id: String?): Stage? {
        var targetStage: Stage? = null
        for (i in stages.indices) {
            val stage = stages.get(i)
            if (stage.getId() == id) {
                targetStage = stage
            }
        }

        return targetStage
    }

    /**
     * @param id
     * @return
     // */
    fun getStageIndex(id: String?): Int {
        var targetStageIndex = -1
        for (i in stages.indices) {
            val stage = stages.get(i)
            if (stage.getId() == id) {
                targetStageIndex = i
            }
        }

        return targetStageIndex
    }

    /**
     * @param id
     // */
    fun startStage(id: String?) {
        if (currentStage != null) currentStage!!.stop()
        val stage = getStage(id)
        if (stage != null) {
            stage.start()
            currentStage = stage
        }
    }

    /**
     * @param id
     // */
    fun stopStage(id: String?) {
        val stage = getStage(id)
        if (stage != null) {
            stage.stop()
        }
    }

    /**
     * @param index
     // */
    fun startStage(index: Int) {
        if (currentStage != null) currentStage!!.stop()
        if (index >= 0 && index < stages.size) {
            val stage = stages.get(index)
            //			stage.start();
            StageManager.startStage(context!!, stage)
            currentStage = stage
            currentStageIndex = index
        }
    }

    /**
     * @param index
     // */
    fun stopStage(index: Int) {
        if (index >= 0 && index < stages.size) {
            stages.get(index).stop()
        }
    }

    /**
     * startSratge like start activity.
     * @param context
     * android context.
     * @param targetStage
     * target
     // */
    private fun startStage(context: Context, targetStage: Stage) {
        val intent = Intent(context, targetStage.javaClass)
        context.startActivity(intent)
    } // has problem: it create a new stage, not use the targetStage.

    /**
     * create next Stage and to next stage, not close current stage.
     // */
    fun next() {
        currentStageIndex++
        if (currentStage != null) currentStage!!.stop()
        if (currentStageIndex == stages.size) {
            currentStageIndex = 0
        }
        val stage = stages.get(currentStageIndex)
        stage.start()
        currentStage = stage
    }

    /**
     * changeStage to previous stage.
     * @param isCloseCurrentStage
     * to control when change, close current stage or not.
     // */
    /**
     * changeStage to previous stage, not close current stage.
     // */
    @JvmOverloads
    fun previous(isCloseCurrentStage: Boolean = false) {
        currentStageIndex--
        if (currentStage != null) currentStage!!.stop()
        if (currentStageIndex == -1) {
            currentStageIndex = stages.size - 1
        }
        val stage = stages.get(currentStageIndex)
        StageManager.changeStage(currentStage!!, stage, isCloseCurrentStage)
        currentStage = stage
    } // has problem: it create a new stage.
    // has problem: it create a new stage.

    /**
     * change stage from current to target class(Stage). The class can be a Activity because Stage also a kind of Activity.
     * @param currentStage
     * which stage used now.
     * @param cls
     * target class to show.
     * @param isCloseCurrentStage
     * to control when change, close current stage or not.
     // */
    fun changeStage(currentStage: Stage, cls: Class<*>?, isCloseCurrentStage: Boolean) {
        val intent = Intent(currentStage, cls)
        currentStage.startActivity(intent)
        stopStage(currentStage)
        if (isCloseCurrentStage) currentStage.finish()
    }

    /**
     * change stage from current to target class(Stage). The class can be a Activity because Stage also a kind of Activity.
     * @param currentStage
     * which stage used now.
     * @param cls
     * target class to show.
     * @param flag
     * the flag for android intent because stage is kind of activity.
     * @param isCloseCurrentStage
     * to control when change, close current stage or not.
     // */
    fun changeStage(currentStage: Stage, cls: Class<*>?, flag: Int, isCloseCurrentStage: Boolean) {
        val intent = Intent(currentStage, cls)
        intent.addFlags(flag)
        currentStage.startActivity(intent)
        if (isCloseCurrentStage) currentStage.finish()
    }

    /**
     * change stage from current to target class(Stage). The class can be a Activity because Stage also a kind of Activity.
     * @param currentStage
     * @param StargeId
     * @param isCloseCurrentStage
     * to control when change, close current stage or not.
     // */
    fun changeStage(currentStage: Stage, StargeId: String?, isCloseCurrentStage: Boolean) {
        val stage = getStage(StargeId)
        val intent = Intent(currentStage, stage!!.javaClass)
        currentStage.startActivity(intent)
        if (isCloseCurrentStage) currentStage.finish()
    }

    //	public void changeStage(int StargeIndex){
    //		
    //	}
    /**
     * change stage from current stage to target stage.
     * @param currentStage
     * which stage used now.
     * @param targetStage
     * target stage used now.
     * @param isCloseCurrentStage
     * to control when change, close current stage or not.
     // */
    fun changeStage(currentStage: Stage, targetStage: Stage, isCloseCurrentStage: Boolean) {
        val intent = Intent(currentStage, targetStage.javaClass)
        currentStage.startActivity(intent)
        if (isCloseCurrentStage) currentStage.finish()
    }

    /**
     * stop stage.
     * @param currentStage
     // */
    private fun stopStage(currentStage: Stage) {
        currentStage.stop()
    }
}
