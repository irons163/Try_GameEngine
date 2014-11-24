package com.example.try_gameengine.framework;

import java.util.List;

public interface IChessPointManager {
//	IChessPoint createChessPointRed();
//	IChessPoint createChessPointYellow();
//	IChessPoint createChessPointWhite();
	
	List<String> getUseableChessPointList();
	
	IChessPoint createChessPonitRamdon();
}
