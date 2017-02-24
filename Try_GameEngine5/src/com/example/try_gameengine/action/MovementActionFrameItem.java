package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.listener.IActionListener;
import com.example.try_gameengine.action.visitor.IMovementActionVisitor;

import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;

public class MovementActionFrameItem extends MovementAction{
	long millisTotal;
	long millisDelay;
	float dx;
	float dy;
	MovementActionInfo info;
	long resumeTotal;
	long resetTotal;
	boolean isStop = false;
	
	public MovementActionFrameItem(long millisTotal, long millisDelay, final int dx, final int dy){
		this(millisTotal, millisDelay, dx, dy, "MovementItem");
	}
	
	public MovementActionFrameItem(long millisTotal, long millisDelay, final int dx, final int dy, String description){
		this.millisTotal = millisTotal;
		this.millisDelay = millisDelay;
		this.dx = dx;
		this.dy = dy;
		info = new MovementActionInfo(millisTotal, millisDelay, dx, dy);
		this.description = description + ",";
		movementItemList.add(this);
	}
	
	public MovementActionFrameItem(MovementActionFrameInfo info){
		frameTimes = info.getFrame();
		dx = info.getDx();
		dy = info.getDy();
		if(info.getDescription()!=null)
			this.description = info.getDescription() + ",";
		this.info = info;
		movementItemList.add(this);
	}
	
	public MovementActionFrameItem(long[] frameTimes, final int dx, final int dy, String description){
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
					if(isStop)
						break;
					
					actionListener.beforeChangeFrame(resumeFrameIndex+1);

						try {
							Thread.sleep(frameTimes[resumeFrameIndex]);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

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
		});
		
		thread.start();
		
	}
	
	@Override
	protected MovementAction initTimer(){ super.initTimer();
		millisTotal = info.getTotal();
		millisDelay = info.getDelay();
		dx = info.getDx();
		dy = info.getDy();
		
		resumeFrameIndex = 0;
		
		return this;
	}
	
	private void doReset(){
		millisTotal = info.getTotal();
		millisDelay = info.getDelay();
		dx = info.getDx();
		dy = info.getDy();
		isStop = false;
	}
	
	@Override
	public boolean isFinish() {
		// TODO Auto-generated method stub
		return isStop;
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
		isStop = true;
	}
	
	@Override
	void pause(){
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
	
	@Override
	public void accept(IMovementActionVisitor movementActionVisitor){
		movementActionVisitor.visitLeaf(this);
	}
}
