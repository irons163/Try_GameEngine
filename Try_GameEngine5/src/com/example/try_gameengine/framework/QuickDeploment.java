package com.example.try_gameengine.framework;

public abstract class QuickDeploment {
	private IGameModel gameModel;
	private IGameController gameController;
	
	public QuickDeploment(IGameModel gameModel, IGameController gameController) {
		// TODO Auto-generated constructor stub
		this.gameModel = gameModel;
		this.gameController = gameController;
	}
	
	public void gameController(){
		
	}
	
	public void gameSetting(){
		
	}
	
	abstract void tunnel();
}
