package com.example.try_gameengine.application;

import com.example.try_gameengine.stage.StageManager;

import android.content.Intent;

public abstract class GameApplication extends android.app.Application{
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		startGame();
	}
	
	public abstract void startGame();
	
	public abstract void stopGame();
	
	public abstract void resumeGame();
	
	public abstract void finishGame();
	
}
