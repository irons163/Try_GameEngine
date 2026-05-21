package com.example.try_gameengine.framework

object ArrayUtil {
    fun isArrayColElementAllNotZero(array: Array<IntArray?>, whichCol: Int): Boolean {
        var isAllNotZero = true

        for (row in array[whichCol]!!) {
            if (row == 0) {
                isAllNotZero = false
                break
            }
        }
        return isAllNotZero
    }

    fun witchArrayColElementIsNotZeroOrderByRow(array: Array<IntArray?>, whichCol: Int): Int {
        var witchIsNotZero = -1

        for (row in array[whichCol]!!.indices.reversed()) {
            if (array[whichCol]!![row] == 0) {
                witchIsNotZero = row
                break
            }
        }
        return witchIsNotZero
    }

    fun arrayTranspose(array: Array<IntArray?>): Array<IntArray?> {
        val newArray = Array<IntArray?>(8) { IntArray(8) }
        for (row in array.indices) {
            for (col in array[row]!!.indices) {
                newArray[col]!![row] = array[row]!![col]
            }
        }
        return newArray
    } //	public static int arrayTurnRignt(int[][] array){
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
