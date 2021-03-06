package com.example.try_gameengine.utils;

public class GameTimeUtil {
	private long initTimeMS;
	private long intervalMS;
	private long nextExecuteTimeMS;
	private boolean enable = true;
	
	public GameTimeUtil(long intervalMS){
		this.initTimeMS = System.currentTimeMillis();
		this.intervalMS = intervalMS;
		this.nextExecuteTimeMS = initTimeMS + intervalMS;
	}
	
	public GameTimeUtil(long initTimeMS, long intervalMS){
		this.initTimeMS = initTimeMS;
		this.intervalMS = intervalMS;
		this.nextExecuteTimeMS = initTimeMS + intervalMS;
	}
	
	public boolean isArriveExecuteTime(){
		if(!enable)
			return false;
		boolean isArriveTime = false;
		if(System.currentTimeMillis() >= nextExecuteTimeMS){
			isArriveTime = true;
			nextExecuteTimeMS += intervalMS;
		}
		return isArriveTime;
	}
	
	public boolean isArriveExecuteTime(long currentTimeMS){
		if(!enable)
			return false;
		boolean isArriveTime = false;
		if(currentTimeMS >= nextExecuteTimeMS){
			isArriveTime = true;
			nextExecuteTimeMS += intervalMS;
		}
		return isArriveTime;
	}
	
	public boolean isArriveExecuteTimeIfOneDelayThenAllDelay(){
		return isArriveExecuteTimeIfOneDelayThenAllDelay(System.currentTimeMillis());
	}
	
	public boolean isArriveExecuteTimeIfOneDelayThenAllDelay(long currentTimeMS){
		if(!enable)
			return false;
		boolean isArriveTime = false;
		if(currentTimeMS >= nextExecuteTimeMS){
			isArriveTime = true;
			nextExecuteTimeMS = currentTimeMS + intervalMS;
		}
		return isArriveTime;
	}
	
	public void enable(boolean enable){
		this.enable = enable;
	}
}
