package com.example.try_gameengine.action;

public class MovementActionItemUpdateTimeData implements MovementActionItemTrigger {
	private boolean isCycleFinish;
	private long millisTotal;
	private long millisDelay;
	private long pauseMilliseconds;
	private long pauseMillisecondsCounter;
	private long resumeMillisCount;
	private long lastMillisCount;
	private boolean isEnableSetSpriteAction;
	private MovementActionItemUpdateTimeDataDelegate movementActionItemUpdateTimeDataDelegate;
	private UpdateType updateType = UpdateType.UpdateEverytime;
	
	enum UpdateType{
		UpdateEverytime,
		UpdateByInterval
	}
	
//	public MovementActionItemUpdateTimeData(
//			long resumeMillisCount, long lastMillisCount,
//			boolean isEnableSetSpriteAction, FrameTrigger myTrigger) {
//		this.resumeMillisCount = resumeMillisCount;
//		this.lastMillisCount = lastMillisCount;
//		this.isEnableSetSpriteAction = isEnableSetSpriteAction;
//		this.myTrigger = myTrigger;
//	}


	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#isCycleFinish()
	 */
	@Override
	public boolean isCycleFinish() {
		return isCycleFinish;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setCycleFinish(boolean)
	 */
	@Override
	public void setCycleFinish(boolean isCycleFinish) {
		this.isCycleFinish = isCycleFinish;
	}
	
	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#getShouldActiveTotalValue()
	 */
	@Override
	public long getShouldActiveTotalValue() {
		return millisTotal;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#getShouldActiveIntervalValue()
	 */
	@Override
	public long getShouldActiveIntervalValue() {
		return millisDelay;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setShouldActiveTotalValue(long)
	 */
	@Override
	public void setShouldActiveTotalValue(long millisTotal) {
		this.millisTotal = millisTotal;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setShouldActiveIntervalValue(long)
	 */
	@Override
	public void setShouldActiveIntervalValue(long millisDelay) {
		this.millisDelay = millisDelay;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#getShouldPauseValue()
	 */
	@Override
	public long getShouldPauseValue() {
		return pauseMilliseconds;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setShouldPauseValue(long)
	 */
	@Override
	public void setShouldPauseValue(long numberOfPauseCounter) {
		this.pauseMilliseconds = numberOfPauseCounter;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#getValueOfPausedCounter()
	 */
	@Override
	public long getValueOfPausedCounter() {
		return pauseMillisecondsCounter;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setValueOfPausedCounter(long)
	 */
	@Override
	public void setValueOfPausedCounter(long pauseMillisecondsCounter) {
		this.pauseMillisecondsCounter = pauseMillisecondsCounter;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#getValueOfActivedCounter()
	 */
	@Override
	public long getValueOfActivedCounter() {
		return resumeMillisCount;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setValueOfActivedCounter(long)
	 */
	@Override
	public void setValueOfActivedCounter(long resumeMillisCount) {
		this.resumeMillisCount = resumeMillisCount;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#getActivedValueForLatestUpdated()
	 */
	@Override
	public long getActivedValueForLatestUpdated() {
		return lastMillisCount;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setActivedValueForLatestUpdated(long)
	 */
	@Override
	public void setActivedValueForLatestUpdated(long lastMillisCount) {
		this.lastMillisCount = lastMillisCount;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#isEnableSetSpriteAction()
	 */
	@Override
	public boolean isEnableSetSpriteAction() {
		return isEnableSetSpriteAction;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setEnableSetSpriteAction(boolean)
	 */
	@Override
	public void setEnableSetSpriteAction(boolean isEnableSetSpriteAction) {
		this.isEnableSetSpriteAction = isEnableSetSpriteAction;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#getMovementActionItemUpdateTimeDataDelegate()
	 */
	@Override
	public MovementActionItemUpdateTimeDataDelegate getMovementActionItemUpdateTimeDataDelegate() {
		return movementActionItemUpdateTimeDataDelegate;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#setMovementActionItemUpdateTimeDataDelegate(com.example.try_gameengine.action.MovementActionItemUpdateTimeData.MovementActionItemUpdateTimeDataDelegate)
	 */
	@Override
	public void setMovementActionItemUpdateTimeDataDelegate(
			MovementActionItemUpdateTimeDataDelegate movementActionItemUpdateTimeDataDelegate) {
		this.movementActionItemUpdateTimeDataDelegate = movementActionItemUpdateTimeDataDelegate;
	}
	
	public UpdateType getUpdateType() {
		return updateType;
	}

	public void setUpdateType(UpdateType updateType) {
		this.updateType = updateType;
	}

	/* (non-Javadoc)
	 * @see com.example.try_gameengine.action.MovementActionItemTrigger#dodo()
	 */
	@Override
	public void dodo(){
		if (this.isCycleFinish())
			this.setCycleFinish(false);

		this.setValueOfActivedCounter(this.getValueOfActivedCounter() + Time.DeltaTime);

		if(updateType == UpdateType.UpdateEverytime){
			movementActionItemUpdateTimeDataDelegate.update(this.getValueOfActivedCounter() - this.getActivedValueForLatestUpdated());
			this.setActivedValueForLatestUpdated(this.getValueOfActivedCounter());
		}else {
			do {
				if (this.getValueOfActivedCounter() >= this.getActivedValueForLatestUpdated()
						+ this.getShouldActiveIntervalValue()) {
					movementActionItemUpdateTimeDataDelegate.update();
					
					this.setActivedValueForLatestUpdated(this.getActivedValueForLatestUpdated()
							+ this.getShouldActiveIntervalValue());
				}
			} while (this.getValueOfActivedCounter() >= this.getActivedValueForLatestUpdated()
					+ this.getShouldActiveIntervalValue());
		}

		if (this.getValueOfActivedCounter() >= this.getShouldActiveIntervalValue()) {
			if (this.getValueOfActivedCounter() >= this.getShouldActiveTotalValue())
				this.setCycleFinish(true);
		}

		if (this.isCycleFinish()) {
			// resumeMillisCount = 0; // during each cycle has a little delay.
			this.setValueOfActivedCounter(this.getValueOfActivedCounter()
					- this.getShouldActiveTotalValue()); // during each cycle has no delay.
			this.setActivedValueForLatestUpdated(0);
		}
	}
}