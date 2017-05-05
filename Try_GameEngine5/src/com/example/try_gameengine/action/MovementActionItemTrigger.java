package com.example.try_gameengine.action;

public interface MovementActionItemTrigger {

	public interface MovementActionItemUpdateTimeDataDelegate{
		public void update();
		public void update(float t);
	}
	
	public interface MovementActionItemTriggerInitDelegate{
		public void initForUpdateTime();
		public void initForFrameTrigger();
	}

	public abstract class DataDelegate implements MovementActionItemUpdateTimeDataDelegate{
		private MovementActionItemUpdateTimeDataDelegate other;
		
		public void addMovementActionItemUpdateTimeDataDelegate(MovementActionItemUpdateTimeDataDelegate dataDelegate){
			if(other!=null && other instanceof DataDelegate)
				((DataDelegate)other).addMovementActionItemUpdateTimeDataDelegate(dataDelegate);
			else if(other!=null){
				throw new RuntimeException("double setting");
			}else{
				other = dataDelegate;
			}
			
//			DataDelegate lastDataDelegate = getDataDelegate();
//			lastDataDelegate.addMovementActionItemUpdateTimeDataDelegate(dataDelegate);
		}
		
		private MovementActionItemUpdateTimeDataDelegate getDataDelegate(){
			if(other!=null && other instanceof DataDelegate)
				return ((DataDelegate)other).getDataDelegate();
			else if(other!=null){
				return other;
			}
			return this;
		}
		
		@Override
		public void update() {
			// TODO Auto-generated method stub
			if(other!=null)
				other.update();
		}

		@Override
		public void update(float t) {
			// TODO Auto-generated method stub
			if(other!=null)
				other.update(t);
		}
		
	}
	
	public abstract void initWithInitDelegate(MovementActionItemTriggerInitDelegate initDelegate);

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
	
	public abstract double getValueOfFactorByUpdate();

	public abstract MovementActionItemUpdateTimeDataDelegate getMovementActionItemUpdateTimeDataDelegate();

	public abstract void setMovementActionItemUpdateTimeDataDelegate(
			MovementActionItemUpdateTimeDataDelegate movementActionItemUpdateTimeDataDelegate);

	public abstract void dodo();
}