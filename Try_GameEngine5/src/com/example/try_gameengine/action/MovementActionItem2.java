package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import android.util.Log;

import com.example.try_gameengine.action.MovementAction.MovementActionMementoImpl;
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener;
import com.example.try_gameengine.action.MovementActionSetWithThreadPool.MovementActionSetWithThreadPoolMementoImpl;
import com.example.try_gameengine.action.listener.IActionListener;
import com.example.try_gameengine.action.visitor.IMovementActionVisitor;

public class MovementActionItem2 extends MovementActionItem{ 
	private long updateTime;	
	public int frameIdx;	
	public boolean isCycleFinish = false;	
	boolean triggerEnable = false;	
	long pauseFrameNum;
	int pauseFrameCounter;	
	FrameTrigger nextframeTrigger;
//	int resumeFrameCount;
//	private long lastTriggerFrameNum;
	long resumeMillisCount = 0;
	long lastMillisCount = 0;
	private boolean isEnableSetSpriteAction = true;
	
	public MovementActionItem2(long millisTotal, long millisDelay, final int dx, final int dy){
		this(millisTotal, millisDelay, dx, dy, "MovementItem");
	}
	
	public MovementActionItem2(long millisTotal, long millisDelay, final int dx, final int dy, String description){
		super(millisTotal, millisDelay, dx, dy, description);
	}
	
	public MovementActionItem2(MovementActionInfo info){
		super(info);
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
	public void setTimer() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub

		
		resumeFrameIndex = 0;
//		resumeFrameCount = 0;
		resumeMillisCount = 0;
		pauseFrameNum = 0;
		pauseFrameCounter = 0;
		isStop = false;
		isCycleFinish = false;
		if(!isEnableSetSpriteAction)
			isEnableSetSpriteAction = isRepeatSpriteActionIfMovementActionRepeat;
		if(info.getSprite()!=null && isEnableSetSpriteAction)
			info.getSprite().setAction(info.getSpriteActionName());
		
		triggerEnable = true;
		isEnableSetSpriteAction = isRepeatSpriteActionIfMovementActionRepeat;
		
		actionListener.actionStart();
	}
	
	
	
	public interface FrameTrigger{
		public void trigger();
	}
	
	
	
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
	
	
	
