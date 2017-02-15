package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.listener.IActionListener;
import com.example.try_gameengine.action.visitor.IMovementActionVisitor;

public class MovementActionItemBaseReugularFPS extends MovementActionItem{ 
	long numberOfFramesTotal;
	long numberOfFramesInterval;
	float dx;
	float dy;
	long resumeTotal;
	long resetTotal;
	public boolean isCycleFinish = false;	
		
//	long resumeFrameIndex;
	long resumeFrameCount;
	long numberOfPauseFrames;
	long pauseFrameCounter;	
	FrameTrigger nextframeTrigger;
	long numberOfFramesAfterLastTrigger;
	private boolean isEnableSetSpriteAction = true;
	private FrameTimesType frameTimesType = FrameTimesType.FrameTimesIntervalBeforeAction;
	
	public enum FrameTimesType{ //Default = FrameTimesIntervalBeforeAction
		FrameTimesIntervalBeforeAction, //wait interval->Action->wait interval->Action->end
		FrameTimesIntervalAfterAction //Action->wait interval->Action->wait interval->end
	};
	
	public MovementActionItemBaseReugularFPS(long frameTimesTotal, long frameTimesInterval, final int dx, final int dy){
		this(frameTimesTotal, frameTimesInterval, dx, dy, "MovementItem");
	}
	
	public MovementActionItemBaseReugularFPS(long frameTimesTotal, long frameTimesInterval, final int dx, final int dy, String description){
		super(new MovementActionInfo(frameTimesTotal, frameTimesInterval, dx, dy), description);
		
		this.numberOfFramesTotal = frameTimesTotal;
		this.numberOfFramesInterval = frameTimesInterval;
		this.dx = dx;
		this.dy = dy;
	}
	
	public MovementActionItemBaseReugularFPS(MovementActionInfo info){
		super(info);
		numberOfFramesTotal = info.getTotal();
		numberOfFramesInterval = info.getDelay();
		dx = info.getDx();
		dy = info.getDy();
		if(info.getDescription()!=null)
			this.description = info.getDescription() + ",";
		this.info = info;
	}
	
	@Override
	public void start() {
//		resumeFrameIndex = 0;
		resumeFrameCount = 0;
		numberOfPauseFrames = 0;
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
		if(triggerEnable && pauseFrameCounter==numberOfPauseFrames){
			numberOfPauseFrames = 0;
			pauseFrameCounter = 0;
			myTrigger.trigger();
		}else if(triggerEnable){
			pauseFrameCounter++;
		}else{
			numberOfPauseFrames = 0;
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
			synchronized (MovementActionItemBaseReugularFPS.this) {
				if(isCycleFinish)
					isCycleFinish = false;
			
			resumeFrameCount++;
			
			if(resumeFrameCount==numberOfFramesAfterLastTrigger+info.getDelay()){
				doRotation();
				doGravity();
				if(timerOnTickListener!=null)
					timerOnTickListener.onTick(dx, dy);		
				numberOfFramesAfterLastTrigger += info.getDelay();
				
			// add by 150228. if the delay change by main app, the function: else if(resumeFrameCount==lastTriggerFrameNum+info.getDelay() maybe make problem.
			}else if(resumeFrameCount>numberOfFramesAfterLastTrigger+info.getDelay()){ 
//				resumeFrameCount--;
//				lastTriggerFrameNum++;
				numberOfFramesAfterLastTrigger = resumeFrameCount+1-info.getDelay();
			}
			
			if(resumeFrameCount>=info.getDelay()){	
				if(resumeFrameCount==info.getTotal())
					isCycleFinish = true;
			}
			
			if(isCycleFinish){
				resumeFrameCount = 0;
				numberOfFramesAfterLastTrigger = 0;
			}
			
			if(!isLoop && isCycleFinish){
				isStop = true;
				doReset();	
				triggerEnable = false;
			}
			
			if(isCycleFinish){
				if(actionListener!=null)
					actionListener.actionCycleFinish();
			}
			
			if(isStop){
				if(actionListener!=null)
					actionListener.actionFinish();
				
				MovementActionItemBaseReugularFPS.this.notifyAll();
			}
			
			}
		}else{
			if(actionListener!=null)
				actionListener.actionFinish();
			
			synchronized (MovementActionItemBaseReugularFPS.this) {
				MovementActionItemBaseReugularFPS.this.notifyAll();
			}
		}
	}
	
