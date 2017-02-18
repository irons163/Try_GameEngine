package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import android.graphics.YuvImage;
import android.util.Log;

import com.example.try_gameengine.action.MovementActionItemBaseReugularFPS.FrameTrigger;
import com.example.try_gameengine.action.MovementActionItemTrigger.MovementActionItemUpdateTimeDataDelegate;
import com.example.try_gameengine.action.listener.IActionListener;
import com.example.try_gameengine.action.visitor.IMovementActionVisitor;
import com.example.try_gameengine.framework.Config;


/**
 * MovementActionItemAlpha is a movement action that control alpha value.
 * @author irons
 *
 */
public class MovementActionItemAlpha2 extends MovementActionItemUpdate{ 
	MovementActionItemTrigger data;
	FrameTrigger myTrigger = new FrameTrigger() {
		
		@Override
		public void trigger() {
			// TODO Auto-generated method stub
			frameTriggerFPSStart();
		}
	};
	
	private static final int NO_ORGINAL_ALPHA = -1;
	private int originalAlpha;
	private int alpha;
	private float offsetAlphaByOnceTrigger;
	
	/**
	 * @param millisTotal
	 * @param alpha
	 */
	public MovementActionItemAlpha2(long millisTotal, int alpha){
		this(millisTotal, 1, NO_ORGINAL_ALPHA, alpha, "MovementActionItemAlpha");
	}
	
	/**
	 * @param millisTotal
	 * @param originalAlpha
	 * @param alpha
	 */
	public MovementActionItemAlpha2(long millisTotal, int originalAlpha, int alpha){
		this(millisTotal, 1, originalAlpha, alpha, "MovementActionItemAlpha");
	}
	
	/**
	 * @param triggerTotal
	 * @param triggerInterval
	 * @param alpha
	 */
	public MovementActionItemAlpha2(long triggerTotal, long triggerInterval, int alpha){
		this(triggerTotal, triggerTotal, NO_ORGINAL_ALPHA, alpha, "MovementActionItemAlpha");
	}
	
	/**
	 * @param triggerTotal
	 * @param triggerInterval
	 * @param originalAlpha
	 * @param alpha
	 */
	public MovementActionItemAlpha2(long triggerTotal, long triggerInterval, int originalAlpha, int alpha){
		this(triggerTotal, triggerInterval, originalAlpha, alpha, "MovementActionItemAlpha");
	}
	
	/**
	 * @param millisTotal
	 * @param millisDelay
	 * @param originalAlpha
	 * @param alpha
	 * @param description
	 */
	public MovementActionItemAlpha2(long millisTotal, long millisDelay, int originalAlpha, int alpha, String description){
		super(new MovementActionInfo(millisTotal, millisDelay, 0, 0));
		
		this.description = description + ",";
		this.originalAlpha = originalAlpha;
		this.alpha = alpha;
	}
	
	@Override
	public void setTimer() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub	
//		resumeFrameIndex = 0;

		data.setValueOfActivedCounter(0);
		data.setShouldPauseValue(0);
		data.setValueOfPausedCounter(0);
		isStop = false;
		data.setCycleFinish(false);
		if(originalAlpha==NO_ORGINAL_ALPHA)
			originalAlpha = info.getSprite().getAlpha();
		else
			info.getSprite().setAlpha(originalAlpha);
		
		int offsetAlpha= alpha - originalAlpha;
		offsetAlphaByOnceTrigger = (int) (offsetAlpha/(info.getTotal()/info.getDelay()));
		
		if(!data.isEnableSetSpriteAction())
			data.setEnableSetSpriteAction(isRepeatSpriteActionIfMovementActionRepeat);
		if(info.getSprite()!=null && data.isEnableSetSpriteAction())
			info.getSprite().setAction(info.getSpriteActionName());
		
		triggerEnable = true;
		data.setEnableSetSpriteAction(isRepeatSpriteActionIfMovementActionRepeat);
	}

	@Override
	public void trigger(){
		if(triggerEnable && data.getValueOfPausedCounter()==data.getShouldPauseValue()){
			
			data.setShouldPauseValue(0);
			data.setValueOfPausedCounter(0);
			myTrigger.trigger();
		}else if(triggerEnable){
			data.setValueOfPausedCounter(data.getValueOfPausedCounter() + 1);
		}else{
			data.setShouldPauseValue(0);
			data.setValueOfPausedCounter(0);
		}
	}
	
	public void setActionListener(IActionListener actionListener){
		this.actionListener = actionListener;
	}

	private void frameTriggerFPSStart(){
		if (!isStop) {
			synchronized (MovementActionItemAlpha2.this) {
			data.dodo();
			
			if(!isLoop && data.isCycleFinish()){
				isStop = true;
				doReset();
				triggerEnable = false;
			}
			
			if(data.isCycleFinish()){
				info.getSprite().setAlpha(alpha);
				
				if(actionListener!=null)
					actionListener.actionCycleFinish();
				
				if(!isLoop){
					if(actionListener!=null)
						actionListener.actionFinish();
					
					MovementActionItemAlpha2.this.notifyAll();
				}
			}
			
			}
		}else{
			synchronized (MovementActionItemAlpha2.this) {
				MovementActionItemAlpha2.this.notifyAll();
			}
		}
	}

	@Override
	protected MovementAction initTimer(){ super.initTimer();
		data = info.getData();
		data.setMovementActionItemUpdateTimeDataDelegate(new MovementActionItemUpdateTimeDataDelegate() {
			
			@Override
			public void update() {
				// TODO Auto-generated method stub
//				timerOnTickListener.onTick(dx, dy);		
				info.getSprite().setAlpha(originalAlpha + (int)offsetAlphaByOnceTrigger);
			}

			@Override
			public void update(float t) {
				Log.e("interval", t+"");
				Log.e("totle", data.getShouldActiveTotalValue()+"");
//				double percent = ((double)t)/data.getShouldActiveTotalValue();
//				int offsetAlpha= alpha - originalAlpha;
//				offsetAlphaByOnceTrigger += (float) (offsetAlpha*percent);
				
				int offsetAlpha= alpha - originalAlpha;
				offsetAlphaByOnceTrigger = (float) (offsetAlpha*t);
				Log.e("offsetAlpha", offsetAlpha+" "+ t);
				info.getSprite().setAlpha(originalAlpha + (int)offsetAlphaByOnceTrigger);
			}
		});
		
		data.setShouldActiveTotalValue(info.getTotal());
		data.setShouldActiveIntervalValue(info.getDelay());
		
//		resumeFrameIndex = 0;
		return this;
	}
	
	private void doReset(){
		data.setShouldActiveTotalValue(info.getTotal());
		data.setShouldActiveIntervalValue(info.getDelay());
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
		synchronized (MovementActionItemAlpha2.this) {
			MovementActionItemAlpha2.this.notifyAll();
		}
	}
	
	@Override
	void pause(){	
		data.setShouldPauseValue(data.getShouldActiveIntervalValue());
	}
	
	@Override
	public boolean isFinish(){
		return isStop;
	}
	
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
