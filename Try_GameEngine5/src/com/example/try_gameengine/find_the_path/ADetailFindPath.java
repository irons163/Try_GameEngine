package com.example.try_gameengine.find_the_path;

import java.util.HashMap;

public abstract class ADetailFindPath {
	HashMap<String,int[][]> hm;
	
	public ADetailFindPath(HashMap<String,int[][]> hm){
		this.hm = hm;
	}
	
	public void detailFindPath(int[] source, int[] target){
		int[] temp=source;
		int count=0;//���|���׭p�ƾ�

		temp=source;
		count=0;//���|���׭p�ƾ���			
		while(true){
			int[][] tempA=hm.get(temp[0]+":"+temp[1]);
//			paint.setColor(Color.BLACK);
//			paint.setStyle(Style.STROKE);//�[��
//			paint.setStrokeWidth(2);//�]�w�e���ʫ׬�2px
//			canvas.drawLine(	
//				tempA[0][0]*(span+1)+span/2+6,tempA[0][1]*(span+1)+span/2+6,
//				tempA[1][0]*(span+1)+span/2+6,tempA[1][1]*(span+1)+span/2+6, 
//				paint
//			);
			count++;
			if(tempA[1][0]==target[0]&&tempA[1][1]==target[1]){///���_��X�o�I
				break;
			}
			
			temp=tempA[1];
			findPath(tempA[0], tempA[1], count);
		}
	}
	
	abstract void findPath(int[] start, int[] end, int step);
	
}
