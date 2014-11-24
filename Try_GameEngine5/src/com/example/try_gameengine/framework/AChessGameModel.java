package com.example.try_gameengine.framework;

import android.content.Context;
import android.view.MotionEvent;

public abstract class AChessGameModel extends GameModel implements IDrawEvent, IChessBoardInit{
	protected AChessPlayerManager playerManager;
	
	public AChessGameModel(Context context, Data data) {
		super(context, data);
		// TODO Auto-generated constructor stub
		initChessBoard();
		initChessPointManager();
		initPlayerManager();
	}
	
	@Override
	public abstract void onTouchEvent(MotionEvent event);
	
}