	@Override
	protected MovementAction initTimer(){
		numberOfFramesTotal = info.getTotal();
		numberOfFramesInterval = info.getDelay();
		dx = info.getDx();
		dy = info.getDy();
		rotationController = info.getRotationController();
		gravityController = info.getGravityController();
		
//		resumeFrameIndex = 0;
		initLastTriggerFrameNum();
		return this;
	}
	
	private void initLastTriggerFrameNum(){
		switch (frameTimesType) {
		
		case FrameTimesIntervalBeforeAction:
			numberOfFramesAfterLastTrigger = 0; //wait interval->Action->wait interval->Action->end, if total = 9 interval = 3 then 3->6->9->end(9)
			break;
		case FrameTimesIntervalAfterAction:
			numberOfFramesAfterLastTrigger = (-info.getDelay() + 1); //Action->wait interval->Action->wait interval->end, if total = 9 interval = 3 then 1->4->7->end(9)
			break;
		}
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


		numberOfFramesTotal = info.getTotal();
		numberOfFramesInterval = info.getDelay();
		dx = info.getDx();
		dy = info.getDy();
		initLastTriggerFrameNum();
	}
	
	public FrameTimesType getFrameTimesType() {
		return frameTimesType;
	}

	public void setFrameTimesType(FrameTimesType frameTimesType) {
		this.frameTimesType = frameTimesType;
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
		//notifyAll in trigger().
		/*
		synchronized (MovementActionItemBaseReugularFPS.this) {
			MovementActionItemBaseReugularFPS.this.notifyAll();
		}
		*/
	}
	
	@Override
	void pause(){	
		numberOfPauseFrames = numberOfFramesInterval;
	}
	
