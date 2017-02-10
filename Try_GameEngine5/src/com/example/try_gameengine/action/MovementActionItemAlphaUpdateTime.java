package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.listener.IActionListener;
import com.example.try_gameengine.action.visitor.IMovementActionVisitor;
import com.example.try_gameengine.framework.Config;


/**
 * MovementActionItemAlpha is a movement action that control alpha value.
 * @author irons
 *
 */
public class MovementActionItemAlphaUpdateTime extends MovementActionItemUpdateTime{ 
	private boolean isEnableSetSpriteAction = true;
	private int originalAlpha;
	private int alpha;
	private int offsetAlphaByOnceTrigger;
	
	private static final int NO_ORGINAL_ALPHA = -1;
	
	/**
	 * @param millisTotal
	 * @param alpha
	 */
	public MovementActionItemAlphaUpdateTime(long millisTotal, int alpha){
		this((long) (millisTotal/(1000.0f/Config.fps)), 1, NO_ORGINAL_ALPHA, alpha, "MovementActionItemAlpha");
	}
	
	/**
	 * @param millisTotal
	 * @param originalAlpha
	 * @param alpha
	 */
	public MovementActionItemAlphaUpdateTime(long millisTotal, int originalAlpha, int alpha){
		this((long) (millisTotal/(1000.0f/Config.fps)), 1, originalAlpha, alpha, "MovementActionItemAlpha");
	}
	
	/**
	 * @param triggerTotal
	 * @param triggerInterval
	 * @param alpha
	 */
	public MovementActionItemAlphaUpdateTime(long triggerTotal, long triggerInterval, int alpha){
		this(triggerTotal, triggerTotal, NO_ORGINAL_ALPHA, alpha, "MovementActionItemAlpha");
	}
	
	/**
	 * @param triggerTotal
	 * @param triggerInterval
	 * @param originalAlpha
	 * @param alpha
	 */
	public MovementActionItemAlphaUpdateTime(long triggerTotal, long triggerInterval, int originalAlpha, int alpha){
		this(triggerTotal, triggerInterval, originalAlpha, alpha, "MovementActionItemAlpha");
	}
	
