package com.example.try_gameengine.framework

import android.content.Context
import android.content.res.Resources

class ChessPointFactory(context: Context, chessPointWidth: Int, chessPointHeight: Int) :
    IChessPointFactory {
    protected var resources: Resources
    protected var chessPointWidth: Int
    protected var chessPointHeight: Int

    init {
        resources = context.getResources()
        this.chessPointWidth = chessPointWidth
        this.chessPointHeight = chessPointHeight
    }

    //	@Override
    //	public IChessPoint createChessPointRed() {
    //		// TODO Auto-generated method stub
    //		IChessPoint chessPoint = new ChessPoint();
    //		chessPoint.setChessPointBitmap(BitmapUtil.createSpecificSizeBitmap(resources.getDrawable(R.drawable.red_point), chessPointWidth, chessPointHeight));
    //		return chessPoint;
    //	}
    //
    //	@Override
    //	public IChessPoint createChessPointYellow() {
    //		// TODO Auto-generated method stub
    //		IChessPoint chessPoint = new ChessPoint();
    //		chessPoint.setChessPointBitmap(BitmapUtil.createSpecificSizeBitmap(resources.getDrawable(R.drawable.yellow_point), chessPointWidth, chessPointHeight));
    //		return chessPoint;
    //	}
    //	
    //	@Override
    //	public IChessPoint createChessPointWhite(){
    //		// TODO Auto-generated method stub
    //		IChessPoint chessPoint = new ChessPoint();
    //		chessPoint.setChessPointBitmap(BitmapUtil.createSpecificSizeBitmap(resources.getDrawable(R.drawable.white_point), chessPointWidth, chessPointHeight));
    //		return chessPoint;
    //	}
    override fun createChessPointRamdon(resource: Int): IChessPoint {
        // TODO Auto-generated method stub
        val chessPoint: IChessPoint = ChessPoint()
        chessPoint.setChessPointBitmap(
            BitmapUtil.createSpecificSizeBitmap(
                resources.getDrawable(
                    resource
                ), chessPointWidth, chessPointHeight
            )
        )
        return chessPoint
    }
}
