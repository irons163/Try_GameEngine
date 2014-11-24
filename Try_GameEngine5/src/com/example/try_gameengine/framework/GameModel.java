package com.example.try_gameengine.framework;


import java.util.Iterator;

import android.content.Context;
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
	private boolean isGameStop = false;
	private boolean isGameReallyStop = false;
	private boolean isSurfaceCreated = false;
	
	public GameModel(Context context, Data data) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data = data;
		
//		initChessBoard();
//		initChessPointManager();
//		initPlayerManager();
//		
//		setPlayersBySquential();
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
	}
	
	@Override
	public void setSurfaceHolder(SurfaceHolder surfaceHolder){
		this.surfaceHolder = surfaceHolder;
	}
	
	protected void process(){
		
	}
	
	Canvas canvas;
	
	private void draw(){
//		try {
			canvas = surfaceHolder.lockCanvas();

			canvas.drawColor(Color.BLACK);

			doDraw(canvas);
//		 } 
//		catch (Exception e) {
//	            Log.v("GameModel", "draw Error");
//	        } finally {
//	            if (canvas != null)
//	            	surfaceHolder.unlockCanvasAndPost(canvas);
//	     }
			if (canvas != null)
            	surfaceHolder.unlockCanvasAndPost(canvas);
	}
	
	protected void doDraw(Canvas canvas){

	}

	
	Thread gameThread = new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(isGameRun){
				process();
				draw();
				if(isGameStop){
					synchronized (GameModel.this) {
						try {
							isGameReallyStop = true;
							GameModel.this.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
	}
	
	public void restart(){
		isGameStop = false;
		if(!gameThread.isAlive())
			gameThread.start();
		isSurfaceCreated = true;
		if(isGameReallyStop=true){
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

}
