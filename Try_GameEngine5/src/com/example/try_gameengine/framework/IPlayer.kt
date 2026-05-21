package com.example.try_gameengine.framework

interface IPlayer {
    //	boolean run(Point point, Point clickPoint, List<Point> allFreePoints);
    fun setThinkingTime()
    fun getThinkingTime(): Int
    fun setCurrentThinkingTime()
    fun getCurrentThinkingTime(): Int
    fun setCurrentMove() //	IChessPoint getChessPoint();
    //	public IChessPoint getPocessableMvoeChessPoint();
    //	boolean isSuccessArrival();
    //	boolean isAutoPlayable();
    //	void doAutoPlay();
}
