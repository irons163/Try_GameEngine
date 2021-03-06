package com.example.try_gameengine.framework;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import com.example.try_gameengine.Camera.Camera;
import com.example.try_gameengine.action.MovementAction;
import com.example.try_gameengine.action.Time;
import com.example.try_gameengine.framework.GameController.BlockRunData;
import com.example.try_gameengine.scene.Scene;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * {@code GameModel} is a class to control the game loop and camera.
 * @author irons
 *
 */
public class GameModel implements IGameModel{
	protected Context context;
	protected IChessBoard chessBoard;
	protected IPlayerManager playerManager;
	protected IChessPointManager chessPointManager;
	protected Data data;
	protected int[][] allExistPoints;
	private SurfaceHolder surfaceHolder;
	protected boolean isGameStop = false;
	private boolean isGameReallyStop = false;
	private long startTime, endTime, previousStartTime;
	private long interval;
	private long startTimeForShowFPS;
	private boolean timeLock = false;
	protected boolean isGameRun = true;
	private long fpsCounter;
	float fps;
	Paint paint = new Paint();
	private int backgroundColor = Color.BLACK;
	private boolean canUseLockHardwareCanvas = false;
	Camera camera;
	Canvas canvas;
	private List<ProcessBlock> processBlocks = new CopyOnWriteArrayList<ProcessBlock>();
	private long frameInterval;
	
	/**
	 * Contructor.
	 * @param context
	 * 			the context of Activity or Scene.
	 * @param data
	 * 			the data for use in game model.
	 */
	public GameModel(Context context, Data data) {
		this.context = context;
		this.data = data;
		paint.setTextSize(50);
		paint.setColor(Config.debugMessageColor);
//		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
//			canUseLockHardwareCanvas = true;
	}
	
	/**
	 * get the start time for each loop start.  
	 * @return long.
	 */
	public long getStartTime(){
		return startTime;
	}
	
	/**
	 * get the end time for each loop end.
	 * @return
	 */
	public long getEndTime(){
		return endTime;
	}
	
	/**
	 * get interval time for each interval by each loop.
	 * @return
	 */
	public long getInterval(){
		return interval;
	}
	
	public void addPreProcessBlock(ProcessBlock processBlock){
		processBlocks.add(processBlock);
	}
	
	@Override
	public int getBackgroundColor() {
		return backgroundColor;
	}

	@Override
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	@Override
	public Camera getCamera(){
		return camera;
	}
	
	@Override
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
	
	protected void willProcess(){
		
	}
	
	private void doPreProcessBlock(){
		if(processBlocks.size()!=0){
			synchronized (processBlocks) {
				for(ProcessBlock processBlock : processBlocks){
					processBlock.runBlock();
				}
				processBlocks.clear();
			}
		}
	}
	
	/**
	 * process part is a part of game loop.
	 */
	protected void process(){
		
	}
	
	/**
	 * after process is a part of game loop.
	 */
	protected void didProcess(){
		if(getCamera()!=null)
			getCamera().bindLayerX();
	}
	

	
	/**
	 * draw is a part of game loop.
	 */
	private void draw(){
		try {
			if(canUseLockHardwareCanvas)
				canvas = surfaceHolder.getSurface().lockHardwareCanvas();
			else
				canvas = surfaceHolder.lockCanvas();

			if(camera==null)
				camera = new Camera(canvas.getWidth(), canvas.getHeight()); //If screen rotation, the size changed, then???
			
			camera.applyViewPort(canvas);
			canvas.concat(camera.getMatrix());
			
			canvas.drawColor(backgroundColor);

			doDraw(canvas);
			
			if(Config.showMovementActionThreadNumber){
				canvas.drawText(String.format("%d", MovementAction.getThreadPoolNumber()), 100, 85, paint);
			}
			
			if(Config.showAllThreadNumber){
				canvas.drawText(String.format("%d", Thread.activeCount()), 100, 120, paint);
			}
			
			endTime = System.currentTimeMillis();
			
			if(Config.showFPS){
				
				fpsCounter++;
				
				if(endTime - startTimeForShowFPS >= 1000){
					fps = fpsCounter*(1000.0f/(endTime - startTimeForShowFPS));
					fpsCounter = 0;
					timeLock = false;
				}	
				
				canvas.drawText(String.format("%.1f", fps), 100, 50, paint);
			}
			

		} 
		catch (Exception e) {      
	            if(!isGameStop){
	            	Log.e("GameModel", "draw Error");
	            	e.printStackTrace();
	            	throw new RuntimeException();
	            }
	    } 
		finally {
	            if (canvas != null)
	            	if(canUseLockHardwareCanvas)
	            		surfaceHolder.getSurface().unlockCanvasAndPost(canvas);
	            	else
	            		surfaceHolder.unlockCanvasAndPost(canvas);
		}
	}
	
	protected void doDraw(Canvas canvas){

	}
	
	/**
	 * This game thread is a thread of game loop. This is an important part of whole engine.
	 */
	Thread gameThread = new Thread(new Runnable() {
		
		@Override
		public void run() {;
			
			while(isGameRun){
				if(surfaceHolder==null) //when game scene start, the surfaceHolder may not stand by.
					continue;
				
				previousStartTime = startTime;
				startTime = System.currentTimeMillis();
				if(previousStartTime==0)
					previousStartTime = startTime;
					
				Time.DeltaTime = startTime - previousStartTime;
				if(Config.enableFPSInterval){
//					startTime = System.currentTimeMillis();
					if(!timeLock){		
						startTimeForShowFPS = startTime;
						timeLock = true;
					}				
				}else if(Config.showFPS){
					if(!timeLock){		
//						startTimeForShowFPS = System.currentTimeMillis();
						startTimeForShowFPS = startTime;
						timeLock = true;
					}	
				}
				
				willProcess();
				doPreProcessBlock();
				process();
				didProcess();
				draw();
				
				while (TouchDispatcher.getInstance().dispatch()) {
					
				};
				
				if(checkIsFPSIntervalArrive()){
		            try {  
		                Thread.sleep(frameInterval - interval);  
		            } catch (final Exception e) {  
		            }  
				}
				
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
	
	private boolean checkIsFPSIntervalArrive(){
		if(Config.enableFPSInterval){
			interval = endTime - startTime; 
			frameInterval = (long) (1000.0f/Config.fps);
			if (interval < frameInterval) {
				return false;
			}
		}
		
		return true;
	}
	
	public void resetTime(){
//		Time.time = 0;
		Time.Time = System.currentTimeMillis();
		Time.DeltaTime = 0;
		previousStartTime = startTime = 0;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		gameThread.start();
	}
	
	@Override
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
	}
	
	@Override
	public void restart(){
		if(data!=null && data instanceof BlockRunData && ((BlockRunData)data).getIsBlock()){
			Canvas canvas = surfaceHolder.lockCanvas();
			doDraw(canvas);
			surfaceHolder.unlockCanvasAndPost(canvas);
			((BlockRunData)data).setIsBlock(false);
			return;
		}
		isGameStop = false;
		if(!gameThread.isAlive()){
			resetTime();
			gameThread.start();
		}
		
		if(isGameReallyStop){
			isGameReallyStop = false;
			resetTime();
			gameLoopResume();
		}
	}
	
	/**
	 * resume the game loop after it wait.
	 */
	private void gameLoopResume(){
		synchronized (GameModel.this) {
			GameModel.this.notify();
		}	
	}

	/**
	 * Destroy the game model.
	 */
	private void destory(){
		if(gameThread.isAlive())
			gameThread.interrupt();
		System.gc();
	}
}
