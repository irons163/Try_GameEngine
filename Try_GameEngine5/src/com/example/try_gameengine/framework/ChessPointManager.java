package com.example.try_gameengine.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;

public class ChessPointManager implements IChessPointManager {
//	private int[] chessPointBimapResiource = { R.drawable.red_point,
//			R.drawable.yellow_point };
	private int[] chessPointBimapResiource;
//	private boolean[] chessPointBimapResiourceUseable = { true, true };
	private boolean[] chessPointBimapResiourceUseable;
	
	private IChessPointFactory chessPointFactory;

	public ChessPointManager(Context context, int chessPointWidth, int chessPointHeight){
		chessPointFactory = new ChessPointFactory(context, chessPointWidth, chessPointHeight);
	}
	
	public ChessPointManager(Context context, IChessPointFactory chessPointFactory ,int chessPointWidth, int chessPointHeight){
		this.chessPointFactory = chessPointFactory;
	}
	
	public void setChessPointFactory(IChessPointFactory chessPointFactory){
		this.chessPointFactory = chessPointFactory;
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

	@Override
	public List<String> getUseableChessPointList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IChessPoint createChessPonitRamdon() {
		// TODO Auto-generated method stub
		List<Integer> chessPointResourceUseableList = new ArrayList<Integer>();
		
		for(int resNo = 0 ; resNo < chessPointBimapResiource.length ; resNo++){
			if(chessPointBimapResiourceUseable[resNo])
				chessPointResourceUseableList.add(chessPointBimapResiource[resNo]);
		}
		
		Random random = new Random();
		int resNo = random.nextInt(chessPointResourceUseableList.size());
		int res = chessPointResourceUseableList.get(resNo);
		
		return chessPointFactory.createChessPointRamdon(res);
	}

	
	public int[] getChessPointBimapResiource() {
		return chessPointBimapResiource;
	}

	public void setChessPointBimapResiource(int[] chessPointBimapResiource) {
		this.chessPointBimapResiource = chessPointBimapResiource;
	}

	public boolean[] getChessPointBimapResiourceUseable() {
		return chessPointBimapResiourceUseable;
	}

	public void setChessPointBimapResiourceUseable(
			boolean[] chessPointBimapResiourceUseable) {
		this.chessPointBimapResiourceUseable = chessPointBimapResiourceUseable;
	}
}