	@Override
	public boolean isFinish(){
		return isStop;
	}
	
//	public IMovementActionMemento createMovementActionMemento(){
//		movementActionMemento = new MovementActionItemBaseReugularFPSMementoImpl(actions, thread, timerOnTickListener, name, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCycleFinish, isCycleFinish, isCycleFinish, isCycleFinish, name, cancelAction, numberOfFramesTotal, numberOfFramesInterval, dx, dy, info, resumeTotal, resetTotal, rotationController, gravityController, name, updateTime, frameIdx, isStop, isCycleFinish, triggerEnable, frameTimes, resumeFrameIndex, resumeFrameCount, numberOfPauseFrames, pauseFrameCounter, nextframeTrigger, numberOfFramesAfterLastTrigger);
//		if(this.info!=null){
//			this.info.createIMovementActionInfoMemento();
//		}
//		return movementActionMemento;
//	}
//	
//	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
////		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento;
//		super.restoreMovementActionMemento(this.movementActionMemento);
//		MovementActionItemBaseReugularFPSMementoImpl mementoImpl = (MovementActionItemBaseReugularFPSMementoImpl) this.movementActionMemento;
//		this.numberOfFramesTotal = mementoImpl.millisTotal;
//		this.numberOfFramesInterval = mementoImpl.millisDelay;
//		this.dx = mementoImpl.dx;
//		this.dy = mementoImpl.dy;
//		this.info = mementoImpl.info;
//		this.resumeTotal = mementoImpl.resumeTotal;
//		this.resetTotal = mementoImpl.resetTotal;
//		this.rotationController = mementoImpl.rotationController;
//		this.gravityController = mementoImpl.gravityController;
//		this.name = mementoImpl.name;
//		this.updateTime = mementoImpl.updateTime;
//		this.frameIdx = mementoImpl.frameIdx;
//		this.isStop = mementoImpl.isStop;
//		this.isCycleFinish = mementoImpl.isCycleFinish;
//		this.triggerEnable = mementoImpl.triggerEnable;
//		this.frameTimes = mementoImpl.frameTimes;
//		this.resumeFrameIndex = mementoImpl.resumeFrameIndex;
//		this.resumeFrameCount = mementoImpl.resumeFrameCount;
//		this.numberOfPauseFrames = mementoImpl.pauseFrameNum;
//		this.pauseFrameCounter = mementoImpl.pauseFrameCounter;
//		this.nextframeTrigger = mementoImpl.nextframeTrigger;
//		this.numberOfFramesAfterLastTrigger = mementoImpl.lastTriggerFrameNum;
////		this.isEnableSetSpriteAction = mementoImpl.isEnableSetSpriteAction;
//		
//		if(this.info!=null){
//			this.info.restoreMovementActionMemento(null);
//		}
//		doReset();
//
//	}
//	
//	protected static class MovementActionItemBaseReugularFPSMementoImpl extends MovementActionMementoImpl{
//	
//		long millisTotal;
//		long millisDelay;
//		float dx;
//		float dy;
//		MovementActionInfo info;
//		long resumeTotal;
//		long resetTotal;
//		IRotationController rotationController;
//		IGravityController gravityController;	
//		public String name;	
//		private long updateTime;	
//		public int frameIdx;	
//		public boolean isStop = false;
//		public boolean isCycleFinish = false;
//		boolean triggerEnable = false;	
//		long[] frameTimes;
//		int resumeFrameIndex;
//		int resumeFrameCount;	
//		long pauseFrameNum;
//		int pauseFrameCounter;	
//		FrameTrigger nextframeTrigger;
//		private long lastTriggerFrameNum;
////		private boolean isEnableSetSpriteAction = true;
//		
//		public MovementActionItemBaseReugularFPSMementoImpl(
//				List<MovementAction> actions, Thread thread,
//				TimerOnTickListener timerOnTickListener, String description,
//				List<MovementAction> copyMovementActionList,
//				List<MovementActionInfo> currentInfoList,
//				List<MovementAction> movementItemList,
//				List<MovementAction> totalCopyMovementActionList,
//				boolean isCancelFocusAppendPart, boolean isFinish,
//				boolean isLoop, boolean isSigleThread, String name,
//				MovementAction cancelAction, long millisTotal,
//				long millisDelay, float dx, float dy, MovementActionInfo info,
//				long resumeTotal, long resetTotal,
//				IRotationController rotationController,
//				IGravityController gravityController, String name2,
//				long updateTime, int frameIdx, boolean isStop,
//				boolean isCycleFinish, boolean triggerEnable,
//				long[] frameTimes, int resumeFrameIndex, int resumeFrameCount,
//				long pauseFrameNum, int pauseFrameCounter,
//				FrameTrigger nextframeTrigger, long lastTriggerFrameNum) {
//			super(actions, thread, timerOnTickListener, description,
//					copyMovementActionList, currentInfoList, movementItemList,
//					totalCopyMovementActionList, isCancelFocusAppendPart,
//					isFinish, isLoop, isSigleThread, name, cancelAction);
//			this.millisTotal = millisTotal;
//			this.millisDelay = millisDelay;
//			this.dx = dx;
//			this.dy = dy;
//			this.info = info;
//			this.resumeTotal = resumeTotal;
//			this.resetTotal = resetTotal;
//			this.rotationController = rotationController;
//			this.gravityController = gravityController;
//			name = name2;
//			this.updateTime = updateTime;
//			this.frameIdx = frameIdx;
//			this.isStop = isStop;
//			this.isCycleFinish = isCycleFinish;
//			this.triggerEnable = triggerEnable;
//			this.frameTimes = frameTimes;
//			this.resumeFrameIndex = resumeFrameIndex;
//			this.resumeFrameCount = resumeFrameCount;
//			this.pauseFrameNum = pauseFrameNum;
//			this.pauseFrameCounter = pauseFrameCounter;
//			this.nextframeTrigger = nextframeTrigger;
//			this.lastTriggerFrameNum = lastTriggerFrameNum;
////			this.isEnableSetSpriteAction = isEnableSetSpriteAction;
//		}
//			
//	}
	
	@Override
	public void accept(IMovementActionVisitor movementActionVisitor){
		movementActionVisitor.visitLeaf(this);
	}
}
