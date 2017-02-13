package com.example.try_gameengine.action;

public interface MovementActionItemTrigger {

	public interface MovementActionItemUpdateTimeDataDelegate{
		public void update();
	}

	public abstract boolean isCycleFinish();

	public abstract void setCycleFinish(boolean isCycleFinish);

	public abstract long getShouldActiveTotalValue();

	public abstract long getShouldActiveIntervalValue();

	public abstract void setShouldActiveTotalValue(long millisTotal);

	public abstract void setShouldActiveIntervalValue(long millisDelay);

	public abstract long getShouldPauseValue();

	public abstract void setShouldPauseValue(long numberOfPauseCounter);

	public abstract long getValueOfPausedCounter();

	public abstract void setValueOfPausedCounter(long pauseMillisecondsCounter);

	public abstract long getValueOfActivedCounter();

	public abstract void setValueOfActivedCounter(long resumeMillisCount);

	public abstract long getActivedValueForLatestUpdated();

	public abstract void setActivedValueForLatestUpdated(long lastMillisCount);

	public abstract boolean isEnableSetSpriteAction();

	public abstract void setEnableSetSpriteAction(
			boolean isEnableSetSpriteAction);

	public abstract MovementActionItemUpdateTimeDataDelegate getMovementActionItemUpdateTimeDataDelegate();

	public abstract void setMovementActionItemUpdateTimeDataDelegate(
			MovementActionItemUpdateTimeDataDelegate movementActionItemUpdateTimeDataDelegate);

	public abstract void dodo();

}