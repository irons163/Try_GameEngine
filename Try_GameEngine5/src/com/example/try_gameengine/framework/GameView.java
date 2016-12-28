package com.example.try_gameengine.framework;


import com.example.try_gameengine.application.GameGlobalVariable;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * {@code GameView} is a class that entends a surface view and the main display for the engine.
 * @author irons
 *
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback, IMoveObserver{
	protected IGameController gameController;
	private IGameModel gameModel;
	private SurfaceHolder surfaceHolder;
	
	private int viewHeight;
	private int viewWidth;
	
	/**
	 * Constructor. 
	 * @param context
	 * 			context can be a activity of stage.
	 * @param gameController
	 * 			game controller is a .
	 * @param gameModel
	 * 			game model is the main model of the game. 
	 */
	public GameView(Context context, IGameController gameController, IGameModel gameModel) {
		super(context);
		// TODO Auto-generated constructor stub
		this.gameController = gameController;
		this.gameModel = gameModel;
		gameModel.registerObserver(this);
		
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
	}

	@Override
	public void updateChess(IChessBoard chessBoard) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		gameController.onTouchEvent(event);
		return true;
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		viewHeight = height;
		viewWidth = width;
		GameGlobalVariable.surfaceHolder = holder;
		gameController.surfaceChanged(holder, format, width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		GameGlobalVariable.surfaceHolder = holder;
		gameController.setSurfaceHolder(surfaceHolder);
		gameController.runStart();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		GameGlobalVariable.surfaceHolder = null;
		gameController.stop();
	}

}
