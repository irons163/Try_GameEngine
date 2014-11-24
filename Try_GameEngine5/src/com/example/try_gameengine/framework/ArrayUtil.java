package com.example.try_gameengine.framework;

public class ArrayUtil {
	public static boolean isArrayColElementAllNotZero(int[][] array, int whichCol){
		boolean isAllNotZero = true;
		
		for(int row : array[whichCol]){
			if(row==0){
				isAllNotZero = false;
				break;
			}
		}	
		return isAllNotZero;
	}
	
	public static int witchArrayColElementIsNotZeroOrderByRow(int[][] array, int whichCol){
		int witchIsNotZero = -1;
		
		for(int row = array[whichCol].length-1 ;row >= 0 ; row--){
			if(array[whichCol][row]==0){
				witchIsNotZero = row;
				break;
			}
		}	
		return witchIsNotZero;
	}
	
	public static int[][] arrayTranspose(int[][] array){
		int[][] newArray = new int[8][8];
		for(int row = 0 ;row < array.length ; row++){
			for(int col = 0 ; col < array[row].length ; col++){
				newArray[col][row] = array[row][col];
			}
		}	
		return newArray;
	}
	
//	public static int arrayTurnRignt(int[][] array){
//		int witchIsNotZero = -1;
//		
//		for(int row = array[whichCol].length-1 ;row >= 0 ; row--){
//			if(array[whichCol][row]==0){
//				witchIsNotZero = row;
//				break;
//			}
//		}	
//		return witchIsNotZero;
//	}
}
