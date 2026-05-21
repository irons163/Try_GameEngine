package com.example.try_gameengine.framework

import android.content.Context
import android.view.MotionEvent

abstract class AChessGameModel(context: Context?, data: Data?) : GameModel(context, data),
    IDrawEvent, IChessBoardInit {
    protected override var playerManager: IPlayerManager? = null

    init {
        // TODO Auto-generated constructor stub
        initChessBoard()
        initChessPointManager()
        initPlayerManager()
    }

    public abstract override fun onTouchEvent(event: MotionEvent?)
}
