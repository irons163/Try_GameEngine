package com.example.try_gameengine.framework

interface IPlayerFactory {
    //	IPlayer createHumanPlayerWithRed();
    //	IPlayer createHumanPlayerWithYellow();
    fun createAIPlayer(): IPlayer?

    fun createAIPlayerRamdon(): IPlayer?
}
