package com.example.try_gameengine.scene;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.example.try_gameengine.framework.Data;
import com.example.try_gameengine.framework.GameController;
import com.example.try_gameengine.framework.GameModel;
import com.example.try_gameengine.framework.GameView;
import com.example.try_gameengine.framework.IGameController;
import com.example.try_gameengine.framework.IGameModel;
import com.example.try_gameengine.framework.IMoveObserver;
import com.example.try_gameengine.scene.Scene;

public abstract class EasyScene extends Scene{
//	EasyGameModel gameModel;
	
	public EasyScene(Context context, String id) {
		super(context, id);
		// TODO Auto-generated constructor stub
	}
	
	public EasyScene(Context context, String id, int level) {
		super(context, id, level);
		// TODO Auto-generated constructor stub
	}
	
	public EasyScene(Context context, String id, int level, int mode) {
		super(context, id, level, mode);
		// TODO Auto-generated constructor stub
	}
	
	private void setGameView(){
		gameModel = new EasyGameModel(context, null);
//		gameView = new EasyGameModel(context, null);
	}
	
	private void setGameController(){
		gameController = new EasyGameController((Activity)context, gameModel);
	}

	@Override
	public void initGameModel() {
		// TODO Auto-generated method stub
		gameModel = new EasyGameModel(context, null);
	}
	
	@Override
	public void initGameController() {
		// TODO Auto-generated method stub
		gameController = new EasyGameController((Activity)context, gameModel);
	}
	
//	@Override
//	public void start() {
//		// TODO Auto-generated method stub
//		super.start();
//	}
//	
//	@Override
//	public void stop() {
//		// TODO Auto-generated method stub
//		super.stop();
//	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public abstract void initGameView(Activity activity, IGameController gameController,IGameModel gameModel);

//	public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
//		// TODO Auto-generated method stub
//		gameModel.setSurfaceHolder(surfaceHolder);
//	}
//
//	public void restart() {
//		// TODO Auto-generated method stub
//		gameModel.restart();
//	}
	
	public abstract void process();
	
	public abstract void doDraw(Canvas canvas);
	
	public abstract void beforeGameStart();

	public abstract void arrangeView(Activity activity);

	public abstract void setActivityContentView(Activity activity);
	
	public abstract void afterGameStart();
	
	public abstract void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height);
	
	class EasyGameController extends GameController {

		public EasyGameController(Activity activity, IGameModel gameModel) {
			super(activity, gameModel);
			// TODO Auto-generated constructor stub
			
		}

		@Override
		protected void initGameView(Activity activity, IGameModel gameModel) {
			// TODO Auto-generated method stub
			EasyScene.this.initGameView(activity, this, gameModel);
		}

		@Override
		public void arrangeView() {
			// TODO Auto-generated method stub
			EasyScene.this.arrangeView(activity);
		}

		@Override
		protected void setActivityContentView(Activity activity) {
			// TODO Auto-generated method stub
			EasyScene.this.setActivityContentView(activity);
		}
		
		@Override
		public void beforeGameStart() {
			// TODO Auto-generated method stub
			EasyScene.this.beforeGameStart();
		}

		@Override
		public void afterGameStart() {
			// TODO Auto-generated method stub
			EasyScene.this.afterGameStart();
		}
		
		@Override
		public void start() {
			// TODO Auto-generated method stub
			initStart(mode);		
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			EasyScene.this.surfaceChanged(holder, format, width, height);
		}
		
	}

	class EasyGameModel extends GameModel {

//		public EasyGameView(Context context, IGameController gameController,
//				IGameModel gameModel) {
//			super(context, gameController, gameModel);
//			// TODO Auto-generated constructor stub
//		}

		public EasyGameModel(Context context, Data data) {
			super(context, data);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void process() {
			// TODO Auto-generated method stub
//			super.process();
			EasyScene.this.process();
		}
		
		@Override
		public void doDraw(Canvas canvas) {
			// TODO Auto-generated method stub
//			super.doDraw(canvas);
			EasyScene.this.doDraw(canvas);
		}
		
		@Override
		public void onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			EasyScene.this.onTouchEvent(event);
//			super.onTouchEvent(event);
		}
	}
}

