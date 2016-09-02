package com.example.try_gameengine.framework;


import java.util.Iterator;

import com.example.try_gameengine.Camera.Camera;
import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.framework.GameController.BlockRunData;
import com.example.try_gameengine.scene.Scene;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class GameModel implements IGameModel{
	protected Context context;
	protected IChessBoard chessBoard;
	protected IPlayerManager playerManager;
	protected IChessPointManager chessPointManager;
	protected Data data;
	protected int[][] allExistPoints;
//	protected Iterator<MyEnemy> allExistPointsIterator;
	private SurfaceHolder surfaceHolder;
	protected boolean isGameStop = false;
	private boolean isGameReallyStop = false;
	private boolean isSurfaceCreated = false;
	private long startTime, endTime;
	private long interval;
	private long startTimeForShowFPS;
	private boolean timeLock = false;
	private long fpsCounter;
	float fps;
	Paint paint = new Paint();
	private int backgroundColor = Color.BLACK;
//	private Bitmap lastCanvas;
	private boolean canUseLockHardwareCanvas = false;
	
	public GameModel(Context context, Data data) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;
		paint.setTextSize(50);
		paint.setColor(Config.debugMessageColor);
//		initChessBoard();
//		initChessPointManager();
//		initPlayerManager();
//		
//		setPlayersBySquential();
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
			canUseLockHardwareCanvas = true;
	}
	
	public long getStartTime(){
		return startTime;
	}
	
	public long getEndTime(){
		return endTime;
	}
	
	public long getInterval(){
		return interval;
	}
	
//	protected void initChessBoard(){
//		chessBoard = new ChessBoard(CommonUtil.screenWidth, CommonUtil.screenHeight, 8, 8);
//		chessBoard.createLines();
//		chessBoard.createPoints();
//		allExistPoints = chessBoard.getAllExistPoints();
////		allExistPointsIterator = chessBoard.get
//		data.setAllExistPoints(allExistPoints);
//	}
//	
//	protected void initChessPointManager(){
//		chessPointManager = new ChessPointManager(context, chessBoard.getLineDistance(), chessBoard.getLineDistance());
//	}
//	
//	protected void initPlayerManager(){
//		playerManager = new PlayerManager(chessBoard, chessPointManager);
//		Logic logic = new Logic(allExistPoints);
//		playerManager.setLogic(logic);
////		playerManager.setPlayersBySquential(players);
////		playerManager.setBoard(jumpChessBoard);
//	}
//	
//	protected void setPlayersBySquential(){
//		chessBoard.setPlayersBySquential(playerManager.getPlayersBySquential());
//	}
	
	@Override
	public int getBackgroundColor() {
		return backgroundColor;
	}


	@Override
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Camera getCamera(){
		return camera;
	}
	
	public void setCamera(Camera camera){
		this.camera = camera;
	}

	@Override
	public void registerObserver(IMoveObserver moveObserver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void removeObserver(IMoveObserver moveObserver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(playerManager!=null)
			playerManager.onTouchEvent(event);
	}

	@Override
	public Data getData() {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public void setData(Data data) {
		// TODO Auto-generated method stub
		this.data = data;
		if(data instanceof Scene.DestoryData){
			destory();
		}
	}
	
	@Override
	public void setSurfaceHolder(SurfaceHolder surfaceHolder){
		this.surfaceHolder = surfaceHolder;
	}
	
	protected void process(){
		
	}
	
	protected void afterProcess(){
		if(getCamera()!=null)
			getCamera().bindLayerX();
	}
	
	Camera camera;
	
	Canvas canvas;
	
	private void draw(){
		try {
//			canvas = surfaceHolder.lockCanvas();
			if(canUseLockHardwareCanvas)
				canvas = surfaceHolder.getSurface().lockHardwareCanvas();
			else
				canvas = surfaceHolder.lockCanvas();

			if(camera==null)
				camera = new Camera(canvas.getWidth(), canvas.getHeight());
			
			camera.applyViewPort(canvas);
			canvas.concat(camera.getMatrix());
			
//			if(camera.getViewPortRectF()!=null)
//				canvas.clipRect(camera.getViewPortRectF());
			
			canvas.drawColor(backgroundColor);

			doDraw(canvas);
			
//			if(camera.getViewPort()!=null){
//				canvas.save(Canvas.MATRIX_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
//				canvas.setMatrix(camera.getViewPort().getMatrix());
//				LayerManager.drawHUDLayers(canvas, paint);
//				canvas.restore();
//			}
			
			if(Config.showFPS){
				
				fpsCounter++;
				endTime = System.currentTimeMillis();
				if(endTime - startTimeForShowFPS >= 1000){
					
					fps = fpsCounter*(1000.0f/(endTime - startTimeForShowFPS));
					fpsCounter = 0;
					timeLock = false;
				}	
				
				canvas.drawText(String.format("%.1f", fps), 100, 50, paint);
			}
			
			if(Config.showMovementActionThreadNumber){
				canvas.drawText(String.format("%d", MovementAction.getThreadPoolNumber()), 100, 85, paint);
			}
			
			if(Config.showAllThreadNumber){
				canvas.drawText(String.format("%d", Thread.activeCount()), 100, 120, paint);
			}
			
			if(Config.enableFPSInterval){
				endTime = System.currentTimeMillis();
				interval = endTime - startTime; 
				long frameInterval = (long) (1000.0f/Config.fps);
				if (interval < frameInterval) {  
		            try {  
		                // because we render it before, so we should sleep twice time interval  
		                Thread.sleep(frameInterval - interval);  
		            } catch (final Exception e) {  
		            }  
				}
			}
		} 
		catch (Exception e) {      
	            if(!isGameStop){
	            	Log.e("GameModel", "draw Error");
//	            	Log.e("GameModel Error Msg", e.printStackTrace());
	            	e.printStackTrace();
	            	throw new RuntimeException();
//	            	e.printStackTrace();
	            }
	    } 
		finally {
	            if (canvas != null)
//	            	surfaceHolder.unlockCanvasAndPost(canvas);
	            	if(canUseLockHardwareCanvas)
	            		surfaceHolder.getSurface().unlockCanvasAndPost(canvas);
	            	else
	            		surfaceHolder.unlockCanvasAndPost(canvas);
		}
//			if (canvas != null)
//            	surfaceHolder.unlockCanvasAndPost(canvas);
	}
	
	protected void doDraw(Canvas canvas){

	}

	
	Thread gameThread = new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isGameRun){
				if(surfaceHolder==null) //when game scene start, the surfaceHolder may not stand by.
					continue;
				if(Config.enableFPSInterval){
					startTime = System.currentTimeMillis();
					if(!timeLock){		
						startTimeForShowFPS = startTime;
						timeLock = true;
					}				
				}else if(Config.showFPS){
					if(!timeLock){		
						startTimeForShowFPS = System.currentTimeMillis();
						timeLock = true;
					}	
				}
				
				process();
				afterProcess();
				draw();
				if(isGameStop){
					synchronized (GameModel.this) {
						try {
							isGameReallyStop = true;
							GameModel.this.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							break;
						}
					}
				}
			}
		}
	});
	
	protected boolean isGameRun = true;
	
