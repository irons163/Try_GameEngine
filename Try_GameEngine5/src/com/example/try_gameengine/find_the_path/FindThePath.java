package com.example.try_gameengine.find_the_path;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;

import android.graphics.Color;
import android.graphics.Paint.Style;

public class FindThePath {
	onDetailPathListener detailPathListener;
	boolean isFindPath = false;
	
	public void setMapList(int[][] map){
		MapList.map = new int[1][map.length][map[0].length];
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[0].length; j++){
				MapList.map[0][i][j] = map[j][i];
			}
		}
	}
	
	public void setMapMark(int arriveablePointTypes){
		for(int i = 0; i < MapList.map[0].length; i++){
			for(int j = 0; j < MapList.map[0][0].length; j++){
				if(MapList.map[0][i][j] == arriveablePointTypes){
					MapList.map[0][i][j] = 0;
				}else{
					MapList.map[0][i][j] = 1;
				}
			}
		}
	}
	
	public boolean findThePath(int[] source, int[] target, int algorithmId){
		Game game = new Game();
		game.findThePath = this;
		game.algorithmId = algorithmId;
		game.source = target;
		game.target = source;
		
		MapList.map[0][source[1]][source[0]] = 0;
		
		game.clearState();
		game.runAlgorithm();
		
		synchronized (FindThePath.this) {
			try {
				FindThePath.this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		hm = game.hm;
//		int[][] tempA=game.hm.get(target[0]+":"+target[1]);

		isFindPath = false;
		if(game.hm!=null)
			isFindPath = true;
		
//		detailFindPath(hm, source, target);
		HashMap<String,int[][]> hm=game.hm;
		this.hm = hm;
		this.target = target;
		this.source = source;
		this.temp = source;
		return isFindPath;
	}
	
	public void detailFindPath(HashMap<String,int[][]> hm, int[] source, int[] target){
		int[] temp=source;
		int count=0;//路徑長度計數器

		temp=source;
		count=0;//路徑長度計數器ん			
		while(true){
			int[][] tempA=hm.get(temp[0]+":"+temp[1]);
//			paint.setColor(Color.BLACK);
//			paint.setStyle(Style.STROKE);//加粗
//			paint.setStrokeWidth(2);//設定畫筆粗度為2px
//			canvas.drawLine(	
//				tempA[0][0]*(span+1)+span/2+6,tempA[0][1]*(span+1)+span/2+6,
//				tempA[1][0]*(span+1)+span/2+6,tempA[1][1]*(span+1)+span/2+6, 
//				paint
//			);

			
			temp=tempA[1];
			if(detailPathListener!=null)
				detailPathListener.findPath(tempA[0], tempA[1], count);
			
			count++;
			if(tempA[1][0]==target[0]&&tempA[1][1]==target[1]){///有否到出發點
				break;
			}
		}
	}
	
	int[] temp;
	HashMap<String,int[][]> hm;
	int[] source;
	int[] target;
	int count;
	public void detailFindPathStepByStep(HashMap<String,int[][]> hm, int[] source, int[] target){
//		int[] temp=source;
		int count=0;//路徑長度計數器

		temp=source;
		count=0;//路徑長度計數器ん			
//		while(true){
		this.hm = hm;
		this.target = target;
//		temp=hm.get(temp[0]+":"+temp[1]);
////			paint.setColor(Color.BLACK);
////			paint.setStyle(Style.STROKE);//加粗
////			paint.setStrokeWidth(2);//設定畫筆粗度為2px
////			canvas.drawLine(	
////				tempA[0][0]*(span+1)+span/2+6,tempA[0][1]*(span+1)+span/2+6,
////				tempA[1][0]*(span+1)+span/2+6,tempA[1][1]*(span+1)+span/2+6, 
////				paint
////			);
//			count++;
//			if(tempA[1][0]==target[0]&&tempA[1][1]==target[1]){///有否到出發點
//				break;
//			}
//			
//			temp=tempA[1];
//			if(detailPathListener!=null)
//				detailPathListener.findPath(tempA[0], tempA[1], count);
//		}
	}
	
	public boolean detailFindPathNext(){
			int[][] tempA=hm.get(temp[0]+":"+temp[1]);
		
			temp=tempA[1];
			if(detailPathListener!=null)
				detailPathListener.findPath(tempA[0], tempA[1], count);
			
			count++;
			if(tempA[1][0]==target[0]&&tempA[1][1]==target[1]){///有否到出發點
				return false;
			}
			return true;
	}

	public interface onDetailPathListener{
		void findPath(int[] start, int[] end, int step);
	}
	
	public void setOnDetailPathListener(onDetailPathListener detailPathListener){
		this.detailPathListener = detailPathListener;
	}
	
	public onDetailPathListener getOnDetailPathListener(){
		return detailPathListener;
	}
	
	public int[] getSource(){
		return source;
	}
	
	public int[] getTarget(){
		return target;
	}
	
	public boolean isFindPath(){
		return isFindPath;
	}
}
