package com.example.try_gameengine.framework

import android.app.Activity
import android.view.MotionEvent
import android.view.SurfaceHolder
import com.example.try_gameengine.scene.Scene

/**
 * @author irons
 // */
abstract class GameController : IGameController {
    protected var gameModel: IGameModel
    protected var activity: Activity?
    protected var gameView: GameView? = null
    protected var sceneMode: Int = Scene.Companion.RESTART
    private var isGameViewCreated = false
    private var isBlocRunStart = false

    private val touchListener: ITouchable? = null

    /**
     * Constructor.
     * @param activity
     * activity about the android activity or `Stage`.
     * @param gameModel
     * gamemodel about the the game loop and the game detail in.
     // */
    constructor(activity: Activity?, gameModel: IGameModel) {
        this.gameModel = gameModel
        this.activity = activity
    }

    /**
     * Constructor.
     * @param activity
     * activity about the android activity or `Stage`.
     * @param gameModel
     * gamemodel about the the game loop and the game detail in.
     * @param sceneMode
     * the scene mode.
     // */
    constructor(activity: Activity?, gameModel: IGameModel, sceneMode: Int) {
        this.gameModel = gameModel
        this.activity = activity
        this.sceneMode = sceneMode
    }

    override fun setFlag(sceneMode: Int) {
        if ((sceneMode and Scene.Companion.BLOCK) != 0) {
            isBlocRunStart = true
        } else {
            isBlocRunStart = false
        }
    }

    /**
     * init start with sceneMode.
     * @param sceneMode
     * the scene mode.
     // */
    /**
     * init start.
     // */
    protected fun initStart(sceneMode: Int = this.sceneMode) {
        if ((sceneMode and Scene.Companion.BLOCK) != 0) {
            isBlocRunStart = true
        } else {
            isBlocRunStart = false
        }

        if ((sceneMode and Scene.Companion.RESTART) != 0) {
            isBlocRunStart = false
            createGameview()
            setActivityContentView(activity)
        } else if ((sceneMode and Scene.Companion.RESUME) != 0) {
            isBlocRunStart = false
            if (!isGameViewCreated) {
                createGameview()
                setActivityContentView(activity)
                isGameViewCreated = true
            } else {
//				if(gameView!=null &&Utils.checkViewExist(activity.getWindow().getDecorView(), gameView)){
//					setActivityContentView(activity);
//				}else{
//					runStart();
//				}

                if (gameView != null) {
                    setActivityContentView(activity)
                } else {
                    runStart()
                }
            }
        } else if ((sceneMode and Scene.Companion.RESUME_WITHOUT_SET_VIEW) != 0) {
            isBlocRunStart = false
            runStart()
        } else if ((sceneMode and Scene.Companion.FINISHED) != 0) {
        } else if ((sceneMode and Scene.Companion.NOT_AUTO_START) != 0) {
        }
    }

    private fun createGameview() {
        gameView = initGameView(activity, gameModel)
        arrangeView()
    }

    protected abstract fun initGameView(activity: Activity?, gameModel: IGameModel?): GameView?

    protected abstract fun arrangeView()

    protected abstract fun setActivityContentView(activity: Activity?)

    override fun start() {
        // TODO Auto-generated method stub
//		gameModel.start();
        gameModel.restart()
    }

    override fun stop() {
        // TODO Auto-generated method stub
        beforeGameStop()
        gameModel.stop()
        afterGameStop()
    }

    override fun showWin() {
        // TODO Auto-generated method stub
    }

    override fun showLose() {
        // TODO Auto-generated method stub
    }

    override fun onTouchEvent(event: MotionEvent?) {
//		if(touchListener==null){
//			touchListener = new ITouchable() {
//
//				@Override
//				public boolean onTouchEvent(MotionEvent event) {
//					gameModel.onTouchEvent(event);
//					return false;
//				}
//
//				@Override
//				public void onTouchMoved(MotionEvent event) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onTouchEnded(MotionEvent event) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public void onTouchCancelled(MotionEvent event) {
//					// TODO Auto-generated method stub
//
//				}
//
//				@Override
//				public boolean onTouchBegan(MotionEvent event) {
//					// TODO Auto-generated method stub
//					return false;
//				}
//			};
//				
//			TouchDispatcher.getInstance().addToFirstStandardTouchDelegate(touchListener);
//		}
//		
//		TouchDispatcher.getInstance().onTouchEvent(event);

        gameModel.onTouchEvent(event)
    }

    override fun setSurfaceHolder(surfaceHolder: SurfaceHolder?) {
        gameModel.setSurfaceHolder(surfaceHolder)
    }

    /**
     * `BlockRunData` is a Data to tell the game loop block running.
     * @author irons
     // */
    internal inner class BlockRunData : Data() {
        var isBlock: Boolean = false

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

    override fun runStart() {
        if (!isBlocRunStart) {
            beforeGameStart()
            gameModel.restart()
            afterGameStart()
        } else {
            val data = BlockRunData()
            data.isBlock = true
            gameModel.setData(data)
            gameModel.restart()
        }
    }

    protected abstract fun beforeGameStart()

    protected abstract fun afterGameStart()

    protected open fun beforeGameStop() {
        //do something
    }

    protected open fun afterGameStop() {
        //do something
    }

    internal interface OnViewCreateListener
}
