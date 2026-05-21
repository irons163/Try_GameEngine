package com.example.try_gameengine.find_the_path

import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.util.LinkedList
import java.util.PriorityQueue
import java.util.Stack
import kotlin.math.sqrt

//引入相關類別
//引入相關類別
//引入相關類別
//引入相關類別
class Game {
    //演算法類別
    var algorithmId: Int = 0 //演算法代號 0--深度優先
    var mapId: Int = 0 //地圖編號
    var map: Array<IntArray> = MapList.map[mapId]
    var source: IntArray = MapList.source //出發點
    var target: IntArray = MapList.target[0] //目標點

    //	GameView gameView;//gameView的引用
    var goButton: Button? = null //goButton的引用
    var BSTextView: TextView? = null //BSTextView的引用
    var searchProcess: ArrayList<Array<IntArray>?> = ArrayList<Array<IntArray>?>() //搜索過程
    var stack: Stack<Array<IntArray>> = Stack<Array<IntArray>>() //深度優先所用堆疊
    var hm: HashMap<String?, Array<IntArray>?>? = HashMap<String?, Array<IntArray>?>() //結果路徑記錄
    var queue: LinkedList<Array<IntArray>?> = LinkedList<Array<IntArray>?>() //廣度優先所用佇列

    //A*用優先順序佇列
    var astarQueue: PriorityQueue<Array<IntArray>?> =
        PriorityQueue<Array<IntArray>?>(100, AStarComparator(this))

    //記錄到每個點的最短路徑 for Dijkstra
    var hmPath: HashMap<String?, ArrayList<Array<IntArray>?>?> =
        HashMap<String?, ArrayList<Array<IntArray>?>?>()

