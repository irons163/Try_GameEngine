package com.example.try_gameengine.action;

import java.util.ArrayList;
import java.util.List;

import com.example.try_gameengine.action.MovementActionItemTrigger.MovementActionItemUpdateTimeDataDelegate;
import com.example.try_gameengine.action.listener.IActionListener;

public class MovementActionItemUpdateTime extends MovementActionItemForMilliseconds{ 
	MovementActionItemTrigger data;
	FrameTrigger myTrigger = new FrameTrigger() {
		
		@Override
		public void trigger() {
			// TODO Auto-generated method stub
			frameTriggerFPSStart();
		}
	};
	
//	public MovementActionItemUpdateTime(long millisTotal, long millisDelay, final int dx, final int dy){
//		this(millisTotal, millisDelay, dx, dy, "MovementItem");
//	}
//	
//	public MovementActionItemUpdateTime(long millisTotal, long millisDelay, final int dx, final int dy, String description){
//		super(millisTotal, millisDelay, dx, dy, description);
//	}
	
	public MovementActionItemUpdateTime(MovementActionInfo info){
		super(info);

	}
	
	@Override
	public void setTimer() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void start() {
		
		data.setValueOfActivedCounter(0);
		data.setShouldPauseValue(0);
		data.setValueOfPausedCounter(0);
		isStop = false;
		data.setCycleFinish(false);
		
		info.ggg();
		
		if(!data.isEnableSetSpriteAction())
			data.setEnableSetSpriteAction(isRepeatSpriteActionIfMovementActionRepeat);
		if(info.getSprite()!=null && data.isEnableSetSpriteAction())
			info.getSprite().setAction(info.getSpriteActionName());
		
		triggerEnable = true;
		data.setEnableSetSpriteAction(isRepeatSpriteActionIfMovementActionRepeat);
		
		actionListener.actionStart();
	}
	
	
	
	public interface FrameTrigger{
		public void trigger();
	}
	
	
	
	@Override
	public void trigger(){
		if(triggerEnable && data.getValueOfPausedCounter()>=data.getShouldPauseValue()){
			
			data.setShouldPauseValue(0);
			data.setValueOfPausedCounter(0);
			myTrigger.trigger();
		}else if(triggerEnable){
			data.setValueOfPausedCounter(data.getValueOfPausedCounter()
					+ Time.DeltaTime);
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
			synchronized (MovementActionItemUpdateTime.this) {
				data.dodo();
			
				if (data.isCycleFinish()) {
					info.didCycleFinish();

					if (actionListener != null)
						actionListener.actionCycleFinish();
				}

				if (!isLoop && data.isCycleFinish()) {
					isStop = true;
					doReset();
					triggerEnable = false;
				}

				if (isStop) {
					if (actionListener != null)
						actionListener.actionFinish();

					MovementActionItemUpdateTime.this.notifyAll();
				}
				
				/*// need consider between above code and below code.
				if (!isLoop && data.isCycleFinish()) {
					isStop = true;
					doReset();
					triggerEnable = false;
				}

				if (data.isCycleFinish()) {
					info.didCycleFinish();

					if (actionListener != null)
						actionListener.actionCycleFinish();

					if (!isLoop) {
						if (actionListener != null)
							actionListener.actionFinish();

						MovementActionItemAlpha2.this.notifyAll();
					}
				}*/
			}
		}else{
			synchronized (MovementActionItemUpdateTime.this) {
				MovementActionItemUpdateTime.this.notifyAll();
			}
		}
	}
	
	@Override
	protected MovementAction initTimer(){ 
		super.initTimer();
		millisTotal = info.getTotal();
		millisDelay = info.getDelay();
		dx = info.getDx();
		dy = info.getDy();
		info.createUpdateByEverytimeData();

		data = info.getData();
		data.setMovementActionItemUpdateTimeDataDelegate(new MovementActionItemUpdateTimeDataDelegate() {

			@Override
			public void update() {
				// TODO Auto-generated method stub
				info.update(timerOnTickListener);
				// if (timerOnTickListener != null)
				// timerOnTickListener.onTick(dx, dy);
			}

			@Override
			public void update(float t) {
				// TODO Auto-generated method stub
				info.update(t, timerOnTickListener);

				// float newDx = (float) (dx*t);
				// float newDy = (float) (dy*t);
				// if (timerOnTickListener != null)
				// timerOnTickListener.onTick(newDx, newDy);
			}
		});

		data.setShouldActiveTotalValue(info.getTotal());
		data.setShouldActiveIntervalValue(info.getDelay());

		// millisTotal = info.getTotal();
		// millisDelay = info.getDelay();
		// dx = info.getDx();
		// dy = info.getDy();

		return this;
	}
	
	private void doReset(){
		millisTotal = info.getTotal();
		millisDelay = info.getDelay();
		dx = info.getDx();
		dy = info.getDy();
		
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
		synchronized (MovementActionItemUpdateTime.this) {
			MovementActionItemUpdateTime.this.notifyAll();
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
//		movementActionMemento = new MovementActionItemBaseReugularFPSMementoImpl(actions, thread, timerOnTickListener, name, copyMovementActionList, currentInfoList, movementItemList, totalCopyMovementActionList, isCycleFinish, isCycleFinish, isCycleFinish, isCycleFinish, name, cancelAction, millisTotal, millisDelay, dx, dy, info, resumeTotal, resetTotal, rotationController, gravityController, name, updateTime, frameIdx, isStop, isCycleFinish, triggerEnable, frameTimes, resumeMillisCount, pauseFrameNum, pauseFrameCounter, nextframeTrigger, lastMillisCount);
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
//		this.millisTotal = mementoImpl.millisTotal;
//		this.millisDelay = mementoImpl.millisDelay;
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
//		this.resumeMillisCount = mementoImpl.resumeMillisCount;
//		this.pauseFrameNum = mementoImpl.pauseFrameNum;
//		this.pauseFrameCounter = mementoImpl.pauseFrameCounter;
//		this.nextframeTrigger = mementoImpl.nextframeTrigger;
//		this.lastMillisCount = mementoImpl.lastMillisCount;
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
////		int resumeFrameCount;	
//		long pauseFrameNum;
//		int pauseFrameCounter;	
//		FrameTrigger nextframeTrigger;
////		private long lastTriggerFrameNum;
//		long resumeMillisCount = 0;
//		long lastMillisCount = 0;
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
//				long[] frameTimes, long resumeMillisCount,
//				long pauseFrameNum, int pauseFrameCounter,
//				FrameTrigger nextframeTrigger, long lastMillisCount) {
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
//			this.resumeMillisCount = resumeMillisCount;
//			this.pauseFrameNum = pauseFrameNum;
//			this.pauseFrameCounter = pauseFrameCounter;
//			this.nextframeTrigger = nextframeTrigger;
//			this.lastMillisCount = lastMillisCount;
////			this.isEnableSetSpriteAction = isEnableSetSpriteAction;
//		}
//			
//	}
}
