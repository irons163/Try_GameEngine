package com.example.try_gameengine.framework

import android.content.Context
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.try_gameengine.application.GameGlobalVariable


/**
 * `GameView` is a class that entends a surface view and the main display for the engine.
 * @author irons
 // */
open class GameView(context: Context?, gameController: IGameController, gameModel: IGameModel) :
    SurfaceView(context), SurfaceHolder.Callback, IMoveObserver {
    protected var gameController: IGameController
    private val gameModel: IGameModel?
    private val surfaceHolder: SurfaceHolder

    private var viewHeight = 0
    private var viewWidth = 0

    /**
     * Constructor.
     * @param context
     * context can be a activity of stage.
     * @param gameController
     * game controller is a .
     * @param gameModel
     * game model is the main model of the game.
     // */
    init {
        // TODO Auto-generated constructor stub
        this.gameController = gameController
        this.gameModel = gameModel
        gameModel.registerObserver(this)

        //		gameController.setGameview();
        surfaceHolder = getHolder()
        surfaceHolder.addCallback(this)
    }

    override fun updateChess(chessBoard: IChessBoard?) {
        // TODO Auto-generated method stub
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        gameController.onTouchEvent(event)
        return true
    }


    override fun surfaceChanged(
        holder: SurfaceHolder, format: Int, width: Int,
        height: Int
    ) {
        viewHeight = height
        viewWidth = width
        GameGlobalVariable.surfaceHolder = holder
        gameController.surfaceChanged(holder, format, width, height)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        GameGlobalVariable.surfaceHolder = holder
        gameController.setSurfaceHolder(surfaceHolder)
        gameController.runStart()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        GameGlobalVariable.surfaceHolder = null
        gameController.stop()
    }
}
