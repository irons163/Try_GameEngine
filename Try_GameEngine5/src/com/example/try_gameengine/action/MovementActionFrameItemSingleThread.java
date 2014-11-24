package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.listener.IActionListener;

import android.os.Looper;
import android.util.Log;

public class MovementActionFrameItemSingleThread extends MovementAction{
	long millisTotal;
	long millisDelay;
	float dx;
	float dy;
	MovementActionInfo info;
	long resumeTotal;
	long resetTotal;
	IRotationController rotationController;
	IGravityController gravityController;
	
	public MovementActionFrameItemSingleThread(long millisTotal, long millisDelay, final int dx, final int dy){
		this(millisTotal, millisDelay, dx, dy, "MovementItem");
	}
	
	public MovementActionFrameItemSingleThread(long millisTotal, long millisDelay, final int dx, final int dy, String description){
		this.millisTotal = millisTotal;
		this.millisDelay = millisDelay;
		this.dx = dx;
		this.dy = dy;
		info = new MovementActionInfo(millisTotal, millisDelay, dx, dy);
		this.description = description + ",";
		movementItemList.add(this);
	}
	
	public MovementActionFrameItemSingleThread(MovementActionFrameInfo info){
		frameTimes = info.getFrame();
		dx = info.getDx();
		dy = info.getDy();
		if(info.getDescription()!=null)
			this.description = info.getDescription() + ",";
		this.info = info;
		movementItemList.add(this);
	}
	
	public MovementActionFrameItemSingleThread(long[] frameTimes, final int dx, final int dy, String description){
		this.frameTimes = frameTimes;
		this.dx = dx;
		this.dy = dy;
		info = new MovementActionInfo(millisTotal, millisDelay, dx, dy);
		this.description = description + ",";
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
		info.getSprite().setAction(info.getSpriteActionName());
		frameStart();	
	}
	
	long[] frameTimes;
	int resumeFrameIndex;
	int resumeFrameCount;
	
	public interface FrameTrigger{
		public void trigger();
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
			frameStart();
			
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
//					actionListener.afterChangeFrame(periousId);
					resumeFrameCount = 0;
					
					if(isLoop && resumeFrameIndex==frameTimes.length-1){
						resumeFrameIndex = -1;
					}
				}
				
				doReset();
				actionListener.actionFinish();
		
	}
	
	public String name;
	
	private long updateTime;
	
	public int frameIdx;
	
	public boolean isStop = false;
	
	@Override
	protected MovementAction initTimer(){
		millisTotal = info.getTotal();
		millisDelay = info.getDelay();
		dx = info.getDx();
		dy = info.getDy();
		rotationController = info.getRotationController();
		gravityController = info.getGravityController();
		
		resumeFrameIndex = 0;
		
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
//		countDownTimer.cancel();
	}
	
	@Override
	void pause(){
//		countDownTimer.cancel();
		
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resetTotal = millisTotal;
		millisTotal =resumeTotal;
		info.setTotal(millisTotal);
		initTimer();
		start();
	}
}
