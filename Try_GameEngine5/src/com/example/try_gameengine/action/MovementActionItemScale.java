package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.MovementAction.MovementActionMementoImpl;
import com.example.try_gameengine.action.MovementAction.TimerOnTickListener;
import com.example.try_gameengine.action.MovementActionItemAlpha.FrameTrigger;
import com.example.try_gameengine.action.MovementActionItemAlpha.MovementActionItemBaseReugularFPSMementoImpl;
import com.example.try_gameengine.action.listener.IActionListener;
import com.example.try_gameengine.action.visitor.IMovementActionVisitor;
import com.example.try_gameengine.framework.Config;
import com.example.try_gameengine.framework.Minimax;

public class MovementActionItemScale extends MovementAction{
	long millisTotal;
	long millisDelay;
	MovementActionInfo info;
	long resumeTotal;
	long resetTotal;	
	public String name;	
	private long updateTime;	
	public int frameIdx;	
	public boolean isStop = false;
	public boolean isCycleFinish = false;	
	boolean triggerEnable = false;	
	long[] frameTimes;
	int resumeFrameIndex;
	int resumeFrameCount;	
	long pauseFrameNum;
	int pauseFrameCounter;	
	FrameTrigger nextframeTrigger;
	private long lastTriggerFrameNum;
	private boolean isEnableSetSpriteAction = true;
//	private int originalAlpha;
//	private int alpha;
//	private int offsetAlphaByOnceTrigger;
	private float scaleX, scaleY;
	private float offsetScaleXByOnceTrigger, offsetScaleYByOnceTrigger;
	
	public static final float NO_SCALE = Float.MIN_VALUE;
	
	public MovementActionItemScale(long millisTotal, float scaleX, float scaleY){
		this((long) (millisTotal/(1000.0f/Config.fps)), 1, scaleX, scaleY, "MovementActionItemAlpha");
	}
	
//	public MovementActionItemScale(long millisTotal, int originalAlpha, int alpha){
//		this((long) (millisTotal/(1000.0f/Config.fps)), 1, originalAlpha, alpha, "MovementActionItemAlpha");
//	}
	
//	public MovementActionItemScale(long triggerTotal, long triggerInterval, int alpha){
//		this(triggerTotal, triggerTotal, NO_ORGINAL_ALPHA, alpha, "MovementActionItemAlpha");
//	}
	
	public MovementActionItemScale(long triggerTotal, long triggerInterval, float scaleX, float scaleY){
		this(triggerTotal, triggerInterval, scaleX, scaleY, "MovementActionItemAlpha");
	}
	
	public MovementActionItemScale(long millisTotal, long millisDelay, float scaleX, float scaleY, String description){
		this.scaleX = scaleX;
		this.scaleY = scaleY;
//		super(millisTotal, millisDelay, dx, dy, description);
		this.millisTotal = millisTotal;
		this.millisDelay = millisDelay;
		this.description = description + ",";

		movementItemList.add(this);
		info = new MovementActionInfo(millisTotal, millisDelay, 0, 0);
	}
	
	@Override
	public void setTimer() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub	
		resumeFrameIndex = 0;
		resumeFrameCount = 0;
		pauseFrameNum = 0;
		pauseFrameCounter = 0;
		isStop = false;
		isCycleFinish = false;
//		if(originalAlpha==NO_ORGINAL_ALPHA)
//			originalAlpha = info.getSprite().getAlpha();
//		else
//			info.getSprite().setAlpha(originalAlpha);
		if(this.scaleX!=NO_SCALE){
			float originalScaleX = info.getSprite().getXscale();
			float offsetScaleX = scaleX - originalScaleX;
			offsetScaleXByOnceTrigger = offsetScaleX/(info.getTotal()/info.getDelay());
		}
		if(this.scaleY!=NO_SCALE){
			float originalScaleY = info.getSprite().getYscale();
			float offsetScaleY = scaleY - originalScaleY;
			offsetScaleYByOnceTrigger = offsetScaleY/(info.getTotal()/info.getDelay());
		}	
		
		if(!isEnableSetSpriteAction)
			isEnableSetSpriteAction = isRepeatSpriteActionIfMovementActionRepeat;
		if(info.getSprite()!=null && isEnableSetSpriteAction)
			info.getSprite().setAction(info.getSpriteActionName());
		
		triggerEnable = true;
		isEnableSetSpriteAction = isRepeatSpriteActionIfMovementActionRepeat;
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
			synchronized (MovementActionItemScale.this) {
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

			}else if(resumeFrameCount==lastTriggerFrameNum+info.getDelay()){
//				timerOnTickListener.onTick(dx, dy);		
				if(offsetScaleXByOnceTrigger!=0)
					info.getSprite().setXscale(info.getSprite().getXscale()+offsetScaleXByOnceTrigger);
				if(offsetScaleYByOnceTrigger!=0)
					info.getSprite().setYscale(info.getSprite().getYscale()+offsetScaleYByOnceTrigger);
				lastTriggerFrameNum += info.getDelay();
				
			// add by 150228. if the delay change by main app, the function: else if(resumeFrameCount==lastTriggerFrameNum+info.getDelay() maybe make problem.
			}else if(resumeFrameCount>lastTriggerFrameNum+info.getDelay()){ 
//				resumeFrameCount--;
//				lastTriggerFrameNum++;
				lastTriggerFrameNum = resumeFrameCount+1-info.getDelay();
			}
			
