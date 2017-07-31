package com.example.try_gameengine.framework;

import java.util.Iterator;

import com.example.try_gameengine.scene.Scene;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * @author irons
 *
 */
public abstract class GameController implements IGameController{
	protected IGameModel gameModel;
	protected Activity activity;
	protected GameView gameView;
	protected int sceneMode = Scene.RESTART;
	private boolean isGameViewCreated = false;
	private boolean isBlocRunStart = false;
	
	private ITouchable touchListener;
	
	/**
	 * Constructor.
	 * @param activity
	 * 			activity about the android activity or {@code Stage}.
	 * @param gameModel
	 * 			gamemodel about the the game loop and the game detail in.
	 */
	public GameController(Activity activity, IGameModel gameModel) {
		this.gameModel = gameModel;
		this.activity = activity;
	}
	
	/**
	 * Constructor.
	 * @param activity
	 * 			activity about the android activity or {@code Stage}.  
	 * @param gameModel
	 * 			gamemodel about the the game loop and the game detail in.
	 * @param sceneMode
	 * 			the scene mode.
	 */
	public GameController(Activity activity, IGameModel gameModel, int sceneMode) {
		this.gameModel = gameModel;
		this.activity = activity;
		this.sceneMode = sceneMode;
	}
	
	@Override
	public void setFlag(int sceneMode) {
		if((sceneMode&Scene.BLOCK)!=0){
			isBlocRunStart = true;
		}else{
			isBlocRunStart = false;
		}
	}
	
	/**
	 * init start.
	 */
	protected void initStart(){
		initStart(sceneMode);
	}
	
	/**
	 * init start with sceneMode.
	 * @param sceneMode
	 * 			the scene mode.
	 */
	protected void initStart(int sceneMode){
		if((sceneMode&Scene.BLOCK)!=0){
			isBlocRunStart = true;
		}else{
			isBlocRunStart = false;
		}
		
		if((sceneMode&Scene.RESTART)!=0){
			isBlocRunStart = false;
			createGameview();
			setActivityContentView(activity);
		}else if((sceneMode&Scene.RESUME)!=0){
			isBlocRunStart = false;
			if(!isGameViewCreated){
				createGameview();
				setActivityContentView(activity);
				isGameViewCreated = true;
			}else{
//				if(gameView!=null &&Utils.checkViewExist(activity.getWindow().getDecorView(), gameView)){
//					setActivityContentView(activity);
//				}else{
//					runStart();
//				}
				
				if(gameView!=null){
					setActivityContentView(activity);
				}else{
					runStart();
				}
			}
		}else if((sceneMode&Scene.RESUME_WITHOUT_SET_VIEW)!=0){
			isBlocRunStart = false;
			runStart();
		}else if((sceneMode&Scene.FINISHED)!=0){
			
		}else if((sceneMode&Scene.NOT_AUTO_START)!=0){
			
		}
	}
	
	private void createGameview(){
		gameView = initGameView(activity, gameModel);
		arrangeView();
	}
	
	protected abstract GameView initGameView(Activity activity, IGameModel gameModel);
	
	protected abstract void arrangeView(); 
	
	protected abstract void setActivityContentView(Activity activity);

	@Override
	public void start() {
		// TODO Auto-generated method stub
//		gameModel.start();
		gameModel.restart();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		beforeGameStop();
		gameModel.stop();
		afterGameStop();
	}

	@Override
	public void showWin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showLose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
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
		
		gameModel.onTouchEvent(event);
	}

	@Override
	public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
		gameModel.setSurfaceHolder(surfaceHolder);
	}
	
	/**
	 * {@code BlockRunData} is a Data to tell the game loop block running. 
	 * @author irons
	 *
	 */
	class BlockRunData extends Data{
		private boolean isBlock = false;
		
		@Override
		public Object getAllExistPoints() {
			// TODO Auto-generated method stub
			return null;
		}
	
		@Override
		public void setAllExistPoints(Object allExistPoints) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public Iterator getAllExistPointsIterator() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public boolean getIsBlock(){
			return isBlock;
		}
		
		public void setIsBlock(boolean isBlock){
			this.isBlock = isBlock;
		}
	}

	public void runStart(){
		if(!isBlocRunStart){
			beforeGameStart();
			gameModel.restart();
			afterGameStart();
		}else{
			BlockRunData data = new BlockRunData();
			data.setIsBlock(true);
			gameModel.setData(data);
			gameModel.restart();
		}
	}

	protected abstract void beforeGameStart();
	
	protected abstract void afterGameStart();
	
	protected void beforeGameStop(){
		//do something
	}
	
	protected void afterGameStop(){
		//do something
	}
	
	interface OnViewCreateListener{
		
	}
}
