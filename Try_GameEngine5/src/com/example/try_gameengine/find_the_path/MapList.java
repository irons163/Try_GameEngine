package com.example.try_gameengine.find_the_path;										//宣告套件語句
public class MapList {									//該類別為地圖類別
	static int[] source={2,2};							//出發點座標 
	static int[][] target={								//目標點座標陣列
		{13,2},{4,22},{0,11},{9,10},{21,22}
	};
	static int[][][] map = new int[][][]				//地圖陣列
	{
		{
			{1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0},
			{1,0,0,0,0,0,1,1,0,0,1,1,0,0,0,0,1,1,0,0,0,0},
			{1,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,1,1,0,0,0,0},
			{1,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1,1,1,1,0,0,0},
			{1,0,0,0,0,0,1,1,1,0,1,1,0,0,0,0,1,1,0,0,0,0},
			{1,0,0,0,0,0,1,1,1,0,1,1,0,0,0,0,1,1,0,0,0,0},
			{1,0,0,0,0,0,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0},
			{1,1,1,0,0,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0},
			{1,1,1,0,0,1,1,1,1,0,1,1,0,0,0,0,0,1,1,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,1,1,0,0,0},
			{0,0,1,0,0,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,0,0},
			{1,1,1,0,0,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,0,0},
			{1,1,1,0,0,1,1,1,1,1,1,1,1,1,0,0,0,1,1,0,0,0},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,1,1,0,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,0,0,0},
			{0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0},
			{0,0,0,0,0,1,1,1,1,1,1,0,0,0,1,1,1,1,0,0,0,0},
			{1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0},
			{1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0},
			{1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0}
		}	
    };
}