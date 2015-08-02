package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.listener.IActionListener;
import com.example.try_gameengine.action.visitor.IMovementActionVisitor;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class MovementActionItem extends MovementAction{
	CountDownTimer countDownTimer; 
	long millisTotal;
	long millisDelay;
	float dx;
	float dy;
	MovementActionInfo info;
	long resumeTotal;
	long resetTotal;
	IRotationController rotationController;
	IGravityController gravityController;
	boolean isReset = true;
	
	public MovementActionItem(long millisTotal, long millisDelay, final int dx, final int dy){
		this(millisTotal, millisDelay, dx, dy, "MovementItem");
	}
	
	public MovementActionItem(long millisTotal, long millisDelay, final int dx, final int dy, String description){
		this.millisTotal = millisTotal;
		this.millisDelay = millisDelay;
		this.dx = dx;
		this.dy = dy;
		info = new MovementActionInfo(millisTotal, millisDelay, dx, dy);
		this.description = description + ",";
		movementItemList.add(this);
	}
	
	public MovementActionItem(MovementActionInfo info){
		millisTotal = info.getTotal();
		millisDelay = info.getDelay();
		dx = info.getDx();
		dy = info.getDy();
		if(info.getDescription()!=null)
			this.description = info.getDescription() + ",";
		this.info = info;
		movementItemList.add(this);
	}
	
	@Override
	public void trigger() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void setTimer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		if(isActionFinish){
			isReset = true;
			thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(isReset){
						isReset = false;
						synchronized (MovementActionItem.this) {
							countDownTimer.start();
							try {
								MovementActionItem.this.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								isActionFinish =false;
							}
						}
					
						
					}
				}
			});
			thread.start();
		}
		
		isActionFinish = false;
		if(isFirstTime){
			resetTotal = millisTotal;
			isFirstTime = false;
			
			thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(isReset){
						isReset = false;
						synchronized (MovementActionItem.this) {
							countDownTimer.start();
							try {
								MovementActionItem.this.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								isActionFinish =false;
							}
						}
					
						
					}
				}
			});
			thread.start();
		}
		
		if(info.getSprite()!=null)
		info.getSprite().setAction(info.getSpriteActionName());

	}
	
	long[] frameTimes;
	int resumeFrameIndex;
	
	private void frameStart(){
		
		for(; resumeFrameIndex < frameTimes.length; resumeFrameIndex++){
			doRotation();
			doGravity();
			timerOnTickListener.onTick(dx, dy);
		}
		
		doReset();		
	}
	
	public String name;
	
	private long updateTime;
	
	public int frameIdx;
	
	public boolean isStop = false;
	
	private void irregularFrameStart(){
		
		IActionListener actionListener = null;
		
			if (System.currentTimeMillis() > updateTime && !isStop) {
				actionListener.beforeChangeFrame(frameIdx+1);
				frameIdx++;
				frameIdx %= frameTimes.length;
				
				if(!isLoop && frameIdx==0){
					isStop = true;
					doReset();
					actionListener.actionFinish();
				}else{
					updateTime = System.currentTimeMillis() + frameTimes[frameIdx];
					
					doRotation();
					doGravity();
					timerOnTickListener.onTick(dx, dy);
					
					int periousId = frameIdx-1<0 ? frameTimes.length+(frameIdx-1) : frameIdx-1;
					actionListener.afterChangeFrame(periousId);
				}
		}
		
		
	}
	
	boolean isFirstTime = true;
	
	@Override
	protected MovementAction initTimer(){
		millisTotal = info.getTotal();
		millisDelay = info.getDelay();
		dx = info.getDx();
		dy = info.getDy();
		rotationController = info.getRotationController();
		gravityController = info.getGravityController();
		
		resumeFrameIndex = 0;
		
		countDownTimer = new CountDownTimer(millisTotal,
				millisDelay) {

			@Override
			public void onTick(long millisUntilFinished) {
				Log.e("t", millisUntilFinished+"");
				Log.e("t", millisUntilFinished/1000+"");
				// TODO Auto-generated method stub
				float x = dx;
				float y = dy;
				
				doRotation();
				doGravity();
				Log.e("dx", dx+"");
				Log.e("dy", dy+"");
				
				resumeTotal = millisUntilFinished;
				timerOnTickListener.onTick(dx, dy);
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				if(isLoop){
//					doReset();
					handler.sendEmptyMessage(0);
					Log.e("Timer", "loop");
				}else{
					synchronized (MovementActionItem.this) {
						MovementActionItem.this.notifyAll();
					}			
					doReset();
					isActionFinish = true;
					Log.e("Timer", "finish");
				}
			}
		};
		return this;
	}
	
	private void doRotation(){
		if(rotationController!=null){
			rotationController.execute(info);
			dx = info.getDx();
			dy = info.getDy();
		}
	}
	
	private void doGravity(){
		if(gravityController!=null){
			gravityController.execute(info);
			dx = info.getDx();
			dy = info.getDy();
		}
	}
	
	private void doReset(){
		if(gravityController!=null){
			gravityController.reset(info);
		}
		if(rotationController!=null)
			rotationController.reset(info);

		
		millisTotal = info.getTotal();
		millisDelay = info.getDelay();
		dx = info.getDx();
		dy = info.getDy();
		initTimer();
	}
	
	@Override
	public MovementAction getAction(){
		return this;
	}
	
	public List<MovementAction> getActions(){
		return actions;
	}

	@Override
	public MovementActionInfo getInfo() {
		// TODO Auto-generated method stub
		return info;
	}

	@Override
	public void setInfo(MovementActionInfo info) {
		// TODO Auto-generated method stub
		this.info = info;
	}
	
	@Override
	public List<MovementAction> getCurrentActionList() {
		// TODO Auto-generated method stub
		List<MovementAction> actions = new ArrayList<MovementAction>();
		actions.add(this);
		return actions;
	}
	
	@Override
	public List<MovementActionInfo> getCurrentInfoList() {
		// TODO Auto-generated method stub
		List<MovementActionInfo> infos = new ArrayList<MovementActionInfo>();
		infos.add(this.info);
		return infos;
	}
	
	@Override
	public List<MovementActionInfo> getMovementInfoList() {
		List<MovementActionInfo> infos = new ArrayList<MovementActionInfo>();
		infos.add(this.info);
		return infos;
	}
	
	@Override
	public void cancelMove(){
		countDownTimer.cancel();
	}
	
	Handler handler = new Handler(Looper.getMainLooper()){
		public void handleMessage(android.os.Message msg) {
			initTimer();
			info.setTotal(resetTotal);
			isReset = true;
			thread.interrupt();
			start();
		};
	};
	
	@Override
	void pause(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(!isActionFinish){
					countDownTimer.cancel();
					
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					millisTotal =resumeTotal;
					info.setTotal(millisTotal);
					handler.sendEmptyMessage(0);
				}
			}
		}).start();
		

	}
	
	private boolean isActionFinish = false;
	
	@Override
	public boolean isFinish(){
		return isActionFinish;
	}
	
	@Override
	public void accept(IMovementActionVisitor movementActionVisitor){
		movementActionVisitor.visitLeaf(this);
	}
}
