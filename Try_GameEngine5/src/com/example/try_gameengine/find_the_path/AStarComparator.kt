package com.example.try_gameengine.find_the_path

//引入相關套件
class AStarComparator(game: Game) : Comparator<Array<IntArray>?> {
    //實作了Comparator介面
    var game: Game

    init { //建構式
        this.game = game
    }

    override fun compare(o1: Array<IntArray>?, o2: Array<IntArray>?): Int { //比較方法
        val t1 = o1!![1]
        val t2 = o2!![1]
        val target = game.target //得到目標點
        //直線物理距離
        val a =
            (t1[0] - target[0]) * (t1[0] - target[0]) + (t1[1] - target[1]) * (t1[1] - target[1])
        val b =
            (t2[0] - target[0]) * (t2[0] - target[0]) + (t2[1] - target[1]) * (t2[1] - target[1])
        //蒙地卡羅距離
        //int a=game.visited[o2[0][1]][o2[0][0]]+Math.abs(t1[0]-target[0])+Math.abs(t1[1]-target[1]);
        //int b=game.visited[o2[0][1]][o2[0][0]]+Math.abs(t2[0]-target[0])+Math.abs(t2[1]-target[1]);	
        return a - b //返回差值
    }

    override fun equals(obj: Any?): Boolean {
        return false
    }
}
