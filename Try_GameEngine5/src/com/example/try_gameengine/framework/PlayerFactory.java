package com.example.try_gameengine.framework;

public class PlayerFactory implements IPlayerFactory{
	private IChessPointManager chessPointManager;
	
	public PlayerFactory(IChessPointManager chessPointManager) {
		// TODO Auto-generated constructor stub
		this.chessPointManager = chessPointManager;
	}
	
//	@Override
//	public IPlayer createHumanPlayerWithRed() {
//		// TODO Auto-generated method stub
//		return new HumanPlayer(chessPointManager.createChessPointRed(), chessPointManager.createChessPointWhite());
//	}

	@Override
	public IPlayer createAIPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPlayer createAIPlayerRamdon() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public IPlayer createHumanPlayerWithYellow() {
//		// TODO Auto-generated method stub
//		return new HumanPlayer(chessPointManager.createChessPointYellow(), chessPointManager.createChessPointWhite());
//	}

}