	private void frameTriggerFPSStart(){
		if (!isStop) {
			synchronized (MovementActionItem2.this) {

			do{	
				if(resumeMillisCount >=info.getDelay()){
					if(resumeMillisCount>=info.getTotal())
						isCycleFinish = true;
				}
				
				if(isCycleFinish){
					resumeMillisCount = 0;
					lastMillisCount = 0;
				}
				
				resumeMillisCount += Time.DeltaTime;
				
				if(!isLoop && isCycleFinish){
					isStop = true;
					doReset();	
					triggerEnable = false;
					if(actionListener!=null)
						actionListener.actionFinish();
					
					MovementActionItem2.this.notifyAll();
				}else if(resumeMillisCount >= lastMillisCount + info.getDelay()){
					doRotation();
					doGravity();
					if(timerOnTickListener!=null)
						timerOnTickListener.onTick(dx, dy);		
					lastMillisCount += info.getDelay();
				}
				
				if(isCycleFinish){
					if(actionListener!=null)
						actionListener.actionCycleFinish();
					isCycleFinish = false;
					break;
				}
			}while(resumeMillisCount >= lastMillisCount + info.getDelay());
			/*
			if(resumeFrameCount>=info.getDelay()){	
				if(resumeFrameCount==info.getTotal())
					isCycleFinish = true;
			}
			
			if(isCycleFinish){
				resumeFrameCount = 0;
				lastTriggerFrameNum = 0;
			}
			
			resumeFrameCount++;
			
			if(!isLoop && isCycleFinish){
				isStop = true;
				doReset();	
				triggerEnable = false;
				if(actionListener!=null)
					actionListener.actionFinish();
				
				MovementActionItem2.this.notifyAll();
			}else if(resumeFrameCount==lastTriggerFrameNum+info.getDelay()){
				doRotation();
				doGravity();
				if(timerOnTickListener!=null)
					timerOnTickListener.onTick(dx, dy);		
				lastTriggerFrameNum += info.getDelay();
				
			// add by 150228. if the delay change by main app, the function: else if(resumeFrameCount==lastTriggerFrameNum+info.getDelay() maybe make problem.
			}else if(resumeFrameCount>lastTriggerFrameNum+info.getDelay()){ 
//				resumeFrameCount--;
//				lastTriggerFrameNum++;
				lastTriggerFrameNum = resumeFrameCount+1-info.getDelay();
			}
			
			if(isCycleFinish){
				if(actionListener!=null)
					actionListener.actionCycleFinish();
				isCycleFinish = false;
			}
			*/
			}
		}else{
			synchronized (MovementActionItem2.this) {
				MovementActionItem2.this.notifyAll();
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
		currentInfoList.add(this.info);
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
		synchronized (MovementActionItem2.this) {
			MovementActionItem2.this.notifyAll();
		}
	}
	
	@Override
	void pause(){	
		pauseFrameNum = millisDelay;
	}
	
	@Override
	public boolean isFinish(){
		return isStop;
	}
	
	public IMovementActionMemento createMovementActionMemento(){
		movementActionMemento = new MovementActionItemBaseReugularFPSMementoImpl(actions, thread, timerOnTickListener, name, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCycleFinish, isCycleFinish, isCycleFinish, isCycleFinish, name, cancelAction, millisTotal, millisDelay, dx, dy, info, resumeTotal, resetTotal, rotationController, gravityController, name, updateTime, frameIdx, isStop, isCycleFinish, triggerEnable, frameTimes, resumeMillisCount, pauseFrameNum, pauseFrameCounter, nextframeTrigger, lastMillisCount);
		if(this.info!=null){
			this.info.createIMovementActionInfoMemento();
		}
		return movementActionMemento;
	}
	
	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
//		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento;
		super.restoreMovementActionMemento(this.movementActionMemento);
		MovementActionItemBaseReugularFPSMementoImpl mementoImpl = (MovementActionItemBaseReugularFPSMementoImpl) this.movementActionMemento;
		this.millisTotal = mementoImpl.millisTotal;
		this.millisDelay = mementoImpl.millisDelay;
		this.dx = mementoImpl.dx;
		this.dy = mementoImpl.dy;
		this.info = mementoImpl.info;
		this.resumeTotal = mementoImpl.resumeTotal;
		this.resetTotal = mementoImpl.resetTotal;
		this.rotationController = mementoImpl.rotationController;
		this.gravityController = mementoImpl.gravityController;
		this.name = mementoImpl.name;
		this.updateTime = mementoImpl.updateTime;
		this.frameIdx = mementoImpl.frameIdx;
		this.isStop = mementoImpl.isStop;
		this.isCycleFinish = mementoImpl.isCycleFinish;
		this.triggerEnable = mementoImpl.triggerEnable;
		this.frameTimes = mementoImpl.frameTimes;
		this.resumeMillisCount = mementoImpl.resumeMillisCount;
		this.pauseFrameNum = mementoImpl.pauseFrameNum;
		this.pauseFrameCounter = mementoImpl.pauseFrameCounter;
		this.nextframeTrigger = mementoImpl.nextframeTrigger;
		this.lastMillisCount = mementoImpl.lastMillisCount;
//		this.isEnableSetSpriteAction = mementoImpl.isEnableSetSpriteAction;
		
		if(this.info!=null){
			this.info.restoreMovementActionMemento(null);
		}
		doReset();

	}
	
	protected static class MovementActionItemBaseReugularFPSMementoImpl extends MovementActionMementoImpl{
	
		long millisTotal;
		long millisDelay;
		float dx;
		float dy;
		MovementActionInfo info;
		long resumeTotal;
		long resetTotal;
		IRotationController rotationController;
		IGravityController gravityController;	
		public String name;	
		private long updateTime;	
		public int frameIdx;	
		public boolean isStop = false;
		public boolean isCycleFinish = false;	
		boolean triggerEnable = false;	
		long[] frameTimes;
//		int resumeFrameCount;	
		long pauseFrameNum;
		int pauseFrameCounter;	
		FrameTrigger nextframeTrigger;
//		private long lastTriggerFrameNum;
		long resumeMillisCount = 0;
		long lastMillisCount = 0;
//		private boolean isEnableSetSpriteAction = true;
		
		public MovementActionItemBaseReugularFPSMementoImpl(
				List<MovementAction> actions, Thread thread,
				TimerOnTickListener timerOnTickListener, String description,
				List<MovementAction> copyMovementActionList,
				List<MovementActionInfo> currentInfoList,
				List<MovementAction> movementItemList,
				List<MovementAction> totalCopyMovementActionList,
				boolean isCancelFocusAppendPart, boolean isFinish,
				boolean isLoop, boolean isSigleThread, String name,
				MovementAction cancelAction, long millisTotal,
				long millisDelay, float dx, float dy, MovementActionInfo info,
				long resumeTotal, long resetTotal,
				IRotationController rotationController,
				IGravityController gravityController, String name2,
				long updateTime, int frameIdx, boolean isStop,
				boolean isCycleFinish, boolean triggerEnable,
				long[] frameTimes, long resumeMillisCount,
				long pauseFrameNum, int pauseFrameCounter,
				FrameTrigger nextframeTrigger, long lastMillisCount) {
			super(actions, thread, timerOnTickListener, description,
					copyMovementActionList, currentInfoList, movementItemList,
					totalCopyMovementActionList, isCancelFocusAppendPart,
					isFinish, isLoop, isSigleThread, name, cancelAction);
			this.millisTotal = millisTotal;
			this.millisDelay = millisDelay;
			this.dx = dx;
			this.dy = dy;
			this.info = info;
			this.resumeTotal = resumeTotal;
			this.resetTotal = resetTotal;
			this.rotationController = rotationController;
			this.gravityController = gravityController;
			name = name2;
			this.updateTime = updateTime;
			this.frameIdx = frameIdx;
			this.isStop = isStop;
			this.isCycleFinish = isCycleFinish;
			this.triggerEnable = triggerEnable;
			this.frameTimes = frameTimes;
			this.resumeMillisCount = resumeMillisCount;
			this.pauseFrameNum = pauseFrameNum;
			this.pauseFrameCounter = pauseFrameCounter;
			this.nextframeTrigger = nextframeTrigger;
			this.lastMillisCount = lastMillisCount;
//			this.isEnableSetSpriteAction = isEnableSetSpriteAction;
		}
			
	}
	
	@Override
	public void accept(IMovementActionVisitor movementActionVisitor){
		movementActionVisitor.visitLeaf(this);
	}
}
