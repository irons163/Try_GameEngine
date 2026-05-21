package com.example.try_gameengine.framework

import android.content.Context
import java.util.Random

class ChessPointManager : IChessPointManager {
    //	private int[] chessPointBimapResiource = { R.drawable.red_point,
    //			R.drawable.yellow_point };
    var chessPointBimapResiource: IntArray = IntArray(0)

    //	private boolean[] chessPointBimapResiourceUseable = { true, true };
    var chessPointBimapResiourceUseable: BooleanArray = BooleanArray(0)

    private lateinit var chessPointFactory: IChessPointFactory

    constructor(context: Context, chessPointWidth: Int, chessPointHeight: Int) {
        chessPointFactory = ChessPointFactory(context, chessPointWidth, chessPointHeight)
    }

    constructor(
        context: Context?,
        chessPointFactory: IChessPointFactory,
        chessPointWidth: Int,
        chessPointHeight: Int
    ) {
        this.chessPointFactory = chessPointFactory
    }

    fun setChessPointFactory(chessPointFactory: IChessPointFactory) {
        this.chessPointFactory = chessPointFactory
    }

    //	@Override
    //	public IChessPoint createChessPointRed() {
    //		// TODO Auto-generated method stub
    //		return chessPointFactory.createChessPointRed();
    //	}
    //
    //	@Override
    //	public IChessPoint createChessPointYellow() {
    //		// TODO Auto-generated method stub
    //		return chessPointFactory.createChessPointYellow();
    //	}
    //	
    //	@Override
    //	public IChessPoint createChessPointWhite() {
    //		// TODO Auto-generated method stub
    //		return chessPointFactory.createChessPointWhite();
    //	}
    override fun getUseableChessPointList(): MutableList<String?>? {
        // TODO Auto-generated method stub
        return null
    }

    override fun createChessPonitRamdon(): IChessPoint? {
        // TODO Auto-generated method stub
        val chessPointResourceUseableList: MutableList<Int?> = ArrayList<Int?>()

        for (resNo in chessPointBimapResiource.indices) {
            if (chessPointBimapResiourceUseable[resNo]) chessPointResourceUseableList.add(
                chessPointBimapResiource[resNo]
            )
        }

        val random = Random()
        val resNo = random.nextInt(chessPointResourceUseableList.size)
        val res: Int = chessPointResourceUseableList.get(resNo)!!

        return chessPointFactory.createChessPointRamdon(res)
    }
}
