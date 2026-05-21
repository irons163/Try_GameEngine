package com.example.try_gameengine.framework

interface IChessPointFactory {
    //	IChessPoint createChessPointRed();
    //	IChessPoint createChessPointYellow();
    //	IChessPoint createChessPointWhite();
    fun createChessPointRamdon(resource: Int): IChessPoint?
}
