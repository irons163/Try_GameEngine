package com.example.try_gameengine.framework;

public interface IPlayerFactory {
//	IPlayer createHumanPlayerWithRed();
//	IPlayer createHumanPlayerWithYellow();
	IPlayer createAIPlayer();
	
	IPlayer createAIPlayerRamdon();
}
