package com.example.try_gameengine.find_the_path

class FindThePath {
    var detailPathListener: onDetailPathListener? = null
    @JvmField
    var isFindPath: Boolean = false

    fun setMapList(map: Array<IntArray>) {
        MapList.map =
            Array<Array<IntArray>>(1) { Array<IntArray>(map.size) { IntArray(map[0]!!.size) } }
        for (i in map.indices) {
            for (j in map[0]!!.indices) {
                MapList.map[0][i][j] = map[j]!![i]
            }
        }
    }

    fun setMapMark(arriveablePointTypes: Int) {
        for (i in MapList.map[0].indices) {
            for (j in MapList.map[0][0].indices) {
                if (MapList.map[0][i][j] == arriveablePointTypes) {
                    MapList.map[0][i][j] = 0
                } else {
                    MapList.map[0][i][j] = 1
                }
            }
        }
    }

    fun findThePath(source: IntArray, target: IntArray, algorithmId: Int): Boolean {
        val game = Game()
        game.findThePath = this
        game.algorithmId = algorithmId
        game.source = target
        game.target = source

        MapList.map[0][source[1]][source[0]] = 0

        game.clearState()
        game.runAlgorithm()

        synchronized(this@FindThePath) {
            try {
                (this@FindThePath as Object).wait()
            } catch (e: InterruptedException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        }


//		hm = game.hm;
//		int[][] tempA=game.hm.get(target[0]+":"+target[1]);
        isFindPath = false
        if (game.hm != null) isFindPath = true


//		detailFindPath(hm, source, target);
        val hm = game.hm
        this.hm = hm
        this.target = target
        this.source = source
        this.temp = source
        return isFindPath
    }

    fun detailFindPath(hm: HashMap<String?, Array<IntArray>?>, source: IntArray, target: IntArray) {
        var temp = source
        var count = 0 //路徑長度計數器

        temp = source
        count = 0 //路徑長度計數器ん			
        while (true) {
            val tempA = hm.get(temp[0].toString() + ":" + temp[1])


            //			paint.setColor(Color.BLACK);
//			paint.setStyle(Style.STROKE);//加粗
//			paint.setStrokeWidth(2);//設定畫筆粗度為2px
//			canvas.drawLine(	
//				tempA[0][0]*(span+1)+span/2+6,tempA[0][1]*(span+1)+span/2+6,
//				tempA[1][0]*(span+1)+span/2+6,tempA[1][1]*(span+1)+span/2+6, 
//				paint
//			);
            temp = tempA!![1]
            if (detailPathListener != null) detailPathListener!!.findPath(
                tempA[0],
                tempA[1],
                count
            )

            count++
            if (tempA[1][0] == target[0] && tempA[1][1] == target[1]) {
                /**有否到出發點 */
                break
            }
        }
    }

    var temp: IntArray = IntArray(0)
    var hm: HashMap<String?, Array<IntArray>?>? = null
    @JvmField
    var source: IntArray? = null
    @JvmField
    var target: IntArray = IntArray(0)
    var count: Int = 0
    fun detailFindPathStepByStep(
        hm: HashMap<String?, Array<IntArray>?>?,
        source: IntArray,
        target: IntArray
    ) {
//		int[] temp=source;
        var count = 0 //路徑長度計數器

        temp = source
        count = 0 //路徑長度計數器ん			
        //		while(true){
        this.hm = hm
        this.target = target
        //		temp=hm.get(temp[0]+":"+temp[1]);
        // /**/            paint.setColor(Color.BLACK);
        // * /            paint.setStyle(Style.STROKE);//加粗
        // * /            paint.setStrokeWidth(2);//設定畫筆粗度為2px
        // * /            canvas.drawLine(
        // * /                tempA[0][0]*(span+1)+span/2+6, tempA[0][1]*(span+1)+span/2+6,
        // * /                tempA[1][0]*(span+1)+span/2+6, tempA[1][1]*(span+1)+span/2+6,
        // * /                paint
        // * /); */
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

    fun detailFindPathNext(): Boolean {
        val tempA = hm!!.get(temp[0].toString() + ":" + temp[1])

        temp = tempA!![1]
        if (detailPathListener != null) detailPathListener!!.findPath(tempA[0], tempA[1], count)

        count++
        if (tempA[1][0] == target[0] && tempA[1][1] == target[1]) {
            /**有否到出發點 */
            return false
        }
        return true
    }

    interface onDetailPathListener {
        fun findPath(start: IntArray?, end: IntArray?, step: Int)
    }

    fun setOnDetailPathListener(detailPathListener: onDetailPathListener?) {
        this.detailPathListener = detailPathListener
    }

    fun getOnDetailPathListener(): onDetailPathListener? {
        return detailPathListener
    }

    fun getSource(): IntArray? {
        return source
    }

    fun getTarget(): IntArray {
        return target
    }

    fun isFindPath(): Boolean {
        return isFindPath
    }
}