    //記錄路徑長度 for Dijkstra
    var length: Array<IntArray> =
        Array<IntArray>(MapList.map[mapId].size) { IntArray(MapList.map[mapId][0].size) }
    var visited: Array<IntArray> =
        Array<IntArray>(MapList.map[0].size) { IntArray(MapList.map[0][0].size) }  //0 未去過 1 去過
    var sequence: Array<IntArray> = arrayOf<IntArray>(
        intArrayOf(0, 1), intArrayOf(0, -1),
        intArrayOf(-1, 0), intArrayOf(1, 0),
        intArrayOf(-1, 1), intArrayOf(-1, -1),
        intArrayOf(1, -1), intArrayOf(1, 1)
    )
    var pathFlag: Boolean = false //true 找到了路徑
    var timeSpan: Int = 10 //時間間隔
    private val myHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            Log.e("stopFind", "stopFind")
        }
    }

    var findThePath: FindThePath? = null

    //	private Handler myHandler = new Handler(){//用來更新UI執行緒的控制項
    // /**/        @SuppressLint("HandlerLeak") */ //		public void handleMessage(Message msg){
    //        	Log.e("stopFind", "stopFind");
    //        	if(msg.what == 1){//改變按鈕狀態
    // /**/                goButton.setEnabled(true);
    // * /                synchronized (FindThePath.
    // class) {
        // * /                    FindThePath.
        // class.notifyAll();
        // * /
    // } */
    //        	}
    //        	else if(msg.what == 2){//改變步數的TextView的值
    // /**/                BSTextView.setText("使用步數：" + (Integer)msg.obj);
    // * /                synchronized (FindThePath.
    // class) {
        // * /                    FindThePath.
        // class.notifyAll();
        // * /
    // } */
    //        	}
    //        }
    //	};
    fun clearState() { //清空所有狀態與清單
        pathFlag = false
        searchProcess.clear()
        stack.clear()
        queue.clear()
        astarQueue.clear()
        hm!!.clear()
        visited = Array<IntArray>(MapList.map[mapId].size) { IntArray(MapList.map[mapId][0].size) }
        hmPath.clear()
        for (i in length.indices) {
            for (j in length[0]!!.indices) {
                length[i]!![j] = 9999 //初始路徑長度為最大距離都不可能的那麼大	
            }
        }
    }

    fun runAlgorithm() { //運行演算法
        clearState()
        when (algorithmId) {
            0 -> DFS()
            1 -> BFS()
            2 -> BFSAStar()
            3 -> Dijkstra()
            4 -> DijkstraAStar() //DijkstraA*演算法
        }
    }


    fun DFS() { //深度優先演算法
        object : Thread() {
            override fun run() {
                val flag = true
                val start = arrayOf<IntArray>( //開始狀態
                    intArrayOf(source[0], source[1]),
                    intArrayOf(source[0], source[1])
                )
                stack.push(start)
                var count = 0 //步數計數器
                while (flag) {
                    val currentEdge = stack.pop() //從堆疊頂取出邊
                    val tempTarget = currentEdge[1] //取出此邊的目的點
                    //判斷目的點是否去過，若去過則直接進入下次迴圈
                    if (visited[tempTarget[1]]!![tempTarget[0]] == 1) {
                        continue
                    }
                    count++
                    visited[tempTarget[1]]!![tempTarget[0]] = 1 //標識目的點為訪問過
                    //將臨時目的點加入搜索過程中
                    searchProcess.add(currentEdge)
                    //記錄此臨時目的點的父節點
                    hm!!.put(
                        tempTarget[0].toString() + ":" + tempTarget[1],
                        arrayOf<IntArray>(currentEdge[1], currentEdge[0])
                    )
                    //					gameView.postInvalidate();
                    try {
                        sleep(timeSpan.toLong())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    //判斷有否找到目的點
                    if (tempTarget[0] == target[0] && tempTarget[1] == target[1]) {
                        break
                    }
                    //將所有可能的邊入堆疊
                    val currCol = tempTarget[0]
                    val currRow = tempTarget[1]
                    for (rc in sequence) {
                        val i = rc[1]
                        val j = rc[0]
                        if (i == 0 && j == 0) {
                            continue
                        }
                        if (currRow + i >= 0 && currRow + i < MapList.map[mapId].size && currCol + j >= 0 && currCol + j < MapList.map[mapId][0].size && map[currRow + i]!![currCol + j] != 1) {
                            val tempEdge = arrayOf<IntArray>(
                                intArrayOf(tempTarget[0], tempTarget[1]),
                                intArrayOf(currCol + j, currRow + i)
                            )
                            stack.push(tempEdge)
                        }
                    }
                }
                pathFlag = true

//				gameView.postInvalidate();
                //設定按鈕的可用性
                val msg1 = myHandler.obtainMessage(1)
                myHandler.sendMessage(msg1)
                myHandler.sendEmptyMessage(1)
                //改變TextView文字
                val msg2 = myHandler.obtainMessage(2, count)
                myHandler.sendMessage(msg2)

                synchronized(findThePath!!) {
                    (findThePath as Object).notifyAll()
                }
            }
        }.start()
    }

    fun BFS() { //廣度優先演算法
        object : Thread() {
            override fun run() {
                var count = 0 //步數計數器
                val flag = true
                val start = arrayOf<IntArray>( //開始狀態
                    intArrayOf(source[0], source[1]),
                    intArrayOf(source[0], source[1])
                )
                queue.offer(start)
                while (flag) {
                    val currentEdge = queue.poll() //從隊首取出邊
                    val tempTarget = currentEdge!![1] //取出此邊的目的點
                    //判斷目的點是否去過，若去過則直接進入下次迴圈
                    if (visited[tempTarget[1]]!![tempTarget[0]] == 1) {
                        continue
                    }
                    count++
                    visited[tempTarget[1]]!![tempTarget[0]] = 1 //標識目的點為訪問過
                    searchProcess.add(currentEdge) //將臨時目的點加入搜索過程中
                    //記錄此臨時目的點的父節點
                    hm!!.put(
                        tempTarget[0].toString() + ":" + tempTarget[1],
                        arrayOf<IntArray>(currentEdge[1], currentEdge[0])
                    )
                    //					gameView.postInvalidate();
                    try {
                        sleep(timeSpan.toLong())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    //判斷有否找到目的點
                    if (tempTarget[0] == target[0] && tempTarget[1] == target[1]) {
                        Log.e("break", "break")
                        break
                    }
                    //將所有可能的邊入佇列
                    val currCol = tempTarget[0]
                    val currRow = tempTarget[1]
                    for (rc in sequence) {
                        val i = rc[1]
                        val j = rc[0]
                        if (i == 0 && j == 0) {
                            continue
                        }
                        if (currRow + i >= 0 && currRow + i < MapList.map[mapId].size && currCol + j >= 0 && currCol + j < MapList.map[mapId][0].size && map[currRow + i]!![currCol + j] != 1) {
                            val tempEdge = arrayOf<IntArray>(
                                intArrayOf(tempTarget[0], tempTarget[1]),
                                intArrayOf(currCol + j, currRow + i)
                            )
                            queue.offer(tempEdge)
                        }
                    }
                }
                pathFlag = true

//				gameView.postInvalidate();
                val msg1 = myHandler.obtainMessage(1)
                myHandler.sendMessage(msg1) //設定按鈕的可用性
                val msg2 = myHandler.obtainMessage(2, count)
                myHandler.sendMessage(msg2) //改變TextView文字

                synchronized(findThePath!!) {
                    (findThePath as Object).notifyAll()
                }
            }
        }.start()
    }

    fun Dijkstra() { //Dijkstra演算法
        object : Thread() {
            override fun run() {
                var count = 0 //步數計數器
                val flag = true //搜索迴圈控制
                val start = intArrayOf(source[0], source[1]) //開始點col,row	
                visited[source[1]]!![source[0]] = 1
                for (rowcol in sequence) {    //計算此點所有可以到達點的路徑及長度				
                    val trow = start[1] + rowcol[1]
                    val tcol = start[0] + rowcol[0]
                    if (trow < 0 || trow > 18 || tcol < 0 || tcol > 18) continue
                    if (map[trow]!![tcol] != 0) continue
                    //記錄路徑長度
                    length[trow]!![tcol] = 1
                    //計算路徑					
                    val key = tcol.toString() + ":" + trow
                    val al = ArrayList<Array<IntArray>?>()
                    al.add(
                        arrayOf<IntArray>(
                            intArrayOf(start[0], start[1]),
                            intArrayOf(tcol, trow)
                        )
                    )
                    hmPath.put(key, al)

                    //將去過的點記錄			
                    searchProcess.add(
                        arrayOf<IntArray>(
                            intArrayOf(start[0], start[1]),
                            intArrayOf(tcol, trow)
                        )
                    )
                    count++
                }

//				gameView.postInvalidate();
                outer@ while (flag) {
                    //找到當前擴展點K 要求擴展點K為從開始點到此點目前路徑最短，且此點未考察過
                    val k = IntArray(2)
                    var minLen = 9999
                    for (i in visited.indices) {
                        for (j in visited[0]!!.indices) {
                            if (visited[i]!![j] == 0) {
                                if (minLen > length[i]!![j]) {
                                    minLen = length[i]!![j]
                                    k[0] = j //col
                                    k[1] = i //row
                                }
                            }
                        }
                    }
                    visited[k[1]]!![k[0]] = 1 //設定去過的點					
                    //					gameView.postInvalidate();//重繪
                    val dk = length[k[1]]!![k[0]] //取出開始點到K的路徑長度
                    val al = hmPath.get(k[0].toString() + ":" + k[1]) //取出開始點到K的路徑
                    //迴圈計算所有K點能直接到的點到開始點的路徑長度
                    for (rowcol in sequence) {
                        val trow = k[1] + rowcol[1] //計算出新的要計算的點的座標
                        val tcol = k[0] + rowcol[0]
                        //若要計算的點超出地圖邊界或地圖上此位置為障礙物則捨棄考察此點
                        if (trow < 0 || trow > MapList.map[mapId].size - 1 || tcol < 0 || tcol > MapList.map[mapId][0].size - 1) continue
                        if (map[trow]!![tcol] != 0) continue
                        val dj = length[trow]!![tcol] //取出開始點到此點的路徑長度
                        val dkPluskj = dk + 1 //計算經K點到此點的路徑長度
                        //若經K點到此點的路徑長度比原來的小則修改到此點的路徑
                        if (dj > dkPluskj) {
                            val key = tcol.toString() + ":" + trow
                            //克隆開始點到K的路徑
                            val tempal = al!!.clone() as ArrayList<Array<IntArray>?>
                            //將路徑中加上一步從K到此點
                            tempal.add(
                                arrayOf<IntArray>(
                                    intArrayOf(k[0], k[1]),
                                    intArrayOf(tcol, trow)
                                )
                            )
                            //將此路徑設定為從開始點到此點的路徑
                            hmPath.put(key, tempal)
                            //修改到從開始點到此點的路徑長度							
                            length[trow]!![tcol] = dkPluskj
                            //若此點從未計算過路徑長度則將此點加入考察過程記錄
                            if (dj == 9999) { //將去過的點記錄	
                                searchProcess.add(
                                    arrayOf<IntArray>(
                                        intArrayOf(k[0], k[1]),
                                        intArrayOf(tcol, trow)
                                    )
                                )
                                count++
                            }
                        }
                        //看是否找到目的點
                        if (tcol == target[0] && trow == target[1]) {
                            pathFlag = true
                            val msg1 = myHandler.obtainMessage(1)
                            myHandler.sendMessage(msg1) //設定按鈕的可用性
                            val msg2 = myHandler.obtainMessage(2, count)
                            myHandler.sendMessage(msg2) //改變TextView文字
                            break@outer
                        }
                    }
                    try {
                        sleep(timeSpan.toLong())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }.start()
    }

    fun BFSAStar() { //廣度優先 A*演算法
        object : Thread() {
            override fun run() {
                val flag = true
                val start = arrayOf<IntArray>( //開始狀態
                    intArrayOf(source[0], source[1]),
                    intArrayOf(source[0], source[1])
                )
                astarQueue.offer(start)
                var count = 0
                while (flag) {
                    val currentEdge: Array<IntArray>?
                    val tempTarget: IntArray
                    try {
                        currentEdge = astarQueue.poll() //從隊首取出邊
                        tempTarget = currentEdge!![1] //取出此邊的目的點
                    } catch (e: Exception) {
                        // TODO: handle exception
                        hm = null
                        break
                    }


                    //判斷目的點是否去過，若去過則直接進入下次迴圈
                    if (visited[tempTarget[1]]!![tempTarget[0]] != 0) {
                        continue
                    }
                    count++
                    //標識目的點為訪問過
                    visited[tempTarget[1]]!![tempTarget[0]] =
                        visited[currentEdge[0][1]]!![currentEdge[0][0]] + 1
                    searchProcess.add(currentEdge) //將臨時目的點加入搜索過程中
                    //記錄此臨時目的點的父節點
                    hm!!.put(
                        tempTarget[0].toString() + ":" + tempTarget[1],
                        arrayOf<IntArray>(currentEdge[1], currentEdge[0])
                    )
                    //					gameView.postInvalidate();
                    try {
                        sleep(timeSpan.toLong())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    //判斷有否找到目的點
                    if (tempTarget[0] == target[0] && tempTarget[1] == target[1]) {
                        break
                    }
                    val currCol = tempTarget[0] //將所有可能的邊入優先順序佇列
                    val currRow = tempTarget[1]
                    for (rc in sequence) {
                        val i = rc[1]
                        val j = rc[0]
                        if (i == 0 && j == 0) {
                            continue
                        }
                        if (currRow + i >= 0 && currRow + i < MapList.map[mapId].size && currCol + j >= 0 && currCol + j < MapList.map[mapId][0].size && map[currRow + i]!![currCol + j] != 1) {
                            val tempEdge = arrayOf<IntArray>(
                                intArrayOf(tempTarget[0], tempTarget[1]),
                                intArrayOf(currCol + j, currRow + i)
                            )
                            astarQueue.offer(tempEdge)
                        }
                    }
                }
                pathFlag = true

//				gameView.postInvalidate();
                val msg1 = myHandler.obtainMessage(1)
                myHandler.sendMessage(msg1) //設定按鈕的可用性
                val msg2 = myHandler.obtainMessage(2, count)
                myHandler.sendMessage(msg2) //改變TextView文字

                synchronized(findThePath!!) {
                    (findThePath as Object).notifyAll()
                }
            }
        }.start()
    }

    fun DijkstraAStar() { //Dijkstra A*演算法
        object : Thread() {
            override fun run() {
                var count = 0 //步數計數器
                val flag = true //搜索迴圈控制
                val start = intArrayOf(source[0], source[1]) //開始點col,row	
                visited[source[1]]!![source[0]] = 1
                //計算此點所有可以到達點的路徑及長度
                for (rowcol in sequence) {
                    val trow = start[1] + rowcol[1]
                    val tcol = start[0] + rowcol[0]
                    if (trow < 0 || trow > MapList.map[mapId].size - 1 || tcol < 0 || tcol > MapList.map[mapId][0].size - 1) continue
                    if (map[trow]!![tcol] != 0) continue
                    //記錄路徑長度
                    length[trow]!![tcol] = 1
                    val key = tcol.toString() + ":" + trow //計算路徑
                    val al = ArrayList<Array<IntArray>?>()
                    al.add(
                        arrayOf<IntArray>(
                            intArrayOf(start[0], start[1]),
                            intArrayOf(tcol, trow)
                        )
                    )
                    hmPath.put(key, al)

                    //將去過的點記錄			
                    searchProcess.add(
                        arrayOf<IntArray>(
                            intArrayOf(start[0], start[1]),
                            intArrayOf(tcol, trow)
                        )
                    )
                    count++
                }

//				gameView.postInvalidate();
                outer@ while (flag) {
                    val k = IntArray(2)
                    var minLen = 9999
                    var iniFlag = true
                    for (i in visited.indices) {
                        for (j in visited[0]!!.indices) {
                            if (visited[i]!![j] == 0) {
                                //與普通Dijkstra演算法的區別部分=========begin=================================
                                if (length[i]!![j] != 9999) {
                                    if (iniFlag) { //第一個找到的可能點
                                        minLen =
                                            length[i]!![j] + sqrt(((j - target[0]) * (j - target[0]) + (i - target[1]) * (i - target[1])).toDouble()).toInt()
                                        k[0] = j //col
                                        k[1] = i //row
                                        iniFlag = !iniFlag
                                    } else {
                                        val tempLen =
                                            length[i]!![j] + sqrt(((j - target[0]) * (j - target[0]) + (i - target[1]) * (i - target[1])).toDouble()).toInt()
                                        if (minLen > tempLen) {
                                            minLen = tempLen
                                            k[0] = j //col
                                            k[1] = i //row
                                        }
                                    }
                                }
                                //與普通Dijkstra演算法的區別部分==========end==================================
                            }
                        }
                    }
                    //設定去過的點
                    visited[k[1]]!![k[0]] = 1

                    //重繪					
//					gameView.postInvalidate();
                    val dk = length[k[1]]!![k[0]]
                    val al = hmPath.get(k[0].toString() + ":" + k[1])
                    for (rowcol in sequence) {
                        val trow = k[1] + rowcol[1]
                        val tcol = k[0] + rowcol[0]
                        if (trow < 0 || trow > MapList.map[mapId].size - 1 || tcol < 0 || tcol > MapList.map[mapId][0].size - 1) continue
                        if (map[trow]!![tcol] != 0) continue
                        val dj = length[trow]!![tcol]
                        val dkPluskj = dk + 1
                        if (dj > dkPluskj) {
                            val key = tcol.toString() + ":" + trow
                            val tempal = al!!.clone() as ArrayList<Array<IntArray>?>
                            tempal.add(
                                arrayOf<IntArray>(
                                    intArrayOf(k[0], k[1]),
                                    intArrayOf(tcol, trow)
                                )
                            )
                            hmPath.put(key, tempal)
                            length[trow]!![tcol] = dkPluskj
                            if (dj == 9999) {
                                //將去過的點記錄			
                                searchProcess.add(
                                    arrayOf<IntArray>(
                                        intArrayOf(k[0], k[1]),
                                        intArrayOf(tcol, trow)
                                    )
                                )
                                count++
                            }
                        }
                        //看是否找到目的點
                        if (tcol == target[0] && trow == target[1]) {
                            pathFlag = true
                            val msg1 = myHandler.obtainMessage(1)
                            myHandler.sendMessage(msg1) //設定按鈕的可用性
                            val msg2 = myHandler.obtainMessage(2, count)
                            myHandler.sendMessage(msg2) //改變TextView文字
                            break@outer
                        }
                    }
                    try {
                        sleep(timeSpan.toLong())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }.start()
    }
}
