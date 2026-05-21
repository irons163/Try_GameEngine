package com.example.try_gameengine.framework

class PlayerFactory(chessPointManager: IChessPointManager?) : IPlayerFactory {
    private val chessPointManager: IChessPointManager?

    init {
        // TODO Auto-generated constructor stub
        this.chessPointManager = chessPointManager
    }

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
