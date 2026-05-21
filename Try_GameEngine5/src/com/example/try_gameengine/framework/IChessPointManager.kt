package com.example.try_gameengine.framework

interface IChessPointManager {
    //	IChessPoint createChessPointRed();
    //	IChessPoint createChessPointYellow();
    //	IChessPoint createChessPointWhite();
    fun getUseableChessPointList(): MutableList<String?>?

    fun createChessPonitRamdon(): IChessPoint?
}
