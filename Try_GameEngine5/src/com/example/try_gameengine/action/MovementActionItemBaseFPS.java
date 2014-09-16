package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.listener.IActionListener;

import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;

public class MovementActionItemBaseFPS extends MovementActionItem{
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
	
	public MovementActionItemBaseFPS(long millisTotal, long millisDelay, final int dx, final int dy){
		this(millisTotal, millisDelay, dx, dy, "MovementItem");
	}
	
	public MovementActionItemBaseFPS(long millisTotal, long millisDelay, final int dx, final int dy, String description){
		super(millisTotal, millisDelay, dx, dy, description);
		
		this.millisTotal = millisTotal;
		this.millisDelay = millisDelay;
		this.dx = dx;
		this.dy = dy;
		info = new MovementActionInfo(millisTotal, millisDelay, dx, dy);
		this.description = description + ",";
		movementItemList.add(this);
	}
	
	public MovementActionItemBaseFPS(MovementActionFrameInfo info){
		super(info);
		
		frameTimes = info.getFrame();
		dx = info.getDx();
		dy = info.getDy();
		if(info.getDescription()!=null)
			this.description = info.getDescription() + ",";
		this.info = info;
		movementItemList.add(this);
	}
	
	public MovementActionItemBaseFPS(long[] frameTimes, final int dx, final int dy, String description){
		super(0, 0, dx, dy, description);
		
		this.frameTimes = frameTimes;
		this.dx = dx;
		this.dy = dy;
		info = new MovementActionInfo(millisTotal, millisDelay, dx, dy);
		this.description = description + ",";
		movementItemList.add(this);
	}
	
	@Override
	public void setTimer() {
		// TODO Auto-generated method stub

	}

	boolean triggerEnable = false;
	
	@Override
	public void start() {
		// TODO Auto-generated method stub

		triggerEnable = true;
		resumeFrameIndex = 0;
		resumeFrameCount = 0;
		pauseFrameNum = 0;
		pauseFrameCounter = 0;
		isStop = false;
		isCycleFinish = false;
		info.getSprite().setAction(info.getSpriteActionName());
		
	}
	
	long[] frameTimes;
	int resumeFrameIndex;
	int resumeFrameCount;
	
	public interface FrameTrigger{
		public void trigger();
	}
	
	int pauseFrameNum;
	int pauseFrameCounter;
	
	@Override
	public void trigger(){
		if(triggerEnable && pauseFrameCounter==pauseFrameNum){
			pauseFrameNum = 0;
			pauseFrameCounter = 0;
			myTrigger.trigger();
		}else if(triggerEnable){
			pauseFrameCounter++;
		}else{
			pauseFrameNum = 0;
			pauseFrameCounter = 0;
		}
	}
	
	private void nextFrameTrigger(){
		myTrigger.trigger();
		nextframeTrigger.trigger();
	}
	
	FrameTrigger nextframeTrigger;
	FrameTrigger myTrigger = new FrameTrigger() {
		
		@Override
		public void trigger() {
			// TODO Auto-generated method stub
			frameTriggerFPSStart();
			
		}
	};
	public FrameTrigger setNextFrameTrigger(FrameTrigger nextframeTrigger){
		
		this.nextframeTrigger = nextframeTrigger;
		
		return myTrigger;
	}
	
	public void setActionListener(IActionListener actionListener){
		this.actionListener = actionListener;
	}
	
	private IActionListener actionListener;
	
	private void frameStart(){
		Thread thread = new Thread(new Runnable() {	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				for(; resumeFrameIndex < frameTimes.length; resumeFrameIndex++){
					actionListener.beforeChangeFrame(resumeFrameIndex+1);
						try {
							Thread.sleep(frameTimes[resumeFrameIndex]);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					doRotation();
					doGravity();
					timerOnTickListener.onTick(dx, dy);
					resumeFrameCount = 0;
				}
				
				doReset();
				actionListener.actionFinish();
			}
		});
		
		thread.start();
		
	}
	
	private void frameTriggerStart(){
		if (!isStop) {
			actionListener.beforeChangeFrame(resumeFrameIndex+1);
			if(resumeFrameCount>frameTimes[resumeFrameIndex]){	
				resumeFrameIndex++;
				resumeFrameIndex %= frameTimes.length;
				resumeFrameCount = 0;
				if(resumeFrameIndex==0)
					isCycleFinish = true;
			}
			
			if(!isLoop && isCycleFinish){
				isStop = true;
				doReset();
				actionListener.actionFinish();
			}else if(resumeFrameCount==frameTimes[resumeFrameIndex]){
				updateTime = System.currentTimeMillis() + frameTimes[resumeFrameIndex];	
				doRotation();
				doGravity();
				timerOnTickListener.onTick(dx, dy);
				resumeFrameCount++;
				int periousId = resumeFrameIndex-1<0 ? frameTimes.length+(resumeFrameIndex-1) : resumeFrameIndex-1;
				actionListener.afterChangeFrame(periousId);
			}
		}
		
		doReset();
	}
	
	private void frameTriggerFPSStart(){
		if (!isStop) {
			actionListener.beforeChangeFrame(resumeFrameIndex+1);
	
			if(resumeFrameCount>=frameTimes[resumeFrameIndex]){	
				resumeFrameIndex++;
				resumeFrameIndex %= frameTimes.length;
				resumeFrameCount = 0;
				if(resumeFrameIndex==0)
					isCycleFinish = true;
			}
			
			resumeFrameCount++;
			
			if(!isLoop && isCycleFinish){
				isStop = true;
				doReset();
				actionListener.actionFinish();
			}else if(resumeFrameCount==frameTimes[resumeFrameIndex]){
				doRotation();
				doGravity();
				timerOnTickListener.onTick(dx, dy);		
				int periousId = resumeFrameIndex-1<0 ? frameTimes.length+(resumeFrameIndex-1) : resumeFrameIndex-1;
				actionListener.afterChangeFrame(periousId);
			}
		}
		
		doReset();
	}
	
	public boolean isLoop;
	
	public String name;
	
	private long updateTime;
	
	public int frameIdx;
	
	public boolean isStop = false;
	public boolean isCycleFinish = false;
	
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
				synchronized (MovementActionItemBaseFPS.this) {
					MovementActionItemBaseFPS.this.notifyAll();
				}			
				doReset();
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
	
	@Override
	void pause(){	
		pauseFrameNum = 1000;
	}
}
