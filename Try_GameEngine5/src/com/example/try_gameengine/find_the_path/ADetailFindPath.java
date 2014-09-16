package com.example.try_gameengine.find_the_path;

import java.util.HashMap;

public abstract class ADetailFindPath {
	HashMap<String,int[][]> hm;
	
	public ADetailFindPath(HashMap<String,int[][]> hm){
		this.hm = hm;
	}
	
	public void detailFindPath(int[] source, int[] target){
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
			count++;
			if(tempA[1][0]==target[0]&&tempA[1][1]==target[1]){///有否到出發點
				break;
			}
			
			temp=tempA[1];
			findPath(tempA[0], tempA[1], count);
		}
	}
	
	abstract void findPath(int[] start, int[] end, int step);
	
}