			if(isCycleFinish){
				if(scaleX!=NO_SCALE)
					info.getSprite().setXscale(scaleX);
				if(scaleY!=NO_SCALE)
					info.getSprite().setYscale(scaleY);
				
				if(actionListener!=null)
					actionListener.actionCycleFinish();
				isCycleFinish = false;
				
				if(!isLoop){
					if(actionListener!=null)
						actionListener.actionFinish();
					
					MovementActionItemScale.this.notifyAll();
				}
			}
			
			}
		}else{
			synchronized (MovementActionItemScale.this) {
				MovementActionItemScale.this.notifyAll();
			}
		}
	}

	@Override
	protected MovementAction initTimer(){
		millisTotal = info.getTotal();
		millisDelay = info.getDelay();
		
		resumeFrameIndex = 0;
		return this;
	}
	
	private void doReset(){
		millisTotal = info.getTotal();
		millisDelay = info.getDelay();
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
		synchronized (MovementActionItemScale.this) {
			MovementActionItemScale.this.notifyAll();
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
		movementActionMemento = new MovementActionItemBaseReugularFPSMementoImpl(actions, thread, timerOnTickListener, name, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCycleFinish, isCycleFinish, isCycleFinish, isCycleFinish, name, cancelAction, allMovementActoinList, millisTotal, millisDelay, info, resumeTotal, resetTotal, name, updateTime, frameIdx, isStop, isCycleFinish, triggerEnable, frameTimes, resumeFrameIndex, resumeFrameCount, pauseFrameNum, pauseFrameCounter, nextframeTrigger, lastTriggerFrameNum);
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
		this.info = mementoImpl.info;
		this.resumeTotal = mementoImpl.resumeTotal;
		this.resetTotal = mementoImpl.resetTotal;
		this.name = mementoImpl.name;
		this.updateTime = mementoImpl.updateTime;
		this.frameIdx = mementoImpl.frameIdx;
		this.isStop = mementoImpl.isStop;
		this.isCycleFinish = mementoImpl.isCycleFinish;
		this.triggerEnable = mementoImpl.triggerEnable;
		this.frameTimes = mementoImpl.frameTimes;
		this.resumeFrameIndex = mementoImpl.resumeFrameIndex;
		this.resumeFrameCount = mementoImpl.resumeFrameCount;
		this.pauseFrameNum = mementoImpl.pauseFrameNum;
		this.pauseFrameCounter = mementoImpl.pauseFrameCounter;
		this.nextframeTrigger = mementoImpl.nextframeTrigger;
		this.lastTriggerFrameNum = mementoImpl.lastTriggerFrameNum;
//		this.isEnableSetSpriteAction = mementoImpl.isEnableSetSpriteAction;
		
		if(this.info!=null){
			this.info.restoreMovementActionMemento(null);
		}
		doReset();

	}
	
	protected static class MovementActionItemBaseReugularFPSMementoImpl extends MovementActionMementoImpl{
	
		long millisTotal;
		long millisDelay;
		MovementActionInfo info;
		long resumeTotal;
		long resetTotal;	
		public String name;	
		private long updateTime;	
		public int frameIdx;	
		public boolean isStop = false;
		public boolean isCycleFinish = false;	
		boolean triggerEnable = false;	
		long[] frameTimes;
		int resumeFrameIndex;
		int resumeFrameCount;	
		long pauseFrameNum;
		int pauseFrameCounter;	
		FrameTrigger nextframeTrigger;
		private long lastTriggerFrameNum;
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
				MovementAction cancelAction,
				List<MovementAction> allMovementActoinList, long millisTotal,
				long millisDelay, MovementActionInfo info,
				long resumeTotal, long resetTotal, String name2,
				long updateTime, int frameIdx, boolean isStop,
				boolean isCycleFinish, boolean triggerEnable,
				long[] frameTimes, int resumeFrameIndex, int resumeFrameCount,
				long pauseFrameNum, int pauseFrameCounter,
				FrameTrigger nextframeTrigger, long lastTriggerFrameNum) {
			super(actions, thread, timerOnTickListener, description,
					copyMovementActionList, currentInfoList, movementItemList,
					totalCopyMovementActionList, isCancelFocusAppendPart,
					isFinish, isLoop, isSigleThread, name, cancelAction,
					allMovementActoinList);
			this.millisTotal = millisTotal;
			this.millisDelay = millisDelay;
			this.info = info;
			this.resumeTotal = resumeTotal;
			this.resetTotal = resetTotal;
			name = name2;
			this.updateTime = updateTime;
			this.frameIdx = frameIdx;
			this.isStop = isStop;
			this.isCycleFinish = isCycleFinish;
			this.triggerEnable = triggerEnable;
			this.frameTimes = frameTimes;
			this.resumeFrameIndex = resumeFrameIndex;
			this.resumeFrameCount = resumeFrameCount;
			this.pauseFrameNum = pauseFrameNum;
			this.pauseFrameCounter = pauseFrameCounter;
			this.nextframeTrigger = nextframeTrigger;
			this.lastTriggerFrameNum = lastTriggerFrameNum;
//			this.isEnableSetSpriteAction = isEnableSetSpriteAction;
		}
			
	}
	
	@Override
	public void accept(IMovementActionVisitor movementActionVisitor){
		movementActionVisitor.visitLeaf(this);
	}
}