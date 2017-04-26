package com.example.try_gameengine.action;

public class MovementActionItemAlpha2Data implements MovementActionItemTrigger{
	private long numberOfFramesTotal;
	private long numberOfFramesInterval;
	private long numberOfPauseFrames;
	private long pauseFrameCounter;
	private long resumeFrameCount;
	private boolean isCycleFinish;
	private long numberOfFramesAfterLastTrigger;
	private boolean isEnableSetSpriteAction;
	private MovementActionItemUpdateTimeDataDelegate movementActionItemUpdateTimeDataDelegate;

	public void dodo(){
		if(this.isCycleFinish())
			this.setCycleFinish(false);
		
		this.setValueOfActivedCounter(this.getValueOfActivedCounter() + 1);
		
		if(this.getValueOfActivedCounter()==this.getActivedValueForLatestUpdated()+getShouldActiveIntervalValue()){
			movementActionItemUpdateTimeDataDelegate.update();
			
			this.setActivedValueForLatestUpdated(this
					.getActivedValueForLatestUpdated() + getShouldActiveIntervalValue());
			
		// add by 150228. if the delay change by main app, the function: else if(resumeFrameCount==lastTriggerFrameNum+info.getDelay() maybe make problem.
		}else if(this.getValueOfActivedCounter()>this.getActivedValueForLatestUpdated()+getShouldActiveIntervalValue()){ 
//			resumeFrameCount--;
//			lastTriggerFrameNum++;
			this.setActivedValueForLatestUpdated(this.getValueOfActivedCounter()+1-getShouldActiveIntervalValue());
		}
		
		if(this.getValueOfActivedCounter()>=getShouldActiveIntervalValue()){	
			if(this.getValueOfActivedCounter()==getShouldActiveTotalValue())
				this.setCycleFinish(true);
		}
		
		if(this.isCycleFinish()){
			this.setValueOfActivedCounter(0);
			this.setActivedValueForLatestUpdated(0);
		}
	}

	@Override
	public long getShouldActiveTotalValue() {
		// TODO Auto-generated method stub
		return numberOfFramesTotal;
	}

	@Override
	public long getShouldActiveIntervalValue() {
		// TODO Auto-generated method stub
		return numberOfFramesInterval;
	}

	@Override
	public void setShouldActiveTotalValue(long numberOfFramesTotal) {
		this.numberOfFramesTotal = numberOfFramesTotal;
	}

	@Override
	public void setShouldActiveIntervalValue(long numberOfFramesInterval) {
		// TODO Auto-generated method stub
		this.numberOfFramesInterval = numberOfFramesInterval;
	}

	@Override
	public long getShouldPauseValue() {
		// TODO Auto-generated method stub
		return numberOfPauseFrames;
	}

	@Override
	public void setShouldPauseValue(long numberOfPauseFrames) {
		// TODO Auto-generated method stub
		this.numberOfPauseFrames = numberOfPauseFrames;
	}

	@Override
	public long getValueOfPausedCounter() {
		// TODO Auto-generated method stub
		return pauseFrameCounter;
	}

	@Override
	public void setValueOfPausedCounter(long pauseFrameCounter) {
		// TODO Auto-generated method stub
		this.pauseFrameCounter = pauseFrameCounter;
	}

	@Override
	public long getValueOfActivedCounter() {
		// TODO Auto-generated method stub
		return resumeFrameCount;
	}

	@Override
	public void setValueOfActivedCounter(long resumeFrameCount) {
		// TODO Auto-generated method stub
		this.resumeFrameCount = resumeFrameCount;
	}

	@Override
	public long getActivedValueForLatestUpdated() {
		// TODO Auto-generated method stub
		return numberOfFramesAfterLastTrigger;
	}

	@Override
	public void setActivedValueForLatestUpdated(long numberOfFramesAfterLastTrigger) {
		// TODO Auto-generated method stub
		this.numberOfFramesAfterLastTrigger = numberOfFramesAfterLastTrigger;
	}

	@Override
	public MovementActionItemUpdateTimeDataDelegate getMovementActionItemUpdateTimeDataDelegate() {
		return movementActionItemUpdateTimeDataDelegate;
	}
	
	@Override
	public void setMovementActionItemUpdateTimeDataDelegate(
			MovementActionItemUpdateTimeDataDelegate movementActionItemUpdateTimeDataDelegate) {
		this.movementActionItemUpdateTimeDataDelegate = movementActionItemUpdateTimeDataDelegate;
	}

	@Override
	public boolean isCycleFinish() {
		// TODO Auto-generated method stub
		return isCycleFinish;
	}

	@Override
	public void setCycleFinish(boolean isCycleFinish) {
		// TODO Auto-generated method stub
		this.isCycleFinish = isCycleFinish;
	}

	@Override
	public boolean isEnableSetSpriteAction() {
		// TODO Auto-generated method stub
		return isEnableSetSpriteAction;
	}

	@Override
	public void setEnableSetSpriteAction(boolean isEnableSetSpriteAction) {
		// TODO Auto-generated method stub
		this.isEnableSetSpriteAction = isEnableSetSpriteAction;
	}

	@Override
	public double getValueOfFactorByUpdate() {
		// TODO Auto-generated method stub
		return (double)getShouldActiveIntervalValue()/getShouldActiveTotalValue();
	}
}