//	protected void gameThreadStart(){
//		
//	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		gameThread.start();
	}
	
	public void stop(){
		isGameStop = true;
		for(int i =0; i< 20; i++){
			if(isGameReallyStop)
				break;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		if(lastCanvas!=null){
//			lastCanvas.recycle();
//			System.gc();
//		}
		
//		lastCanvas = Bitmap.createBitmap(surfaceHolder.getSurfaceFrame().width(), surfaceHolder.getSurfaceFrame().height(), Bitmap.Config.);
//		Canvas c = new Canvas(lastCanvas);
//		doDraw(c);
	}
	
	public void restart(){
		if(data!=null && data instanceof BlockRunData && ((BlockRunData)data).getIsBlock()){
			Canvas canvas = surfaceHolder.lockCanvas();
//			canvas.drawBitmap(lastCanvas, 0, 0, null);
			doDraw(canvas);
			surfaceHolder.unlockCanvasAndPost(canvas);
			((BlockRunData)data).setIsBlock(false);
			return;
		}
		isGameStop = false;
		if(!gameThread.isAlive())
			gameThread.start();
		isSurfaceCreated = true;
//		if(isGameReallyStop=true){
		if(isGameReallyStop){
			isGameReallyStop = false;
			r();
		}
		
//		for(int i =0; i< 100; i++){
//			if(isSurfaceCreated)
//				break;
//			try {
//				Thread.sleep(200);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}	
	}
	
	private void r(){
		synchronized (GameModel.this) {
			GameModel.this.notify();
		}	
	}

	private void destory(){
//		if(lastCanvas!=null)
//			lastCanvas.recycle();
		if(gameThread.isAlive())
			gameThread.interrupt();
		System.gc();
	}
}
