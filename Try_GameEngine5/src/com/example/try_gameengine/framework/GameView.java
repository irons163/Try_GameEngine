package com.example.try_gameengine.framework;


import com.example.try_gameengine.application.GameGlobalVariable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, IMoveObserver{
	protected IGameController gameController;
	private IGameModel gameModel;
	private SurfaceHolder surfaceHolder;
	
	
	private IChessBoard chessBoard;
	private int[][] allExistPoints;
	
	public int viewHeight;
	public int viewWidth;
	
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
		// TODO Auto-generated method stub
		super.onTouchEvent(event);
		
		gameController.onTouchEvent(event);
		
		System.out.println("onTouchEvent");
		
//		return super.onTouchEvent(event);
		return true;
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		viewHeight = height;
		viewWidth = width;
		GameGlobalVariable.surfaceHolder = holder;
		gameController.surfaceChanged(holder, format, width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		GameGlobalVariable.surfaceHolder = holder;
		gameController.setSurfaceHolder(surfaceHolder);
		gameController.runStart();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		GameGlobalVariable.surfaceHolder = null;
		gameController.stop();
	}

}
