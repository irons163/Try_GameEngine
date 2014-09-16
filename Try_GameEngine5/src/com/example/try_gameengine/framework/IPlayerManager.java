package com.example.try_gameengine.framework;

import java.util.List;

import android.view.MotionEvent;

public interface IPlayerManager {
	void setPlayersBySquential(List<IPlayer> playersBySquential);
//	void setBoard(IChessBoard jumpChessBoard);
	IPlayer getNextPlayer();
	IPlayer getBefforePlayer();
	IPlayer getCurrentPlayer();
	void toNextPlayer();
	void toBefforePlayer();
	boolean isAllPlayersDone();
	boolean isPlayerCanRun();
	boolean isPlayerProcessing();
	void setOnProcessing();
	void onTouchEvent(MotionEvent event);
//	void setLogic(Logic logic);
	public List<IPlayer> getPlayersBySquential();
}