	/**
	 * @param millisTotal
	 * @param millisDelay
	 * @param originalAlpha
	 * @param alpha
	 * @param description
	 */
	public MovementActionItemAlphaUpdateTime(long millisTotal, long millisDelay, int originalAlpha, int alpha, String description){
		super(new MovementActionInfo(millisTotal, millisDelay, 0, 0));
		
		this.description = description + ",";
		this.originalAlpha = originalAlpha;
		this.alpha = alpha;
	}
	
//	@Override
//	public void setTimer() {
//		// TODO Auto-generated method stub
//
//	}
//	
//	@Override
//	public void start() {
//		// TODO Auto-generated method stub	
//		resumeFrameIndex = 0;
//		resumeFrameCount = 0;
//		numberOfPauseFrames = 0;
//		pauseFrameCounter = 0;
//		isStop = false;
//		isCycleFinish = false;
//		if(originalAlpha==NO_ORGINAL_ALPHA)
//			originalAlpha = info.getSprite().getAlpha();
//		else
//			info.getSprite().setAlpha(originalAlpha);
//		
//		int offsetAlpha= alpha - originalAlpha;
//		offsetAlphaByOnceTrigger = (int) (offsetAlpha/(info.getTotal()/info.getDelay()));
//		
//		if(!isEnableSetSpriteAction)
//			isEnableSetSpriteAction = isRepeatSpriteActionIfMovementActionRepeat;
//		if(info.getSprite()!=null && isEnableSetSpriteAction)
//			info.getSprite().setAction(info.getSpriteActionName());
//		
//		triggerEnable = true;
//		isEnableSetSpriteAction = isRepeatSpriteActionIfMovementActionRepeat;
//	}
//
//	@Override
//	public void trigger(){
//		if(triggerEnable && pauseFrameCounter==numberOfPauseFrames){
//			
//			numberOfPauseFrames = 0;
//			pauseFrameCounter = 0;
//			myTrigger.trigger();
//		}else if(triggerEnable){
//			pauseFrameCounter++;
//		}else{
//			numberOfPauseFrames = 0;
//			pauseFrameCounter = 0;
//		}
//	}
//	
//	FrameTrigger myTrigger = new FrameTrigger() {
//		
//		@Override
//		public void trigger() {
//			// TODO Auto-generated method stub
//			frameTriggerFPSStart();
//			
//		}
//	};
//	public FrameTrigger setNextFrameTrigger(FrameTrigger nextframeTrigger){
//		
//		this.nextframeTrigger = nextframeTrigger;
//		
//		return myTrigger;
//	}
//	
//	public void setActionListener(IActionListener actionListener){
//		this.actionListener = actionListener;
//	}
//
//	private void frameTriggerFPSStart(){
//		if (!isStop) {
//			synchronized (MovementActionItemAlphaUpdateTime.this) {
//			if(isCycleFinish)
//				isCycleFinish = false;
//			
//			resumeFrameCount++;
//			
//			if(resumeFrameCount==numberOfFramesAfterLastTrigger+info.getDelay()){
////				timerOnTickListener.onTick(dx, dy);		
//				info.getSprite().setAlpha(info.getSprite().getAlpha()+offsetAlphaByOnceTrigger);
//				numberOfFramesAfterLastTrigger += info.getDelay();
//				
//			// add by 150228. if the delay change by main app, the function: else if(resumeFrameCount==lastTriggerFrameNum+info.getDelay() maybe make problem.
//			}else if(resumeFrameCount>numberOfFramesAfterLastTrigger+info.getDelay()){ 
////				resumeFrameCount--;
////				lastTriggerFrameNum++;
//				numberOfFramesAfterLastTrigger = resumeFrameCount+1-info.getDelay();
//			}
//			
//			if(resumeFrameCount>=info.getDelay()){	
//				if(resumeFrameCount==info.getTotal())
//					isCycleFinish = true;
//			}
//			
//			if(isCycleFinish){
//				resumeFrameCount = 0;
//				numberOfFramesAfterLastTrigger = 0;
//			}
//			
//			if(!isLoop && isCycleFinish){
//				isStop = true;
//				doReset();
//				triggerEnable = false;
//			}
//			
//			if(isCycleFinish){
//				info.getSprite().setAlpha(alpha);
//				
//				if(actionListener!=null)
//					actionListener.actionCycleFinish();
//				
//				if(!isLoop){
//					if(actionListener!=null)
//						actionListener.actionFinish();
//					
//					MovementActionItemAlphaUpdateTime.this.notifyAll();
//				}
//			}
//			
//			}
//		}else{
//			synchronized (MovementActionItemAlphaUpdateTime.this) {
//				MovementActionItemAlphaUpdateTime.this.notifyAll();
//			}
//		}
//	}
//
//	@Override
//	protected MovementAction initTimer(){
//		numberOfFramesTotal = info.getTotal();
//		numberOfFramesInterval = info.getDelay();
//		
//		resumeFrameIndex = 0;
//		return this;
//	}
//	
//	private void doReset(){
//		numberOfFramesTotal = info.getTotal();
//		numberOfFramesInterval = info.getDelay();
//	}
//	
//	@Override
//	public MovementAction getAction(){
//		return this;
//	}
//	
//	public List<MovementAction> getActions(){
//		return actions;
//	}
//
//	@Override
//	public MovementActionInfo getInfo() {
//		// TODO Auto-generated method stub
//		return info;
//	}
//
//	@Override
//	public void setInfo(MovementActionInfo info) {
//		// TODO Auto-generated method stub
//		this.info = info;
//	}
//	
//	@Override
//	public List<MovementAction> getCurrentActionList() {
//		// TODO Auto-generated method stub
//		List<MovementAction> actions = new ArrayList<MovementAction>();
//		actions.add(this);
//		return actions;
//	}
//	
//	@Override
//	public List<MovementActionInfo> getCurrentInfoList() {
//		// TODO Auto-generated method stub
//		List<MovementActionInfo> infos = new ArrayList<MovementActionInfo>();
//		infos.add(this.info);
//		currentInfoList.add(this.info);
//		return infos;
//	}
//	
//	@Override
//	public List<MovementActionInfo> getMovementInfoList() {
//		List<MovementActionInfo> infos = new ArrayList<MovementActionInfo>();
//		infos.add(this.info);
//		return infos;
//	}
//	
//	@Override
//	public void cancelMove(){
//		isStop = true;
//		synchronized (MovementActionItemAlphaUpdateTime.this) {
//			MovementActionItemAlphaUpdateTime.this.notifyAll();
//		}
//	}
//	
//	@Override
//	void pause(){	
//		numberOfPauseFrames = numberOfFramesInterval;
//	}
//	
//	@Override
//	public boolean isFinish(){
//		return isStop;
//	}
	
//	public IMovementActionMemento createMovementActionMemento(){
//		movementActionMemento = new MovementActionItemAlphaMementoImpl(actions, thread, timerOnTickListener, name, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCycleFinish, isCycleFinish, isCycleFinish, isCycleFinish, name, cancelAction, millisTotal, millisDelay, info, resumeTotal, resetTotal, name, updateTime, frameIdx, isStop, isCycleFinish, triggerEnable, frameTimes, resumeFrameIndex, resumeFrameCount, numberOfPauseFrames, pauseFrameCounter, nextframeTrigger, numberOfFramesAfterLastTrigger);
//		if(this.info!=null){
//			this.info.createIMovementActionInfoMemento();
//		}
//		return movementActionMemento;
//	}
//	
//	public void restoreMovementActionMemento(IMovementActionMemento movementActionMemento){
////		MovementActionMementoImpl mementoImpl = (MovementActionMementoImpl) movementActionMemento;
//		super.restoreMovementActionMemento(this.movementActionMemento);
//		MovementActionItemAlphaMementoImpl mementoImpl = (MovementActionItemAlphaMementoImpl) this.movementActionMemento;
//		this.millisTotal = mementoImpl.millisTotal;
//		this.millisDelay = mementoImpl.millisDelay;
//		this.info = mementoImpl.info;
//		this.resumeTotal = mementoImpl.resumeTotal;
//		this.resetTotal = mementoImpl.resetTotal;
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
//	protected static class MovementActionItemAlphaMementoImpl extends MovementActionMementoImpl{
//	
//		long millisTotal;
//		long millisDelay;
//		MovementActionInfo info;
//		long resumeTotal;
//		long resetTotal;	
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
//		public MovementActionItemAlphaMementoImpl(
//				List<MovementAction> actions, Thread thread,
//				TimerOnTickListener timerOnTickListener, String description,
//				List<MovementAction> copyMovementActionList,
//				List<MovementActionInfo> currentInfoList,
//				List<MovementAction> movementItemList,
//				List<MovementAction> totalCopyMovementActionList,
//				boolean isCancelFocusAppendPart, boolean isFinish,
//				boolean isLoop, boolean isSigleThread, String name,
//				MovementAction cancelAction, long millisTotal,
//				long millisDelay, MovementActionInfo info,
//				long resumeTotal, long resetTotal, String name2,
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
//			this.info = info;
//			this.resumeTotal = resumeTotal;
//			this.resetTotal = resetTotal;
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
