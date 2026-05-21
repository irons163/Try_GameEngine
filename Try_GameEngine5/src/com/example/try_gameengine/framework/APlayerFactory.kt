package com.example.try_gameengine.framework

class APlayerFactory : IPlayerFactory {
    private val chessPointManager: IChessPointManager? = null

    //	public APlayerFactory(IChessPointManager chessPointManager) {
    //		// TODO Auto-generated constructor stub
    //		this.chessPointManager = chessPointManager;
    //	}
    //	@Override
    //	public IPlayer createHumanPlayerWithRed() {
    //		// TODO Auto-generated method stub
    //		return new HumanPlayer(chessPointManager.createChessPointRed(), chessPointManager.createChessPointWhite());
    //	}
    override fun createAIPlayer(): IPlayer? {
        // TODO Auto-generated method stub
        return null
    }

    override fun createAIPlayerRamdon(): IPlayer? {
        // TODO Auto-generated method stub
        return null
    } //	@Override
    //	public IPlayer createHumanPlayerWithYellow() {
    //		// TODO Auto-generated method stub
    //		return new HumanPlayer(chessPointManager.createChessPointYellow(), chessPointManager.createChessPointWhite());
    //	}
}
